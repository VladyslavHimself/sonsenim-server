package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.UserCardsProgressionHistory;
import com.sonsenim.sonsenimbackend.model.dto.UserCardsProgressionHistoryDTO;
import java.util.List;
import java.util.stream.Collectors;

public class UserCardsProgressionHistoryMapper {

    public static List<UserCardsProgressionHistoryDTO> toStatsDTO(List<UserCardsProgressionHistory> history) {
        return history.stream().map(UserCardsProgressionHistoryMapper::toDto).collect(Collectors.toList());
    }

    public static UserCardsProgressionHistoryDTO toDto(UserCardsProgressionHistory history) {
       UserCardsProgressionHistoryDTO progressionHistoryDTO = new UserCardsProgressionHistoryDTO();

       progressionHistoryDTO.setDate(history.getCreatedDate().toLocalDate());
       progressionHistoryDTO.setGroupId(String.valueOf(history.getGroup().getId()));
       progressionHistoryDTO.setHigh(history.getHighIndicationCount());
       progressionHistoryDTO.setMid(history.getMidIndicationCount());
       progressionHistoryDTO.setLow(history.getLowIndicationCount());
       progressionHistoryDTO.setVlow(history.getVeryLowIndicationCount());

       return progressionHistoryDTO;
    };
}
