package com.sonsenim.sonsenimbackend.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CardConfigurationBody {
    @NotNull
    @NotBlank
    @Size(min = 1)
    private String primaryWord;

    @NotNull
    @NotBlank
    @Size(min = 1)
    private String definition;

    private String explanation;

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
}
