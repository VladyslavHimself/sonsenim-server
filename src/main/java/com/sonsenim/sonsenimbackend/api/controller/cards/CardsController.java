package com.sonsenim.sonsenimbackend.api.controller.cards;

import com.sonsenim.sonsenimbackend.api.model.CardConfigurationBody;
import com.sonsenim.sonsenimbackend.api.model.UpdateCurveConfigurationBody;
import com.sonsenim.sonsenimbackend.mappers.CardsMapper;
import com.sonsenim.sonsenimbackend.model.Card;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.CardDTO;
import com.sonsenim.sonsenimbackend.repositories.CardsRepository;
import com.sonsenim.sonsenimbackend.repositories.UserCardsProgressionHistoryRepository;
import com.sonsenim.sonsenimbackend.service.CardsService;
import com.sonsenim.sonsenimbackend.service.ProgressionHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cards")
public class CardsController {

    private final CardsService cardsService;
    private final CardsRepository cardsRepository;
    private final ProgressionHistoryService progressionHistoryService;

    public CardsController(ProgressionHistoryService progressionHistoryService, CardsService cardsService, CardsRepository cardsRepository, UserCardsProgressionHistoryRepository userCardsProgressionHistoryRepository) {
        this.cardsService = cardsService;
        this.cardsRepository = cardsRepository;
        this.progressionHistoryService = progressionHistoryService;
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<List<CardDTO>> getCardsFromDeck(@AuthenticationPrincipal LocalUser user, @PathVariable Long deckId) {
        List<CardDTO> cards = cardsService.getAllUserCardsFromDeck(deckId, user);
        return ResponseEntity.ok(cards);
    }


    // TODO: Add function to add newly created card immediately to card progression in vlow row
    @PostMapping("/{deckId}")
    public ResponseEntity<String> addCardToDeck(@AuthenticationPrincipal LocalUser user, @PathVariable Long deckId, @Valid @RequestBody CardConfigurationBody cardConfiguration) {
        try {
            cardsService.addNewCardToDeck(deckId, user, cardConfiguration);
            return ResponseEntity.ok().body("Successfully added card to the deck");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add card");
        }
    }

    @DeleteMapping("/{deckId}/{cardId}")
    public ResponseEntity<String> removeCardFromDeck(@AuthenticationPrincipal LocalUser user, @PathVariable Long deckId, @PathVariable Long cardId) {
        try {
            cardsService.removeCardFromDeck(user, deckId, cardId);
            return ResponseEntity.ok().body("Successfully removed card from the deck");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove card");
        }
    }


    // TODO: Check if deckId is rly necessary
    @PutMapping("/{deckId}/{cardId}")
    public ResponseEntity<CardDTO> updateCard(@AuthenticationPrincipal LocalUser user, @PathVariable Long deckId, @PathVariable Long cardId, @RequestBody CardConfigurationBody cardConfiguration) {
        try {
            CardDTO card = cardsService.updateCard(deckId, cardId, user, cardConfiguration);
            return ResponseEntity.ok().body(card);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{cardId}/update-curve")
    public ResponseEntity updateTimeCurveForCards(
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long cardId,
            @RequestBody @Valid UpdateCurveConfigurationBody configuration
    ) {
        try {
            Card existingCard = cardsRepository.findByIdAndDeck_Groups_LocalUser(cardId, user);
            if (existingCard == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
            cardsService.updateCardTimeIntervalByUserDecision(existingCard, configuration);
            progressionHistoryService.updateUserCardsHistory(existingCard);
            return ResponseEntity.status(HttpStatus.OK).body(CardsMapper.toDto(existingCard));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

        @GetMapping("/{deckId}/to-repeat")
        public ResponseEntity<List<CardDTO>> getCardsToRepeatFromDeck(@AuthenticationPrincipal LocalUser user, @PathVariable Long deckId) {
            List<CardDTO> cardsToRepeat = cardsService.getCardsToRepeatFromDeck(deckId, user);
            return ResponseEntity.ok(cardsToRepeat);
        }
}
