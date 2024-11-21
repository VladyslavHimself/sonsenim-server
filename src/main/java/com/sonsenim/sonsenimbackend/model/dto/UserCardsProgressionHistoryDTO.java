package com.sonsenim.sonsenimbackend.model.dto;
import java.time.LocalDate;

public class UserCardsProgressionHistoryDTO {
    private String groupId;
    private LocalDate date;
    private int vlow;
    private int low;
    private int mid;
    private int high;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getVlow() {
        return vlow;
    }

    public void setVlow(int vlow) {
        this.vlow = vlow;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
