package com.example.javaproject.AI.control;

import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.gameModel.BettingDecision;
import com.example.javaproject.AI.gameModel.BettingRound;
import com.example.javaproject.AI.gameModel.GameHand;
import com.example.javaproject.AI.Player;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.infra.Poker.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerControl {
    public BettingDecision decide(Player player, GameHand gameHand) {
        List<Card> cards = new ArrayList<Card>();
        cards.addAll(gameHand.getSharedCards());
        cards.addAll(player.getHoleCards());

        if (cards.size() == 2) {
            return decidePreFlop(player, gameHand, cards);
        } else {
            return decideAfterFlop(player, gameHand, cards);
        }
    }

    protected boolean canCheck(GameHand gameHand, Player player) {
        BettingRound bettingRound = gameHand.getCurrentBettingRound();
        return bettingRound.getHighestBet() == bettingRound.getBetForPlayer(player);
    }

    protected  abstract BettingDecision decidePreFlop(Player player,
                                                     GameHand gameHand, List<Card> cards);

    protected abstract BettingDecision decideAfterFlop(Player player,
                                                       GameHand gameHand, List<Card> cards);

    protected BettingDecision decideBet(GameHand gameHand, Player player,
                                        int type, double x, int opponentsModeledCount) {
        // TODO Auto-generated method stub
        return null;
    }


}
