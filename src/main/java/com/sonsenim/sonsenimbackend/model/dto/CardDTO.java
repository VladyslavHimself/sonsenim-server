package com.sonsenim.sonsenimbackend.model.dto;

import java.time.LocalDateTime;

public class CardDTO {
    private Long cardId;
    private String primaryWord;
    private String definition;
    private String explanation;
    private LocalDateTime nextRepetitionTime;
    private Integer intervalDaysStrength;
    private LocalDateTime createdAt;


    public String getPrimaryWord() {
        return primaryWord;
    }

    public void setPrimaryWord(String primaryWord) {
        this.primaryWord = primaryWord;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDateTime getNextRepetitionTime() {
        return nextRepetitionTime;
    }

    public void setNextRepetitionTime(LocalDateTime nextRepetitionTime) {
        this.nextRepetitionTime = nextRepetitionTime;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getIntervalDaysStrength() {
        return intervalDaysStrength;
    }

    public void setIntervalDaysStrength(Integer intervalDaysStrength) {
        this.intervalDaysStrength = intervalDaysStrength;
    }
}
