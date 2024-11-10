package com.sonsenim.sonsenimbackend.repositories;

import com.sonsenim.sonsenimbackend.model.Deck;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecksRepository extends JpaRepository<Deck, Long> {

    List<Deck> findByGroups_IdAndGroups_LocalUser_Id(Long id, Long id1);

    Deck findByIdAndGroups_LocalUser(Long id, LocalUser localUser);

    long countByGroups_Id(Long id);

    long deleteByIdAndGroups_LocalUser(Long id, LocalUser localUser);

    long countByGroups_LocalUser_Id(Long id);
}
