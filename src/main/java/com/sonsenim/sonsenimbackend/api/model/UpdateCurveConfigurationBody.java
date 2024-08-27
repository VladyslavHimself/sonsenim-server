package com.sonsenim.sonsenimbackend.api.model;

import jakarta.validation.constraints.NotNull;

public class UpdateCurveConfigurationBody {

    @NotNull
    private boolean answerIsRight;


    public boolean isAnswerIsRight() {
        return answerIsRight;
    }

    public void setAnswerIsRight(boolean answerIsRight) {
        this.answerIsRight = answerIsRight;
    }
}
