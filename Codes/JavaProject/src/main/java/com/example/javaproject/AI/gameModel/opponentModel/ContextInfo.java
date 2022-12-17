package com.example.javaproject.AI.gameModel.opponentModel;

public class ContextInfo {
    private final ContextAction contextAction;
    private final double handStrength;

    public ContextInfo(ContextAction contextAction, double handStrength) {
        this.contextAction = contextAction;
        this.handStrength = handStrength;
    }

    public ContextAction getContextAction() {
        return contextAction;
    }

    public double getHandStrength() {
        return handStrength;
    }

}
