package com.example.javaproject.AI.gameModel.opponentModel;

public enum ContextPlayer {
    FEW,
    MANY;

    public static ContextPlayer valueFor(int numberOfPlayersRemaining) {
        if (numberOfPlayersRemaining < 3) {
            return FEW;
        } else {
            return MANY;
        }
    }
}
