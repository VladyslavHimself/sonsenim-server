package com.sonsenim.sonsenimbackend.model.dto;

public class GroupStatisticsDTO {
    private int decksTotal;
    private long cardsTotal;

    public int getDecksTotal() {
        return decksTotal;
    }

    public void setDecksTotal(int decksTotal) {
        this.decksTotal = decksTotal;
    }

    public long getCardsTotal() {
        return cardsTotal;
    }

    public void setCardsTotal(long cardsTotal) {
        this.cardsTotal = cardsTotal;
    }
}
