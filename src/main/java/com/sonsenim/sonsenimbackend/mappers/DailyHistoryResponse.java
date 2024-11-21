package com.sonsenim.sonsenimbackend.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DailyHistoryResponse {
    private LocalDate date;
    private Integer veryLowIndicationCount;
    private Integer lowIndicationCount;
    private Integer midIndicationCount;
    private Integer highIndicationCount;

    public DailyHistoryResponse(LocalDateTime date, Integer veryLowIndicationCount, Integer lowIndicationCount,
                                Integer midIndicationCount, Integer highIndicationCount) {
        this.date = date.toLocalDate();
        this.veryLowIndicationCount = veryLowIndicationCount;
        this.lowIndicationCount = lowIndicationCount;
        this.midIndicationCount = midIndicationCount;
        this.highIndicationCount = highIndicationCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getVeryLowIndicationCount() {
        return veryLowIndicationCount;
    }

    public void setVeryLowIndicationCount(Integer veryLowIndicationCount) {
        this.veryLowIndicationCount = veryLowIndicationCount;
    }

    public Integer getLowIndicationCount() {
        return lowIndicationCount;
    }

    public void setLowIndicationCount(Integer lowIndicationCount) {
        this.lowIndicationCount = lowIndicationCount;
    }

    public Integer getMidIndicationCount() {
        return midIndicationCount;
    }

    public void setMidIndicationCount(Integer midIndicationCount) {
        this.midIndicationCount = midIndicationCount;
    }

    public Integer getHighIndicationCount() {
        return highIndicationCount;
    }

    public void setHighIndicationCount(Integer highIndicationCount) {
        this.highIndicationCount = highIndicationCount;
    }
}
