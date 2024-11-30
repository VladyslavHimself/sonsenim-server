package com.sonsenim.sonsenimbackend.model.dto;
import java.time.LocalDate;

public class UserCardsProgressionHistoryDTO {
    private String date;
    private int veryLowIndicationCount;
    private int lowIndicationCount;
    private int midIndicationCount;
    private int highIndicationCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVeryLowIndicationCount() {
        return veryLowIndicationCount;
    }

    public void setVeryLowIndicationCount(int veryLowIndicationCount) {
        this.veryLowIndicationCount = veryLowIndicationCount;
    }

    public int getLowIndicationCount() {
        return lowIndicationCount;
    }

    public void setLowIndicationCount(int lowIndicationCount) {
        this.lowIndicationCount = lowIndicationCount;
    }

    public int getMidIndicationCount() {
        return midIndicationCount;
    }

    public void setMidIndicationCount(int midIndicationCount) {
        this.midIndicationCount = midIndicationCount;
    }

    public int getHighIndicationCount() {
        return highIndicationCount;
    }

    public void setHighIndicationCount(int highIndicationCount) {
        this.highIndicationCount = highIndicationCount;
    }
}
