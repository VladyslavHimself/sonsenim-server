package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.UserCardsProgressionHistory;
import com.sonsenim.sonsenimbackend.model.dto.UserCardsProgressionHistoryDTO;
import com.sonsenim.sonsenimbackend.model.helpers.DailyHistoryResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserCardsProgressionHistoryMapper {


    public List<UserCardsProgressionHistoryDTO> toStatsDTO(List<DailyHistoryResponse> history) {
        return history.stream().map(UserCardsProgressionHistoryMapper::toDto).collect(Collectors.toList());
    }

    public static UserCardsProgressionHistoryDTO toDto(DailyHistoryResponse history) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");

       UserCardsProgressionHistoryDTO progressionHistoryDTO = new UserCardsProgressionHistoryDTO();

       progressionHistoryDTO.setDate(history.getDate().format(formatter));
       progressionHistoryDTO.setHighIndicationCount(history.getHighIndicationCount());
       progressionHistoryDTO.setMidIndicationCount(history.getMidIndicationCount());
       progressionHistoryDTO.setLowIndicationCount(history.getLowIndicationCount());
       progressionHistoryDTO.setVeryLowIndicationCount(history.getVeryLowIndicationCount());

       return progressionHistoryDTO;
    };
}
