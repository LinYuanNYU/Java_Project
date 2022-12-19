package com.example.javaproject.AI;

import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.AI.control.phase2.PlayerControllerPhaseII;
import com.example.javaproject.AI.gameProperties.GameProperties;
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
import com.example.javaproject.AI.gameProperties.*;

import java.util.Arrays;
import java.util.List;

public class Play {
    public static void main(String[] args) {

        /** Choose from Easy AI, Middle AI, and Hard AI**/
        List<String> options = Arrays.asList("Easy", "Middle", "Hard");;
        String gameP = "Easy";
        if(args.length == 1){
            gameP = args[0];
        }
        GameProperties newGameProperty;
        /*
        * ----------------------------Total Eight Kinds of AI-----------------------------------------
        *  AI Code can be found in AI.control.phase 1/phase 2/phase 3
        * Easy AI:
        *               1. Simple Bluff AI Strategy; 2. Simple Normal AI Strategy;
        * Middle AI:
        *               3. Middle Bluff Strategy; 4. Aggressive Bluff  AI Strategy; 5. Conservative Bluff AI Strategy 6. Middle Normal AI Strategy;
        *Hard AI:
        *               7. Hard Aggresive Mix AI strategy; 8. Hard Conservative Mix AI Strategy;
        *
        * */

        if (gameP.equals("Easy")){
            /*Choose parameters: number of Player, Hands number, initial money, large blind, and small blind*/
            newGameProperty = new PhaseIGameProperties(4, 1000, 1000, 20, 10);
        } else if (gameP.equals("Middle")){
            newGameProperty = new PhaseIIGameProperties(6, 1000, 1000, 20, 10);
        } else {
            newGameProperty = new PhaseIIIGameProperties(8, 1000, 1000, 20, 10);
        }

        List<Player> players = newGameProperty.getPlayers();

        GameHandControl newGameHandControl = new GameHandControl(newGameProperty);


        PokerControl newPokerControl = new PokerControl(newGameHandControl, newGameProperty);
        newPokerControl.play();

    }


}
