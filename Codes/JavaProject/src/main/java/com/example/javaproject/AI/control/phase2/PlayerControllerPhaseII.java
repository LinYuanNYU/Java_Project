package com.example.javaproject.AI.control.phase2;


import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.control.PlayerControl;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.AI.gameModel.*;
import com.example.javaproject.AI.gameModel.opponentModel.*;
import com.example.javaproject.AI.Evaluator.*;
import com.example.javaproject.AI.gameModel.opponentModel.*;

public abstract class PlayerControllerPhaseII extends PlayerControl{



    protected PlayerControllerPhaseII() {

    }

    protected double calculateCoefficient(GameHand gameHand, Player player) {
        double p = HandStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size());
        double EHS = p;
        EHS = p + (1 - p) * calculatehandpotential(gameHand, player);

        
        // Decision must depends on the number of players
        EHS = EHS * (1 + gameHand.getPlayersCount() / 10);

        // Last round, why not?
        if (gameHand.getBettingRoundName().equals(BettingRoundName.POST_RIVER)) {
            EHS += 0.2;
        }
        // Lot of raises, be careful
        if (ContextRaise.valueFor(gameHand.getCurrentBettingRound().getNumberOfRaises()).equals(ContextRaise.MANY)) {
            EHS -= 0.2;
        }

        return EHS;
    }
    
    protected double calculatehandpotential(GameHand gameHand, Player player) {
    	
    	double q = 0;
        if(gameHand.getBettingRoundName().equals(BettingRoundName.POST_FLOP)){
        	q = HandPotentialEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size(),gameHand);
        }
        
        if(gameHand.getBettingRoundName().equals(BettingRoundName.POST_TURN)){
        	q = HandPotentialEvaluator1.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size(),gameHand);
        }
        return q;
    }
}
