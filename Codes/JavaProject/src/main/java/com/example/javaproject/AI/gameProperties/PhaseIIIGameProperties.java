package com.example.javaproject.AI.gameProperties;

import com.example.javaproject.AI.control.phase2.*;
import com.example.javaproject.AI.control.phase3.*;
import com.example.javaproject.AI.Player;
public class PhaseIIIGameProperties extends GameProperties {

    public PhaseIIIGameProperties() {
        super(1000, 1000, 20, 10);

        addPlayer(new Player(1, getInitialMoney(), new PlayerControllerPhaseIIBluff()));
        addPlayer(new Player(2, getInitialMoney(), new PlayerControllerPhaseIINormal()));
        addPlayer(new Player(3, getInitialMoney(), new PlayerControllerPhaseIIIAgressive()));
        addPlayer(new Player(4, getInitialMoney(), new PlayerControllerPhaseIIIConservative()));
    }
}