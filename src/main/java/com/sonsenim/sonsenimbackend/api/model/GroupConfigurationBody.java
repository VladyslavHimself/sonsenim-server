package com.sonsenim.sonsenimbackend.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GroupConfigurationBody {

    @NotNull
    @NotBlank
    @Size(min = 1)
    private String groupName;

    public @NotNull @NotBlank @Size(min = 1) String getGroupName() {
        return groupName;
    }

    public void setGroupName(@NotNull @NotBlank @Size(min = 1) String groupName) {
        this.groupName = groupName;
    }
}
