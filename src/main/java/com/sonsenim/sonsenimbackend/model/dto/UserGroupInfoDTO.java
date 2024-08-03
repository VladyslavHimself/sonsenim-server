package com.sonsenim.sonsenimbackend.model.dto;

public class UserGroupInfoDTO {
    private Long groupId;
    private String groupName;
    private long decksCount;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getDecksCount() {
        return decksCount;
    }

    public void setDecksCount(long decksCount) {
        this.decksCount = decksCount;
    }
}
