package com.sonsenim.sonsenimbackend.service;

import com.sonsenim.sonsenimbackend.api.model.CardConfigurationBody;
import com.sonsenim.sonsenimbackend.mappers.CardsMapper;
import com.sonsenim.sonsenimbackend.model.Card;
import com.sonsenim.sonsenimbackend.model.Deck;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.CardDTO;
import com.sonsenim.sonsenimbackend.repositories.CardsRepository;
import com.sonsenim.sonsenimbackend.repositories.DecksRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CardsService {

    private final CardsRepository cardsRepository;
    private final DecksRepository decksRepository;

    public CardsService(CardsRepository cardsRepository, DecksRepository decksRepository) {
        this.cardsRepository = cardsRepository;
        this.decksRepository = decksRepository;
    }

    public List<CardDTO> getAllUserCardsFromDeck(Long deckId, LocalUser user) {
        List<Card> cards = cardsRepository.findByDeck_IdAndDeck_Groups_LocalUser(deckId, user);
        return CardsMapper.toCardDTO(cards);
    }

    public List<CardDTO> getCardsToRepeatFromDeck(Long deckId, LocalUser user) {
        List<Card> cards = cardsRepository.findByDeck_IdAndDeck_Groups_LocalUserAndDeck_Cards_NextRepetitionTimeLessThan(deckId, user, LocalDateTime.now());
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

    public void updateCardTimeCurve(Long deckId, Long cardId, LocalUser user, LocalDateTime newRepetitionTime) {
        Card existingCard = cardsRepository.findByIdAndDeck_IdAndDeck_Groups_LocalUser(cardId, deckId, user);
        cardsRepository.updateNextRepetitionTimeById(newRepetitionTime, existingCard.getId());
    }

    public void removeCardFromDeck(LocalUser user, Long deckId, Long cardId) {
        Card card = cardsRepository.findByIdAndDeck_IdAndDeck_Groups_LocalUser(cardId, deckId, user);
        cardsRepository.delete(card);
    }
}
