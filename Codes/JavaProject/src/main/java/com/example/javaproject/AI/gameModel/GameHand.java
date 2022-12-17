package com.example.javaproject.AI.gameModel;

import com.example.javaproject.AI.Player;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.infra.Poker.Card;

import com.example.javaproject.AI.gameProperties.GameProperties;
import com.example.javaproject.AI.gameModel.opponentModel.ContextPlayer;
import com.example.javaproject.AI.gameModel.opponentModel.ContextAction;
import com.example.javaproject.AI.gameModel.opponentModel.ContextInfo;
import com.example.javaproject.AI.gameModel.opponentModel.ContextRaise;
import com.example.javaproject.AI.gameModel.opponentModel.ContextPotOdd;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class GameHand {
    private final Deque<Player> players;

    private final List<Card> sharedCards = new ArrayList<Card>();
    private final List<BettingRound> bettingRounds = new ArrayList<BettingRound>();
    private Boolean hasRemoved = true;
    public static int raiseValue = 0;

    public GameHand(List<Player> players) {
        this.players = new LinkedList<Player>(players);
    }

    public void nextRound() {
        bettingRounds.add(new BettingRound());

        if (getBettingRoundName().equals(BettingRoundName.PRE_FLOP)) {
            for (Player player : players) {
                Card card1 = CardSet.pop();
                Card card2 = CardSet.pop();
                player.setHoleCards(card1, card2);
            }
        } else if (getBettingRoundName().equals(BettingRoundName.POST_FLOP)) {
            ArrayList<Card> flopCards = CardSet.getFlopCards();
            sharedCards.add(flopCards.get(0));
            sharedCards.add(flopCards.get(1));
            sharedCards.add(flopCards.get(2));
        }  else if (getBettingRoundName().equals(BettingRoundName.POST_TURN)) {
        Card flopCard = CardSet.getTurnCard();
        sharedCards.add(flopCard);
        } else {
            Card riverCard = CardSet.getRiverCard();
            sharedCards.add(riverCard);
        }
    }

    public BettingRoundName getBettingRoundName() {
        return BettingRoundName.fromRoundNumber(bettingRounds.size());
    }

    public Player getNextPlayer() {
        if (!hasRemoved) {
            Player player = players.removeFirst();
            players.addLast(player);
        }
        hasRemoved = false;
        return getCurrentPlayer();
    }

    public int getTotalBets() {
        int totalBets = 0;
        for (BettingRound bettingRound : bettingRounds) {
            totalBets += bettingRound.getTotalBets();
        }
        return totalBets;
    }


    public Player getCurrentPlayer() {
        return players.getFirst();
    }

    public List<Card> getSharedCards() {
        return sharedCards;
    }

    public int getPlayersCount() {
        return players.size();
    }

    public BettingRound getCurrentBettingRound() {
        return bettingRounds.get(bettingRounds.size() - 1);
    }

    public List<BettingRound> getBettingRounds() {
        return bettingRounds;
    }

    public void removeCurrentPlayer() {
        players.removeFirst();
        hasRemoved = true;
    }


    public Deque<Player> getPlayers() {
        return this.players;
    }

    public void applyDecision(Player player, BettingDecision bettingDecision, GameProperties gameProperties,
                              double handStrength) {
        BettingRound currentBettingRound = getCurrentBettingRound();
        double potOdds = calculatePotOdds(player);
        ContextAction contextAction = new ContextAction(player, bettingDecision, getBettingRoundName(),
                currentBettingRound.getNumberOfRaises(),
                getPlayersCount(), potOdds);
        ContextInfo contextInformation = new ContextInfo(contextAction, handStrength);

        currentBettingRound.applyDecision(this, contextInformation, gameProperties);

        if (bettingDecision.equals(BettingDecision.FOLD)) {
            removeCurrentPlayer();
        }
    }

    public double calculatePotOdds(Player player) {
        BettingRound currentBettingRound = getCurrentBettingRound();
        int amountNeededToCall = currentBettingRound.getHighestBet() - currentBettingRound.getBetForPlayer(player);
        return (double) amountNeededToCall / (amountNeededToCall + getTotalBets());
    }

    protected ArrayList<Card> getDeck() {
        ArrayList<Card> DeckCards = CardSet.getFlopCards();
        DeckCards.add(CardSet.getTurnCard());
        DeckCards.add(CardSet.getRiverCard());
        return DeckCards;
    }
}
