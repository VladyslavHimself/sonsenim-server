package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.Groups;
import com.sonsenim.sonsenimbackend.model.dto.UserGroupInfoDTO;
import com.sonsenim.sonsenimbackend.repositories.DecksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserGroupInfoMapper {

    private static DecksRepository decksRepository;

    @Autowired
    public UserGroupInfoMapper(DecksRepository decksRepository) {
        this.decksRepository = decksRepository;
    }

    public static List<UserGroupInfoDTO> toUserGroupInfoDTO(List<Groups> groups) {
        return groups.stream()
                .map(UserGroupInfoMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static UserGroupInfoDTO toDTO(Groups group) {
        UserGroupInfoDTO userGroupInfoList = new UserGroupInfoDTO();

        long decksInGroup = decksRepository.countByGroups_Id(group.getId());

        userGroupInfoList.setGroupId(group.getId());
        userGroupInfoList.setGroupName(group.getGroupName());
        userGroupInfoList.setDecksCount(decksInGroup);

        return userGroupInfoList;
    }

}
