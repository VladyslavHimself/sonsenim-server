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
    private boolean flashcardNormal;

    @NotNull
    private boolean flashcardReversed;

    @NotNull
    private boolean flashcardTyping;

    @NotNull
    private boolean randomizedOrder;

    public @NotNull @NotBlank @Size(min = 1, max = 256) String getDeckName() {
        return deckName;
    }

    public void setDeckName(@NotNull @NotBlank @Size(min = 1, max = 256) String deckName) {
        this.deckName = deckName;
    }

    @NotNull
    public boolean isFlashcardNormal() {
        return flashcardNormal;
    }

    public void setFlashcardNormal(@NotNull boolean flashcardNormal) {
        this.flashcardNormal = flashcardNormal;
    }

    @NotNull
    public boolean isFlashcardReversed() {
        return flashcardReversed;
    }

    public void setFlashcardReversed(@NotNull boolean flashcardReversed) {
        this.flashcardReversed = flashcardReversed;
    }

    @NotNull
    public boolean isFlashcardTyping() {
        return flashcardTyping;
    }

    public void setFlashcardTyping(@NotNull boolean flashcardTyping) {
        this.flashcardTyping = flashcardTyping;
    }

    @NotNull
    public boolean isRandomizedOrder() {
        return randomizedOrder;
    }

    public void setRandomizedOrder(@NotNull boolean randomizedOrder) {
        this.randomizedOrder = randomizedOrder;
    }
}
