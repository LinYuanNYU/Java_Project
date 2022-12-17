package com.example.javaproject.AI.gameModel.opponentModel;

public enum ContextRaise {
    FEW,
    MANY;

    public static ContextRaise valueFor(int numberOfRaises) {
        if(numberOfRaises < 3){
            return FEW;
        }else{
            return MANY;
        }
    }

}
