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

public abstract class PlayerControllerPhaseII extends PlayerControl{
    private final HandStrengthEvaluator handStrengthEvaluator;
	private final HandPotentialEvaluator handPotentialEvaluator;
	private final HandPotentialEvaluator1 handPotentialEvaluator1;

    protected PlayerControllerPhaseII(final HandStrengthEvaluator handStrengthEvaluator,final HandPotentialEvaluator handPotentialEvaluator,final HandPotentialEvaluator1 handPotentialEvaluator1) {
        this.handStrengthEvaluator = handStrengthEvaluator;
        this.handPotentialEvaluator = handPotentialEvaluator;
        this.handPotentialEvaluator1 = handPotentialEvaluator1;
    }

    protected double calculateCoefficient(GameHand gameHand, Player player) {
        double p = this.handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
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
        if (ContextRaise.valueFor(gameHand.getCurrentBettingRound().getNumberOfRaises()).equals(ContextRaises.MANY)) {
            EHS -= 0.2;
        }

        return EHS;
    }
    
    protected double calculatehandpotential(GameHand gameHand, Player player) {
    	
    	double q = 0;
        if(gameHand.getBettingRoundName().equals(BettingRoundName.POST_FLOP)){
        	q = this.handPotentialEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size(),gameHand);
        }
        
        if(gameHand.getBettingRoundName().equals(BettingRoundName.POST_TURN)){
        	q = this.handPotentialEvaluator1.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayers().size(),gameHand);
        }
        return q;
    }
}
