package com.example.javaproject.AI.control.phase3;

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
import com.example.javaproject.AI.control.phase2.*;
import java.util.List;
import java.util.Random;
public class PlayerControllerPhaseIIIAgressive extends PlayerControllerPhaseIII {

    public PlayerControllerPhaseIIIAgressive() {
        super();
    }


    public String toString() {
        return "PhaseIII Agressive";
    }


    protected BettingDecision decideBet(GameHand gameHand, Player player,
                                        int type,
                                        double x,
                                        int opponentsModeledCount) {
        System.out.println(type);
        Random rand = new Random();
        Double n = rand.nextDouble();
        if (type == 0 || (type == 1 && n > 0.5)) {
            GameHand.raiseValue = (int) (0.5 * (1 + n) * player.getMoney());
            return BettingDecision.RAISE;
        } else if (type == 3) {
            GameHand.raiseValue = (int) (Math.exp(x-1) * 0.5 * player.getMoney());
            return BettingDecision.RAISE;
        } else if (type == 2){
            return BettingDecision.CALL;
        } else{
            return BettingDecision.FOLD;
        }
    }
}
