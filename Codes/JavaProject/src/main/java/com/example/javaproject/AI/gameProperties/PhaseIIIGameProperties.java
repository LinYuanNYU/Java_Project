package com.example.javaproject.AI.gameProperties;

import com.example.javaproject.AI.Persistent.PersistenceManager;
import com.example.javaproject.AI.Persistent.PreFlopPersistence;
import com.example.javaproject.AI.control.phase1.PlayerControllerPhaseIBluff;
import com.example.javaproject.AI.control.phase1.PlayerControllerPhaseINormal;
import com.example.javaproject.AI.control.phase2.*;
import com.example.javaproject.AI.control.phase3.*;
import com.example.javaproject.AI.Player;

import java.util.Random;

public class PhaseIIIGameProperties extends GameProperties {

    public PhaseIIIGameProperties(int numberOfPlayer, int numberOfHands, int initialMoney, int bigBlind, int smallBlind) {
        super(numberOfHands, initialMoney, bigBlind, smallBlind);

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


//        super(1000, 1000, 20, 10);
//        PersistenceManager persistenceManager = new PersistenceManager();
//        PreFlopPersistence preFlopPersistence = new PreFlopPersistence(persistenceManager);
//        addPlayer(new Player(1, getInitialMoney(), new PlayerControllerPhaseIIBluff(preFlopPersistence)));
//        addPlayer(new Player(2, getInitialMoney(), new PlayerControllerPhaseIINormal(preFlopPersistence)));
//        addPlayer(new Player(3, getInitialMoney(), new PlayerControllerPhaseIIIAgressive(preFlopPersistence)));
//        addPlayer(new Player(4, getInitialMoney(), new PlayerControllerPhaseIIIConservative(preFlopPersistence)));
    }
}