package com.sonsenim.sonsenimbackend.repositories;
import com.sonsenim.sonsenimbackend.model.UserCardsProgressionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserCardsProgressionHistoryRepository extends JpaRepository<UserCardsProgressionHistory, Long> {

    Optional<UserCardsProgressionHistory> findByCreatedDateGreaterThanEqualAndGroup_Id(LocalDateTime createdDate, Long id);

    List<UserCardsProgressionHistory> findByGroup_IdAndCreatedDateBetweenOrderByCreatedDateAsc(Long id, LocalDateTime createdDateStart, LocalDateTime createdDateEnd);
}
