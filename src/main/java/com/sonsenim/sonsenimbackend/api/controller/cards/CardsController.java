package com.sonsenim.sonsenimbackend.api.controller.cards;

import com.sonsenim.sonsenimbackend.api.model.CardConfigurationBody;
import com.sonsenim.sonsenimbackend.api.model.UpdateCurveConfigurationBody;
import com.sonsenim.sonsenimbackend.mappers.CardsMapper;
import com.sonsenim.sonsenimbackend.model.Card;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.CardDTO;
import com.sonsenim.sonsenimbackend.repositories.CardsRepository;
import com.sonsenim.sonsenimbackend.service.CardsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * 1. + Get all user cards from DECK
 * 2. + Get cards that need to repeat by DECK
 * 3. Get all cards that need to repeat by user in this GROUP
 *
 */


@RestController
@RequestMapping("/cards")
public class CardsController {

    private final CardsService cardsService;
    private final CardsRepository cardsRepository;

    public CardsController(CardsService cardsService, CardsRepository cardsRepository) {
        this.cardsService = cardsService;
        this.cardsRepository = cardsRepository;
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<List<CardDTO>> getCardsFromDeck(@AuthenticationPrincipal LocalUser user, @PathVariable Long deckId) {
        List<CardDTO> cards = cardsService.getAllUserCardsFromDeck(deckId, user);
        return ResponseEntity.ok(cards);
    }

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

    @PatchMapping("/{cardId}/update-curve/1")
    public ResponseEntity updateTimeCurveForCards(
            @AuthenticationPrincipal LocalUser user,
            @PathVariable Long cardId,
            @RequestBody UpdateCurveConfigurationBody configuration
    ) {
        try {
            Card existingCard = cardsRepository.findByIdAndDeck_Groups_LocalUser(cardId, user);

            // TODO: Hardcoded, optimize with LinkedMap

            boolean isAnswerRight = !configuration.isAnswerRight();

            float currentIntervalStr = existingCard.getIntervalStrength();

            if ((currentIntervalStr >= 180 && currentIntervalStr < 360) && isAnswerRight) {
                existingCard.setIntervalStrength(360f);
            }

            if ((currentIntervalStr >= 90 && currentIntervalStr < 180 ) && isAnswerRight) {
                existingCard.setIntervalStrength(180f);
            }

            if ((currentIntervalStr >= 30 && currentIntervalStr < 90) && isAnswerRight) {
                existingCard.setIntervalStrength(90f);
            }

            if ((currentIntervalStr >= 14 && currentIntervalStr < 30) && isAnswerRight) {
                existingCard.setIntervalStrength(30f);
            }

            if ((currentIntervalStr >= 7 && currentIntervalStr < 14) && isAnswerRight) {
                existingCard.setIntervalStrength(14f);
            }

            if ((currentIntervalStr >= 3 && currentIntervalStr < 7) && isAnswerRight) {
                existingCard.setIntervalStrength(7f);
            }

            if ((currentIntervalStr >= 1 && currentIntervalStr < 3) && isAnswerRight) {
                existingCard.setIntervalStrength(3f);
            }

            if ((currentIntervalStr >= 0.5 && currentIntervalStr < 1) && isAnswerRight) {
                existingCard.setIntervalStrength(1f);
            }

            if ((currentIntervalStr >= 0.25 && currentIntervalStr < 0.5) && isAnswerRight) {
                existingCard.setIntervalStrength(0.5f);
            }

            if (currentIntervalStr == 0 && !isAnswerRight) {
                existingCard.setIntervalStrength(0.125f);
            }

            if ((currentIntervalStr >= 0 && currentIntervalStr < 0.25) && isAnswerRight) {
                existingCard.setIntervalStrength(0.25f);
            }

            if (!isAnswerRight && currentIntervalStr > 0.125) {
                if (currentIntervalStr / 2 < 0.125) {
                    existingCard.setIntervalStrength(0.125f);
                } else {
                    existingCard.setIntervalStrength(currentIntervalStr / 2);
                }
            }

            cardsRepository.save(existingCard);
            return ResponseEntity.status(HttpStatus.OK).body(CardsMapper.toDto(existingCard));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{deckId}/to-repeat")
    public ResponseEntity<List<CardDTO>> getCardsToRepeatFromDeck(@AuthenticationPrincipal LocalUser user, @PathVariable Long deckId) {
        List<CardDTO> cardsToRepeat = cardsService.getCardsToRepeatFromDeck(deckId, user);
        return ResponseEntity.ok(cardsToRepeat);
    }
}
