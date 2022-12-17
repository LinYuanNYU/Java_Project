package com.example.javaproject.AI.control.phase1;

import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.control.PlayerControl;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.AI.gameModel.*;

import java.util.List;

public class PlayerControllerPhaseINormal extends PlayerControl {
    private final HandPowerRanker handPowerRanker;

    public PlayerControllerPhaseINormal(final HandPowerRanker handPowerRanker) {
        this.handPowerRanker = handPowerRanker;
    }

    @Override
    public String toString() {
        return "PhaseI normal";
    }

    @Override
    public BettingDecision decidePreFlop(Player player, GameHand gameHand,
                                         List<Card> cards) {
        Card card1 = cards.get(0);
        Card card2 = cards.get(1);

        if ((card1.getRank() == card2.getRank())) {
        	GameHand.raiseValue = 40;
            return BettingDecision.RAISE;
        } else if (card1.getRank() + card2.getRank() > 16) {
            return BettingDecision.CALL;
        } else {
            return BettingDecision.FOLD;
        }
    }

    @Override
    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
        HandPower handPower = handPowerRanker.rank(cards);

        HandPowerType handPowerType = handPower.getHandPowerType();
        if (handPowerType.equals(HandPowerType.HIGH_CARD) && !gameHand.getPlayers().getFirst().equals(player)) {
            return BettingDecision.FOLD;
        } else if (handPowerType.getPower() >= HandPowerType.STRAIGHT.getPower()) {
        	GameHand.raiseValue = 40;
            return BettingDecision.RAISE;
        } else {
            return BettingDecision.CALL;
        }
    }
}
