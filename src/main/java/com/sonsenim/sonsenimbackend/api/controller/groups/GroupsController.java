package com.sonsenim.sonsenimbackend.api.controller.groups;

import com.sonsenim.sonsenimbackend.exception.GroupAlreadyExistsException;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.GroupDTO;
import com.sonsenim.sonsenimbackend.service.GroupsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    private final GroupsService groupsService;

    public GroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping("/")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@AuthenticationPrincipal LocalUser user) {
        List<GroupDTO> groups = groupsService.getUserGroups(user.getId());
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/add/{groupName}")
    public List<GroupDTO> addUserGroup(@AuthenticationPrincipal LocalUser user, @PathVariable String groupName) throws GroupAlreadyExistsException {
        groupsService.addNewUserGroup(user, groupName);
        return groupsService.getUserGroups(user.getId());
    }
}
