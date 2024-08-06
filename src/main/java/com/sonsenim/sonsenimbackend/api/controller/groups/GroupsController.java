package com.sonsenim.sonsenimbackend.api.controller.groups;

import com.sonsenim.sonsenimbackend.api.model.DeckConfigurationBody;
import com.sonsenim.sonsenimbackend.api.model.GroupConfigurationBody;
import com.sonsenim.sonsenimbackend.exception.GroupAlreadyExistsException;
import com.sonsenim.sonsenimbackend.mappers.GroupStatisticsMapper;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.GroupDTO;
import com.sonsenim.sonsenimbackend.model.dto.GroupStatisticsDTO;
import com.sonsenim.sonsenimbackend.model.dto.UserGroupInfoDTO;
import com.sonsenim.sonsenimbackend.service.CardsService;
import com.sonsenim.sonsenimbackend.service.DecksService;
import com.sonsenim.sonsenimbackend.service.GroupsService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupsController {

    private final GroupsService groupsService;
    private final DecksService decksService;
    private final CardsService cardsService;

    public GroupsController(GroupsService groupsService, DecksService decksService, CardsService cardsService) {
        this.groupsService = groupsService;
        this.decksService = decksService;
        this.cardsService = cardsService;
    }

    @GetMapping("/")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@AuthenticationPrincipal LocalUser user) {
        List<GroupDTO> groups = groupsService.getUserGroups(user.getId());
        return ResponseEntity.ok(groups);
    }


    // TODO: Delete 'add' in endpoint path
    @PostMapping("/add/{groupName}")
    public List<GroupDTO> addUserGroup(@AuthenticationPrincipal LocalUser user, @PathVariable String groupName) throws GroupAlreadyExistsException {
        groupsService.addNewUserGroup(user, groupName);
        return groupsService.getUserGroups(user.getId());
    }

    @Transactional
    @DeleteMapping("/{groupId}")
    public ResponseEntity deleteUserGroup(@AuthenticationPrincipal LocalUser user, @PathVariable Long groupId) {
        try {
            groupsService.deleteUserGroup(user, groupId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDTO> editUserGroup(@AuthenticationPrincipal LocalUser user, @PathVariable Long groupId, @RequestBody GroupConfigurationBody groupConfiguration) {
        try {
            GroupDTO group = groupsService.editUserGroup(user, groupId, groupConfiguration);
            return ResponseEntity.ok().body(group);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 1. view total cards by group +
    // 2. view total decks by group +
    // 3. view stats by group (later)
    // 4. (Optional) add possibility to view above params in all groups
    @GetMapping("/stats/{groupId}")
    public ResponseEntity<GroupStatisticsDTO> getGroupStatistics(@AuthenticationPrincipal LocalUser user, @PathVariable Long groupId) {
        int totalDecksInGroup = decksService.getUserDecksByGroupId(user, groupId).size();
        long totalCardsInGroup = cardsService.getTotalNumberOfCardsFromGroup(user, groupId);

        GroupStatisticsDTO groupStatisticsDTO = GroupStatisticsMapper.toDTO(totalDecksInGroup, (int) totalCardsInGroup);

        return ResponseEntity.ok().body(groupStatisticsDTO);
    }

    @GetMapping("/user-groups-info")
    public ResponseEntity<List<UserGroupInfoDTO>> getUserGroupsInfo(@AuthenticationPrincipal LocalUser user) {
       try {
           List<UserGroupInfoDTO> groupsWithInfo = groupsService.getUserGroupsWithInfo(user.getId());

           return ResponseEntity.ok().body(groupsWithInfo);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }
}
