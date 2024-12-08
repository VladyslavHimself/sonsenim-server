package com.sonsenim.sonsenimbackend.api.controller.progressionHistory;


import com.sonsenim.sonsenimbackend.mappers.UserCardsProgressionHistoryMapper;
import com.sonsenim.sonsenimbackend.model.dto.UserCardsProgressionHistoryDTO;
import com.sonsenim.sonsenimbackend.model.helpers.DailyHistoryResponse;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.UserCardsProgressionHistory;
import com.sonsenim.sonsenimbackend.service.ProgressionHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/history")
public class ProgressionHistoryController {

    private final ProgressionHistoryService progressionHistoryService;
    private final UserCardsProgressionHistoryMapper userCardsProgressionHistoryMapper;

    public ProgressionHistoryController(ProgressionHistoryService progressionHistoryService, UserCardsProgressionHistoryMapper userCardsProgressionHistoryMapper) {
        this.progressionHistoryService = progressionHistoryService;
        this.userCardsProgressionHistoryMapper = userCardsProgressionHistoryMapper;
    }


    @GetMapping("/{groupId}")
    public ResponseEntity<List<UserCardsProgressionHistoryDTO>> getCardsIntervalHistory(@AuthenticationPrincipal LocalUser user, @PathVariable Long groupId) {
        LocalDateTime startDay = LocalDateTime.now().minusDays(7);
        LocalDateTime endDay = LocalDateTime.now().plusDays(1);

        Map<LocalDate, UserCardsProgressionHistory> historyMap =
                progressionHistoryService.getMappedProgressionHistory(groupId, startDay, endDay);

        List<DailyHistoryResponse> fulfilledHistory = progressionHistoryService.getFulfilledProgressionHistory(
                groupId,
                startDay,
                historyMap
        );

        List<UserCardsProgressionHistoryDTO> historyList = userCardsProgressionHistoryMapper.toStatsDTO(fulfilledHistory);
        return ResponseEntity.ok(historyList);
    }
}
