package com.sonsenim.sonsenimbackend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @Column(name = "primary_word", nullable = false)
    private String primaryWord;

    @Column(name = "definition", nullable = false)
    private String definition;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "next_repetition_time")
    private LocalDateTime nextRepetitionTime;



    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "interval_strength")
    private Float intervalStrength;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
        intervalStrength = 0f;
    }

    public LocalDateTime getNextRepetitionTime() {
        return nextRepetitionTime;
    }

    public void setNextRepetitionTime(LocalDateTime nextRepetitionTime) {
        this.nextRepetitionTime = nextRepetitionTime;
    }

    public Float getIntervalStrength() {
        return intervalStrength;
    }

    public void setIntervalStrength(Float intervalStrength) {
        this.intervalStrength = intervalStrength;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPrimaryWord() {
        return primaryWord;
    }

    public void setPrimaryWord(String primaryWord) {
        this.primaryWord = primaryWord;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}