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

    public void updateCardTimeIntervalByUserDecision(Card existingCard, UpdateCurveConfigurationBody configuration) {
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
    }

    public void removeCardFromDeck(LocalUser user, Long deckId, Long cardId) {
        Card card = cardsRepository.findByIdAndDeck_IdAndDeck_Groups_LocalUser(cardId, deckId, user);
        cardsRepository.delete(card);
    }

    public long getTotalNumberOfCardsFromGroup(LocalUser user, Long groupId) {
        return cardsRepository.countByDeck_Groups_IdAndDeck_Groups_LocalUser(groupId, user);
    }

}

