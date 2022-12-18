package com.example.javaproject.AI;

import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.AI.control.phase2.PlayerControllerPhaseII;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.infra.Poker.*;
import com.example.javaproject.AI.control.PlayerControl;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.AI.gameModel.*;
import com.example.javaproject.AI.gameModel.opponentModel.*;
import com.example.javaproject.AI.Persistent.*;
import com.example.javaproject.AI.Evaluator.*;
import com.example.javaproject.AI.control.*;

public class Play {
    public static void main(String[] args) {
        String gameP = "Easy";
        if(args.length == 1){
            gameP = args[0];
        }
        GameHandControl gameHandControl1 = new GameHandControl()
        PokerControl pockerControl1 = new PokerControl()

//        Injector injector = Guice.createInjector(new TexasModule(LogLevel.ALL, GamePropertiesParameter.fromString(gameP)));
//
//        PokerController pokerController = injector.getInstance(PokerController.class);
//        pokerController.play();
    }


}
