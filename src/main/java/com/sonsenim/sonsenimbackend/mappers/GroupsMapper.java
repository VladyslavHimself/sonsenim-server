package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.Groups;
import com.sonsenim.sonsenimbackend.model.dto.GroupDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GroupsMapper {
    public static List<GroupDTO> toGroupsDTO(List<Groups> groups) {
            return groups.stream()
                    .map(GroupsMapper::toDTO)
                    .collect(Collectors.toList());
    }

    public static GroupDTO toDTO(Groups group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setGroupName(group.getGroupName());
        return dto;
    }
}
