package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.Deck;
import com.sonsenim.sonsenimbackend.model.dto.DeckDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DecksMapper {

    public static List<DeckDTO> toDeckDTOs(List<Deck> decks) {
        return decks.stream().map(DecksMapper::toDTO).collect(Collectors.toList());
    }

    public static DeckDTO toDTO(Deck deck) {
        DeckDTO dto = new DeckDTO();
        dto.setId(deck.getId());
        dto.setDeckName(deck.getDeckName());
        dto.setFlashcardNormal(deck.getIsFlashcardNormal());
        dto.setFlashcardReversed(deck.getIsFlashcardReversed());
        dto.setRandomizedOrder(deck.getRandomizedOrder());
        dto.setFlashcardTyping(deck.getIsTyping());
        dto.setCreatedAt(deck.getCreatedAt());
        return dto;
    }


}
