package com.sonsenim.sonsenimbackend.repositories;

import com.sonsenim.sonsenimbackend.model.Groups;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GroupsRepository extends JpaRepository<Groups, Long> {

    List<Groups> findByLocalUser_Id(Long id);


    boolean existsByGroupNameIgnoreCaseAndLocalUser_Id(String groupName, Long id);

    Groups findByIdAndLocalUser_Id(Long groupId, Long userId);

    long deleteByLocalUserAndId(LocalUser localUser, Long id);

}
