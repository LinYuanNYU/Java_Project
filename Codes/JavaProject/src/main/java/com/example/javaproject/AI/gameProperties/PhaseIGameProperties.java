package com.example.javaproject.AI.gameProperties;


import com.example.javaproject.AI.control.phase1.*;
import com.example.javaproject.AI.Player;
import java.util.Random;



public class PhaseIGameProperties extends GameProperties {

    public PhaseIGameProperties(int numberOfPlayer, int numberOfHands, int initialMoney, int bigBlind, int smallBlind) {
        super(numberOfHands, initialMoney, bigBlind, smallBlind);
//        super(1000, 1000, 20, 10);
        Integer max = 10;
        Integer min = 5;
        for (int i=1; i<=numberOfPlayer; i++) {
            Random random = new Random();
            Integer a = random.nextInt(max - min + 1) + min;
            if (a <= 5){
                addPlayer(new Player(i, initialMoney, new PlayerControllerPhaseIBluff()));
            } else {
                addPlayer(new Player(i, getInitialMoney(), new PlayerControllerPhaseINormal()));
            }
        }
    }
}