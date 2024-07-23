package com.sonsenim.sonsenimbackend.model.dto;

import java.sql.Timestamp;

public class DeckDTO {
    private Long id;
    private String deckName;
    private Boolean isFlashcardNormal;
    private Boolean isFlashcardReversed;
    private Boolean isFlashcardTyping;
    private Boolean isRandomizedOrder;
    private Timestamp createdAt;

    public Boolean getFlashcardReversed() {
        return isFlashcardReversed;
    }

    public void setFlashcardReversed(Boolean flashcardReversed) {
        isFlashcardReversed = flashcardReversed;
    }


    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public Boolean getFlashcardNormal() {
        return isFlashcardNormal;
    }

    public void setFlashcardNormal(Boolean flashcardNormal) {
        isFlashcardNormal = flashcardNormal;
    }

    public Boolean getFlashcardTyping() {
        return isFlashcardTyping;
    }

    public void setFlashcardTyping(Boolean flashcardTyping) {
        isFlashcardTyping = flashcardTyping;
    }

    public Boolean getRandomizedOrder() {
        return isRandomizedOrder;
    }

    public void setRandomizedOrder(Boolean randomizedOrder) {
        isRandomizedOrder = randomizedOrder;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
