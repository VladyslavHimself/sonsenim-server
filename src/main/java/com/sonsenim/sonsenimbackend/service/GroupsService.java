package com.sonsenim.sonsenimbackend.service;

import com.sonsenim.sonsenimbackend.exception.GroupAlreadyExistsException;
import com.sonsenim.sonsenimbackend.mappers.GroupsMapper;
import com.sonsenim.sonsenimbackend.mappers.UserGroupInfoMapper;
import com.sonsenim.sonsenimbackend.model.Groups;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.GroupDTO;
import com.sonsenim.sonsenimbackend.model.dto.UserGroupInfoDTO;
import com.sonsenim.sonsenimbackend.repositories.GroupsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsService {

    private final GroupsRepository groupsRepository;

    public GroupsService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    public List<GroupDTO> getUserGroups(Long userId) {
        try {
            List<Groups> groups = groupsRepository.findByLocalUser_Id(userId);
            return GroupsMapper.toGroupsDTO(groups);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long deleteGroup(LocalUser user, Long groupId) {
        return groupsRepository.deleteByLocalUserAndId(user, groupId);
    }

    public List<UserGroupInfoDTO> getUserGroupsWithInfo(Long userId) {
        try {
            List<Groups> groups = groupsRepository.findByLocalUser_Id(userId);
            return UserGroupInfoMapper.toUserGroupInfoDTO(groups);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewUserGroup(LocalUser user, String groupName) throws GroupAlreadyExistsException {
        if (groupsRepository.existsByGroupNameIgnoreCaseAndLocalUser_Id(groupName, user.getId())) {
            throw new GroupAlreadyExistsException();
        }

        Groups group = new Groups();
        group.setGroupName(groupName);
        group.setLocalUser(user);
        groupsRepository.save(group);
    }

    public Groups getUserGroupById(LocalUser user, Long groupId) throws Exception {
        return groupsRepository.findByIdAndLocalUser_Id(groupId, user.getId());
    }
}
