package com.example.javaproject.AI.control.phase1;



import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.control.PlayerControl;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.AI.gameModel.*;

import java.util.List;

public class PlayerControllerPhaseIBluff extends PlayerControl {
    private final HandPowerRanker handPowerRanker;


    public PlayerControllerPhaseIBluff(final HandPowerRanker handPowerRanker) {
        this.handPowerRanker = handPowerRanker;
    }

    @Override
    public String toString() {
        return "PhaseI bluff";
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand,
                                         List<Card> cards) {
        Card card1 = cards.get(0);
        Card card2 = cards.get(1);
        int sumPower = card1.getRank() + card2.getRank();

        if ((card1.getRank() == card2.getRank()) || sumPower <= 8) {
        	GameHand.raiseValue = 40;
        	return BettingDecision.RAISE;
        } else {
            if (sumPower > 16) {
                return BettingDecision.CALL;
            } else {
                return BettingDecision.FOLD;
            }
        }
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand,
                                           List<Card> cards) {
        HandPower handPower = handPowerRanker.rank(cards);

        HandPowerType handPowerType = handPower.getHandPowerType();
        if (handPowerType.equals(HandPowerType.HIGH_CARD)) {
        	GameHand.raiseValue = 40;
        	return BettingDecision.RAISE;
        } else if (handPowerType.getPower() >= HandPowerType.STRAIGHT.getPower()) {
        	GameHand.raiseValue = 40;
        	return BettingDecision.RAISE;
        } else if(gameHand.getPlayers().getFirst().equals(player)){
        	return BettingDecision.CALL;
        } else{
            return BettingDecision.FOLD;
        }
    }
}
