package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.Deck;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.AggregatedDeckDTO;
import com.sonsenim.sonsenimbackend.repositories.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AggregatedDeckMapper {

    private static CardsRepository cardsRepository;

    @Autowired
    public AggregatedDeckMapper(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    public static List<AggregatedDeckDTO> toAggregatedDeckDTOs(List<Deck> decks, LocalUser user) {
        return decks.stream().map((deck) -> toDTO(deck, user)).collect(Collectors.toList());
    }

    @SuppressWarnings("DuplicatedCode")
    public static AggregatedDeckDTO toDTO(Deck deck, LocalUser user) {
        AggregatedDeckDTO aggregatedDeckDTO = new AggregatedDeckDTO();

        long cardsInDeck = cardsRepository.countByDeck_IdAndDeck_Groups_LocalUser(deck.getId(), user);
        long dueCardsInDeck = cardsRepository.countCardsDueBeforeTodayOrNull(deck.getId(), user, LocalDateTime.now());

        aggregatedDeckDTO.setId(deck.getId());
        aggregatedDeckDTO.setDeckName(deck.getDeckName());
        aggregatedDeckDTO.setFlashcardNormal(deck.getIsFlashcardNormal());
        aggregatedDeckDTO.setFlashcardReversed(deck.getIsFlashcardReversed());
        aggregatedDeckDTO.setRandomizedOrder(deck.getRandomizedOrder());
        aggregatedDeckDTO.setFlashcardTyping(deck.getIsTyping());
        aggregatedDeckDTO.setCreatedAt(deck.getCreatedAt());
        aggregatedDeckDTO.setCardsInDeckTotal(cardsInDeck);
        aggregatedDeckDTO.setDueCardsInDeck(dueCardsInDeck);

        return aggregatedDeckDTO;
    }
}
