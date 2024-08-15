package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.Card;
import com.sonsenim.sonsenimbackend.model.dto.CardDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CardsMapper {

    public static List<CardDTO> toCardDTO(List<Card> cards) {
        return cards.stream().map(CardsMapper::toDto).collect(Collectors.toList());
    }

    public static CardDTO toDto(Card card) {
       CardDTO dto = new CardDTO();

       dto.setCardId(card.getId());
       dto.setPrimaryWord(card.getPrimaryWord());
       dto.setDefinition(card.getDefinition());
       dto.setExplanation(card.getExplanation());
       dto.setNextRepetitionTime(card.getNextRepetitionTime());
       dto.setIntervalDaysStrength(card.getIntervalDaysStrength());
       dto.setCreatedAt(card.getCreatedAt());
       return dto;
    };
}
