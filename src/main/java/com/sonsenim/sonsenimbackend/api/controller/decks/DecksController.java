package com.sonsenim.sonsenimbackend.api.controller.decks;

import com.sonsenim.sonsenimbackend.api.model.DeckConfigurationBody;
import com.sonsenim.sonsenimbackend.model.Deck;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.DeckDTO;
import com.sonsenim.sonsenimbackend.service.DecksService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/decks")
public class DecksController {

    private final DecksService decksService;

    public DecksController(DecksService decksService) {
        this.decksService = decksService;
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<DeckDTO>> getGroupDecks(@AuthenticationPrincipal LocalUser user, @PathVariable Long groupId) {
        List<DeckDTO> decks = decksService.getUserDecksByGroupId(user, groupId);
        return ResponseEntity.ok(decks);
    }

    @PostMapping("/{groupId}")
    public ResponseEntity<String> addDeckToGroup(
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long groupId,
            @Valid @RequestBody DeckConfigurationBody deckConfiguration) {
        try {
            decksService.createNewDeck(user, groupId, deckConfiguration);
            return ResponseEntity.ok("Deck created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create deck" + e.getMessage());
        }

    }
}
