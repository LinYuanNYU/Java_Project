package com.example.javaproject.AI.gameProperties;


import com.example.javaproject.AI.control.phase1.*;
import com.example.javaproject.AI.Player;




public class PhaseIGameProperties extends GameProperties {

    public PhaseIGameProperties() {
        super(1000, 1000, 20, 10);

        addPlayer(new Player(1, getInitialMoney(), new PlayerControllerPhaseIBluff()));
        addPlayer(new Player(2, getInitialMoney(), new PlayerControllerPhaseIBluff()));
        addPlayer(new Player(3, getInitialMoney(), new PlayerControllerPhaseINormal()));
        addPlayer(new Player(4, getInitialMoney(), new PlayerControllerPhaseINormal()));
    }
}