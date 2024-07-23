package com.sonsenim.sonsenimbackend.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DeckConfigurationBody {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 256)
    private String deckName;

    @NotNull
    private boolean isFlashcardNormal;

    @NotNull
    private boolean isFlashcardReversed;

    @NotNull
    private boolean isTyping;


    @NotNull
    private boolean isRandomizedOrder;

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public boolean isFlashcardNormal() {
        return isFlashcardNormal;
    }

    public void setFlashcardNormal(boolean flashcardNormal) {
        isFlashcardNormal = flashcardNormal;
    }

    public boolean isFlashcardReversed() {
        return isFlashcardReversed;
    }

    public void setFlashcardReversed(boolean flashcardReversed) {
        isFlashcardReversed = flashcardReversed;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean typing) {
        isTyping = typing;
    }

    public boolean isRandomizedOrder() {
        return isRandomizedOrder;
    }

    public void setRandomizedOrder(boolean randomizedOrder) {
        isRandomizedOrder = randomizedOrder;
    }
}
