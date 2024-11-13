package com.sonsenim.sonsenimbackend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "user_progression_history")
@EntityListeners(AuditingEntityListener.class)
public class UserCardsProgressionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups group;

    public Groups getGroup() {
        return group;
    }

    @Column(name = "high_indication_count")
    private Integer highIndicationCount;

    @Column(name = "mid_indication_count")
    private Integer midIndicationCount;

    @Column(name = "low_indication_count")
    private Integer lowIndicationCount;

    @Column(name = "very_low_indication_count")
    private Integer veryLowIndicationCount;

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

    public Integer getHighIndicationCount() {
        return highIndicationCount;
    }

    public void setHighIndicationCount(Integer highIndicationCount) {
        this.highIndicationCount = highIndicationCount;
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

    public Integer getVeryLowIndicationCount() {
        return veryLowIndicationCount;
    }

    public void setVeryLowIndicationCount(Integer veryLowIndicationCount) {
        this.veryLowIndicationCount = veryLowIndicationCount;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
