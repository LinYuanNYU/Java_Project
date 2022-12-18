package com.example.javaproject.AI.Evaluator;



import com.example.javaproject.infra.Poker.Deck;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.control.PlayerControl;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.AI.gameModel.*;
import java.util.ArrayList;
import java.util.List;


public class HandStrengthEvaluator {

    private final HandPowerRanker handPowerRanker;


    public HandStrengthEvaluator(final HandPowerRanker handPowerRanker) {
        this.handPowerRanker = handPowerRanker;
    }

    public double evaluate(List<Card> playerHoleCards, List<Card> sharedCards, Integer numberOfPlayers) {
        if(sharedCards == null || sharedCards.isEmpty()){
            return 0d;
        }

        int wins = 0;
        int losses = 0;
        int ties = 0;

        Deck deck = new Deck();
        Card hole1 = playerHoleCards.get(0);
        Card hole2 = playerHoleCards.get(1);
        deck.removeCard(hole1);
        deck.removeCard(hole2);
        for (Card card : sharedCards) {
            deck.removeCard(card);
        }

        List<List<Card>> couplesOfCards = deck.fromDeckToCouplesOfCard();

        List<Card> playerCards = new ArrayList<Card>();
        playerCards.addAll(playerHoleCards);
        playerCards.addAll(sharedCards);
        HandPower playerRank = handPowerRanker.rank(playerCards);

        for (List<Card> couple : couplesOfCards) {
            List<Card> opponentCards = new ArrayList<Card>();
            opponentCards.addAll(couple);
            opponentCards.addAll(sharedCards);
            HandPower opponentRank = handPowerRanker.rank(opponentCards);

            int result = playerRank.compareTo(opponentRank);
            if (result > 0) {
                wins++;
            } else if (result < 0) {
                losses++;
            } else {
                ties++;
            }
        }

        double num = (wins + 0.5 * ties);
        double den = (wins + losses + ties);
        return Math.pow(num / den, numberOfPlayers);

    }


}

