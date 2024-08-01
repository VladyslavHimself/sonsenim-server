package com.sonsenim.sonsenimbackend.api.controller.groups;

import com.sonsenim.sonsenimbackend.exception.GroupAlreadyExistsException;
import com.sonsenim.sonsenimbackend.mappers.GroupStatisticsMapper;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.GroupDTO;
import com.sonsenim.sonsenimbackend.model.dto.GroupStatisticsDTO;
import com.sonsenim.sonsenimbackend.service.CardsService;
import com.sonsenim.sonsenimbackend.service.DecksService;
import com.sonsenim.sonsenimbackend.service.GroupsService;
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

    @PostMapping("/add/{groupName}")
    public List<GroupDTO> addUserGroup(@AuthenticationPrincipal LocalUser user, @PathVariable String groupName) throws GroupAlreadyExistsException {
        groupsService.addNewUserGroup(user, groupName);
        return groupsService.getUserGroups(user.getId());
    }

    // 1. view total cards by group +
    // 2. view total decks by group +
    // 3. view stats by group (later)
    // 4. (Optional) add possibility to view above params in all groups
    @GetMapping("/stats/{groupId}")
    public ResponseEntity<GroupStatisticsDTO> getGroupStatistics(@AuthenticationPrincipal LocalUser user, @PathVariable Long groupId) {
        int totalDecksInGroup = decksService.getUserDecksByGroupId(user, groupId).size();
        long totalCardsInGroup = cardsService.getTotalNumberOfCardsFromGroup(user);

        GroupStatisticsDTO groupStatisticsDTO = GroupStatisticsMapper.toDTO(totalDecksInGroup, (int) totalCardsInGroup);

        return ResponseEntity.ok().body(groupStatisticsDTO);
    }
}
