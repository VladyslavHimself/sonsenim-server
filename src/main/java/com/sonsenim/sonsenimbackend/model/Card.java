package com.sonsenim.sonsenimbackend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "interval_strength")
    private Float intervalStrength;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @PrePersist
    protected void onCreate() {
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