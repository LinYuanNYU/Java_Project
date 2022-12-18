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
import java.util.Random;


public class PlayerControllerPhaseIIIConservative extends PlayerControllerPhaseIII {

    public PlayerControllerPhaseIIIConservative(PlayerControllerPhaseIIBluffConservative playerControllerPhaseIIBluffConservative,
                                                HandStrengthEvaluator handStrengthEvaluator,
                                                OpponentModeler opponentModeler) {
        super(playerControllerPhaseIIBluffConservative, handStrengthEvaluator, opponentModeler);
    }


    public String toString() {
        return "PhaseIII Conservative";
    }


    protected BettingDecision decideBet(GameHand gameHand, Player player,
                                        int type,
                                        double x,
                                        int opponentsModeledCount) {
        System.out.println(type);
        if (type == 0) {
            Random rand = new Random();
            Double n = rand.nextDouble();
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