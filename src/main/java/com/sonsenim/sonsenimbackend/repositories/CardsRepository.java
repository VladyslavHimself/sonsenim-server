package com.sonsenim.sonsenimbackend.repositories;

import com.sonsenim.sonsenimbackend.model.Card;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


public interface CardsRepository extends JpaRepository<Card, Long> {

    List<Card> findByDeck_IdAndDeck_Groups_LocalUser(Long id, LocalUser localUser);

    @Query("SELECT c FROM Card c WHERE c.deck.id = :deckId AND c.deck.groups.localUser = :localUser AND (c.nextRepetitionTime IS NULL OR c.nextRepetitionTime < :todayStart)")
    List<Card> findCardsDueBeforeTodayOrNull(@Param("deckId") Long deckId, @Param("localUser") LocalUser localUser, @Param("todayStart") LocalDateTime todayStart);

    Card findByIdAndDeck_IdAndDeck_Groups_LocalUser(Long id, Long id1, LocalUser localUser);



    @Transactional
    @Modifying
    @Query("update Card c set c.nextRepetitionTime = ?1 where c.id = ?2")
    int updateNextRepetitionTimeById(LocalDateTime nextRepetitionTime, Long id);

    long countByDeck_Groups_IdAndDeck_Groups_LocalUser(Long id, LocalUser localUser);

    long countByDeck_IdAndDeck_Groups_LocalUser(Long id, LocalUser localUser);

}
