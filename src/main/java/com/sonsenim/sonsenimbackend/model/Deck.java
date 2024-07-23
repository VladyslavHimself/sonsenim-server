package com.sonsenim.sonsenimbackend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "deck_name", nullable = false)
    private String deckName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups groups;

    @Column(name = "is_flashcard_normal", nullable = false)
    private Boolean isFlashcardNormal = false;

    @Column(name = "is_flashcard_reversed", nullable = false)
    private Boolean isFlashcardReversed = false;

    @Column(name = "is_typing", nullable = false)
    private Boolean isTyping = false;

    @Column(name = "is_randomized_order", nullable = false)
    private Boolean isRandomizedOrder;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    public Boolean getIsTyping() {
        return isTyping;
    }

    public void setIsTyping(Boolean isTyping) {
        this.isTyping = isTyping;
    }

    public Boolean getIsFlashcardReversed() {
        return isFlashcardReversed;
    }

    public void setIsFlashcardReversed(Boolean isFlashcardReversed) {
        this.isFlashcardReversed = isFlashcardReversed;
    }

    public Boolean getIsFlashcardNormal() {
        return isFlashcardNormal;
    }

    public void setIsFlashcardNormal(Boolean isFlashcardNormal) {
        this.isFlashcardNormal = isFlashcardNormal;
    }

    public Groups getGroup() {
        return groups;
    }

    public void setGroup(Groups groups) {
        this.groups = groups;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRandomizedOrder() {
        return isRandomizedOrder;
    }

    public void setRandomizedOrder(Boolean randomizedOrder) {
        isRandomizedOrder = randomizedOrder;
    }
}