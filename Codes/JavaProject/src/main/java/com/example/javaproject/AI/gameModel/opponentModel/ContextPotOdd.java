package com.example.javaproject.AI.gameModel.opponentModel;

public enum ContextPotOdd {

    LOW,
    HIGH;

    public static ContextPotOdd valueFor(double potOdds) {
        if (potOdds > 0.2) {
            return HIGH;
        } else {
            return LOW;
        }
    }

}
