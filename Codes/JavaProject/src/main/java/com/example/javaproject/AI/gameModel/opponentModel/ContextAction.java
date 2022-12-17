package com.example.javaproject.AI.gameModel.opponentModel;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.gameModel.BettingDecision;
import com.example.javaproject.AI.gameModel.BettingRound;
import com.example.javaproject.AI.gameModel.BettingRoundName;


public class ContextAction {
    private final Player player;
    private final BettingDecision bettingDecision;
    private final BettingRoundName bettingRoundName;
    private final ContextRaise contextRaises;
    private final ContextPlayer contextPlayers;
    private final ContextPotOdd contextPotOdds;


    public ContextAction(Player player, BettingDecision bettingDecision, BettingRoundName bettingRoundName,
                         int numberOfRaises, int numberOfPlayersRemaining, double potOdds) {
        this.player = player;
        this.bettingDecision = bettingDecision;
        this.bettingRoundName = bettingRoundName;
        contextRaises = ContextRaise.valueFor(numberOfRaises);
        contextPlayers = ContextPlayer.valueFor(numberOfPlayersRemaining);
        contextPotOdds = ContextPotOdd.valueFor(potOdds);
    }

    public boolean equals(Object o) {
        if (!(o instanceof ContextAction)) {
            return false;
        }

        ContextAction other = (ContextAction) o;

        return (player.equals(other.player) && bettingDecision.equals(other.bettingDecision) && bettingRoundName
                .equals(other.bettingRoundName) && contextRaises.equals(other.contextRaises) && contextPlayers.equals
                (other.contextPlayers) && contextPotOdds.equals(other.contextPotOdds));
    }

    public Player getPlayer() {
        return player;
    }

    public BettingDecision getBettingDecision() {
        return bettingDecision;
    }

    public BettingRoundName getBettingRoundName() {
        return bettingRoundName;
    }

    public ContextRaise getContextRaises() {
        return contextRaises;
    }

    public ContextPlayer getContextPlayers() {
        return contextPlayers;
    }

    public ContextPotOdd getContextPotOdds() {
        return contextPotOdds;
    }



}
