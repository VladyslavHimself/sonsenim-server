package com.sonsenim.sonsenimbackend.service;

import com.sonsenim.sonsenimbackend.api.model.CardConfigurationBody;
import com.sonsenim.sonsenimbackend.api.model.UpdateCurveConfigurationBody;
import com.sonsenim.sonsenimbackend.mappers.CardsMapper;
import com.sonsenim.sonsenimbackend.model.*;
import com.sonsenim.sonsenimbackend.model.dto.CardDTO;
import com.sonsenim.sonsenimbackend.repositories.CardsRepository;
import com.sonsenim.sonsenimbackend.repositories.DecksRepository;
import com.sonsenim.sonsenimbackend.repositories.UserCardsProgressionHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CardsService {

    private final CardsRepository cardsRepository;
    private final DecksRepository decksRepository;
    private final UserCardsProgressionHistoryRepository userCardsProgressionHistoryRepository;

    private final float[][] paceRepetitionMatrix = {
            {360, 360}, {180f, 360f}, {90f, 180f}, {30f, 90f}, {14f, 30f},
            {7f, 14f}, {3f, 7f}, {1f, 3f}, {0.5f, 1f}, {0.25f, 0.5f}, {0f, 0.25f}
    };


    public CardsService(CardsRepository cardsRepository,
                        DecksRepository decksRepository,
                        UserCardsProgressionHistoryRepository userCardsProgressionHistoryRepository) {
        this.cardsRepository = cardsRepository;
        this.decksRepository = decksRepository;
        this.userCardsProgressionHistoryRepository = userCardsProgressionHistoryRepository;
    }

    public List<CardDTO> getAllUserCardsFromDeck(Long deckId, LocalUser user) {
        List<Card> cards = cardsRepository.findByDeck_IdAndDeck_Groups_LocalUser(deckId, user);
        return CardsMapper.toCardDTO(cards);
    }

    public List<CardDTO> getCardsToRepeatFromDeck(Long deckId, LocalUser user) {
          List<Card> cards = cardsRepository.findCardsDueBeforeTodayOrNull(deckId, user, LocalDateTime.now());
        return CardsMapper.toCardDTO(cards);
    }

    public void addNewCardToDeck(Long deckId, LocalUser user, CardConfigurationBody cardConfiguration)  {
        Deck deck = decksRepository.findByIdAndGroups_LocalUser(deckId, user);
        Card card = new Card();
        card.setPrimaryWord(cardConfiguration.getPrimaryWord());
        card.setDefinition(cardConfiguration.getDefinition());
        card.setExplanation(cardConfiguration.getExplanation());
        card.setDeck(deck);

        cardsRepository.save(card);
    }

    public CardDTO updateCard(Long deckId, Long cardId, LocalUser user, CardConfigurationBody cardConfiguration) {
        Card existingCard = cardsRepository.findByIdAndDeck_IdAndDeck_Groups_LocalUser(cardId, deckId, user);
        existingCard.setPrimaryWord(cardConfiguration.getPrimaryWord());
        existingCard.setDefinition(cardConfiguration.getDefinition());
        existingCard.setExplanation(cardConfiguration.getExplanation());
        cardsRepository.save(existingCard);
        return CardsMapper.toDto(existingCard);
    }

    public Card updateCardTimeIntervalByUserDecision(Card existingCard, UpdateCurveConfigurationBody configuration) {
        boolean isAnswerRight = configuration.isAnswerIsRight();
        float currentIntervalStr = existingCard.getIntervalStrength();

        if (isAnswerRight) {
            for (float[] range : paceRepetitionMatrix) {
                if (currentIntervalStr >= range[0] && (currentIntervalStr < range[1] || currentIntervalStr == 360)) {
                    existingCard.setIntervalStrength(range[1]);

                    if (range[1] < 1f) {
                        long hours = (long) (range[1] * 24);
                        existingCard.setNextRepetitionTime(LocalDateTime.now().plusHours(hours));
                    } else {
                        existingCard.setNextRepetitionTime(LocalDateTime.now().plusDays((long) range[1]));
                    }
                    break;
                }
            }
        }
        if (!isAnswerRight) {
            if (currentIntervalStr == 0) {
                existingCard.setIntervalStrength(0.125f);
                existingCard.setNextRepetitionTime(LocalDateTime.now().plusHours(3));

            }

            if (currentIntervalStr >= 0.125f) {
                float newIntervalStrength = Math.max(0.125f, currentIntervalStr / 2);
                existingCard.setIntervalStrength(newIntervalStrength);

                if (existingCard.getNextRepetitionTime() == null) {
                    existingCard.setNextRepetitionTime(LocalDateTime.now().plusDays((long) newIntervalStrength));
                }

                if (newIntervalStrength < 1f) {
                    long hours = (long) (newIntervalStrength * 24);
                    existingCard.setNextRepetitionTime(LocalDateTime.now().plusHours(hours));
                } else {
                    existingCard.setNextRepetitionTime(LocalDateTime.now().plusDays((long) newIntervalStrength));
                }
            }
        }

        cardsRepository.save(existingCard);
        return existingCard;
    }

    // TODO: Think about move in cardProgressionHistory service or smth like that...
    public void updateUserCardsHistory(Card existingCard) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        Groups cardGroup = existingCard.getDeck().getGroup();
        Optional<UserCardsProgressionHistory> actualHistoryData = userCardsProgressionHistoryRepository.findByCreatedDateGreaterThanEqualAndGroup_Id(today, cardGroup.getId());


        UserCardStats stats = getUserCardStats(cardGroup.getId());

        actualHistoryData.ifPresentOrElse(
                history -> updateHistory(history, stats),
                () -> createAndSaveNewHistory(cardGroup, stats)
        );
    }

    public void removeCardFromDeck(LocalUser user, Long deckId, Long cardId) {
        Card card = cardsRepository.findByIdAndDeck_IdAndDeck_Groups_LocalUser(cardId, deckId, user);
        cardsRepository.delete(card);
    }

    public long getTotalNumberOfCardsFromGroup(LocalUser user, Long groupId) {
        return cardsRepository.countByDeck_Groups_IdAndDeck_Groups_LocalUser(groupId, user);
    }


    private void updateHistory(UserCardsProgressionHistory history, UserCardStats stats) {
        history.setVeryLowIndicationCount((int) stats.veryLowInd);
        history.setLowIndicationCount((int) stats.lowInd);
        history.setMidIndicationCount((int) stats.midInd);
        history.setHighIndicationCount((int) stats.highInd);
        userCardsProgressionHistoryRepository.save(history);
    }

    private void createAndSaveNewHistory(Groups group, UserCardStats stats) {
        UserCardsProgressionHistory newHistory = new UserCardsProgressionHistory();
        newHistory.setGroup(group);
        newHistory.setVeryLowIndicationCount((int) stats.veryLowInd);
        newHistory.setLowIndicationCount((int) stats.lowInd);
        newHistory.setMidIndicationCount((int) stats.midInd);
        newHistory.setHighIndicationCount((int) stats.highInd);
        userCardsProgressionHistoryRepository.save(newHistory);
    }

    private UserCardStats getUserCardStats(Long groupId) {
        long veryLowInd = cardsRepository.countByDeck_Groups_IdAndIntervalStrengthBetween(groupId, 0f, 0.49f);
        long lowInd = cardsRepository.countByDeck_Groups_IdAndIntervalStrengthBetween(groupId, 0.5f, 6.99f);
        long midInd = cardsRepository.countByDeck_Groups_IdAndIntervalStrengthBetween(groupId, 7f, 89.9f);
        long highInd = cardsRepository.countByDeck_Groups_IdAndIntervalStrengthGreaterThanEqual(groupId, 90f);

        return new UserCardStats(veryLowInd, lowInd, midInd, highInd);
    }

    private static class UserCardStats {
        long veryLowInd;
        long lowInd;
        long midInd;
        long highInd;

        UserCardStats(long veryLowInd, long lowInd, long midInd, long highInd) {
            this.veryLowInd = veryLowInd;
            this.lowInd = lowInd;
            this.midInd = midInd;
            this.highInd = highInd;
        }
    }
}

