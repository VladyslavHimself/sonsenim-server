package com.sonsenim.sonsenimbackend.repositories;

import com.sonsenim.sonsenimbackend.model.Card;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


public interface CardsRepository extends JpaRepository<Card, Long> {

    List<Card> findByDeck_IdAndDeck_Groups_LocalUser(Long id, LocalUser localUser);

    List<Card> findByDeck_IdAndDeck_Groups_LocalUserAndDeck_Cards_NextRepetitionTimeLessThan(Long id, LocalUser localUser, @Nullable LocalDateTime nextRepetitionTime);

    Card findByIdAndDeck_IdAndDeck_Groups_LocalUser(Long id, Long id1, LocalUser localUser);



    @Transactional
    @Modifying
    @Query("update Card c set c.nextRepetitionTime = ?1 where c.id = ?2")
    int updateNextRepetitionTimeById(LocalDateTime nextRepetitionTime, Long id);
}
