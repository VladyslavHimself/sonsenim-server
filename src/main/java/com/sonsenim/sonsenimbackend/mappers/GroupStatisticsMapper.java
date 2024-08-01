package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.dto.GroupStatisticsDTO;

public class GroupStatisticsMapper {
    public static GroupStatisticsDTO toDTO(int totalDecks, long totalCards) {
        GroupStatisticsDTO groupStatisticsDTO = new GroupStatisticsDTO();
        groupStatisticsDTO.setCardsTotal(totalCards);
        groupStatisticsDTO.setDecksTotal(totalDecks);
        return groupStatisticsDTO;
    };
}
