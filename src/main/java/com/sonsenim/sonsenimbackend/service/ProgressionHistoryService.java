package com.sonsenim.sonsenimbackend.service;

import com.sonsenim.sonsenimbackend.model.Card;
import com.sonsenim.sonsenimbackend.model.Groups;
import com.sonsenim.sonsenimbackend.model.helpers.DailyHistoryResponse;
import com.sonsenim.sonsenimbackend.model.UserCardsProgressionHistory;
import com.sonsenim.sonsenimbackend.repositories.CardsRepository;
import com.sonsenim.sonsenimbackend.repositories.UserCardsProgressionHistoryRepository;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
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
    private final CardsRepository cardsRepository;

    public ProgressionHistoryService(UserCardsProgressionHistoryRepository userCardsProgressionHistoryRepository, CardsRepository cardsRepository) {
        this.userCardsProgressionHistoryRepository = userCardsProgressionHistoryRepository;
        this.cardsRepository = cardsRepository;
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

    public void updateUserCardsHistory(Card existingCard) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        Groups cardGroup = existingCard.getDeck().getGroup();
        Optional<UserCardsProgressionHistory> actualHistoryData = userCardsProgressionHistoryRepository.findByCreatedDateGreaterThanEqualAndGroup_Id(today, cardGroup.getId());


        UserCardStats stats = getUserCardStats(cardGroup.getId());

        actualHistoryData.ifPresentOrElse(
                history -> updateHistory(history, stats),
                () -> createAndSaveNewHistory(cardGroup, stats)
        );
    }

    private void updateHistory(UserCardsProgressionHistory history, UserCardStats stats) {
        history.setVeryLowIndicationCount((int) stats.veryLowInd);
        history.setLowIndicationCount((int) stats.lowInd);
        history.setMidIndicationCount((int) stats.midInd);
        history.setHighIndicationCount((int) stats.highInd);
        userCardsProgressionHistoryRepository.save(history);
    }

    private void createAndSaveNewHistory(Groups group, UserCardStats stats) {
        UserCardsProgressionHistory newHistory = new UserCardsProgressionHistory();
        newHistory.setGroup(group);
        newHistory.setVeryLowIndicationCount((int) stats.veryLowInd);
        newHistory.setLowIndicationCount((int) stats.lowInd);
        newHistory.setMidIndicationCount((int) stats.midInd);
        newHistory.setHighIndicationCount((int) stats.highInd);
        userCardsProgressionHistoryRepository.save(newHistory);
    }

    private UserCardStats getUserCardStats(Long groupId) {
        long veryLowInd = cardsRepository.countByDeck_Groups_IdAndIntervalStrengthBetween(groupId, 0f, 0.49f);
        long lowInd = cardsRepository.countByDeck_Groups_IdAndIntervalStrengthBetween(groupId, 0.5f, 6.99f);
        long midInd = cardsRepository.countByDeck_Groups_IdAndIntervalStrengthBetween(groupId, 7f, 89.9f);
        long highInd = cardsRepository.countByDeck_Groups_IdAndIntervalStrengthGreaterThanEqual(groupId, 90f);

        return new UserCardStats(veryLowInd, lowInd, midInd, highInd);
    }

    private static class UserCardStats {
        long veryLowInd;
        long lowInd;
        long midInd;
        long highInd;

        UserCardStats(long veryLowInd, long lowInd, long midInd, long highInd) {
            this.veryLowInd = veryLowInd;
            this.lowInd = lowInd;
            this.midInd = midInd;
            this.highInd = highInd;
        }
    }
}
