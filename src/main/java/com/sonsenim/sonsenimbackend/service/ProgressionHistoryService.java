package com.sonsenim.sonsenimbackend.service;

import com.sonsenim.sonsenimbackend.mappers.DailyHistoryResponse;
import com.sonsenim.sonsenimbackend.model.UserCardsProgressionHistory;
import com.sonsenim.sonsenimbackend.repositories.UserCardsProgressionHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProgressionHistoryService {

    private final UserCardsProgressionHistoryRepository userCardsProgressionHistoryRepository;

    public ProgressionHistoryService(UserCardsProgressionHistoryRepository userCardsProgressionHistoryRepository) {
        this.userCardsProgressionHistoryRepository = userCardsProgressionHistoryRepository;
    }


    public Map<LocalDate, UserCardsProgressionHistory> getMappedProgressionHistory(Long groupId, LocalDateTime startDay, LocalDateTime endDay) {
        List<UserCardsProgressionHistory> rawHistory =
                userCardsProgressionHistoryRepository.findByGroup_IdAndCreatedDateBetweenOrderByCreatedDateAsc(groupId, startDay, endDay);

        return rawHistory.stream()
                .collect(Collectors.toMap(
                        record -> record.getCreatedDate().toLocalDate(),
                        record -> record
                ));
    }

    public List<DailyHistoryResponse> getFulfilledProgressionHistory(Long groupId, LocalDateTime startDay, Map<LocalDate, UserCardsProgressionHistory> historyMap) {
        return IntStream.rangeClosed(1, 7)
                .mapToObj(startDay::plusDays)
                .map(date -> {
                    Optional<UserCardsProgressionHistory> optionalRecord = Optional.ofNullable(historyMap.get(date.toLocalDate()));
                    return optionalRecord
                            .map(record -> new DailyHistoryResponse(
                                    date,
                                    record.getVeryLowIndicationCount(),
                                    record.getLowIndicationCount(),
                                    record.getMidIndicationCount(),
                                    record.getHighIndicationCount()
                            ))
                            .orElseGet(() -> {
                                List<UserCardsProgressionHistory> earlierSnapshots = userCardsProgressionHistoryRepository.findByGroup_IdAndCreatedDateLessThanEqualOrderByCreatedDateDesc(groupId, date);
                                if (!earlierSnapshots.isEmpty()) {
                                    UserCardsProgressionHistory lastRecord = earlierSnapshots.get(0);
                                    return new DailyHistoryResponse(
                                            date,
                                            lastRecord.getVeryLowIndicationCount(),
                                            lastRecord.getLowIndicationCount(),
                                            lastRecord.getMidIndicationCount(),
                                            lastRecord.getHighIndicationCount()
                                    );
                                }

                                return new DailyHistoryResponse(date, 0, 0, 0, 0);
                            });
                })
                .collect(Collectors.toList());
    }
}
