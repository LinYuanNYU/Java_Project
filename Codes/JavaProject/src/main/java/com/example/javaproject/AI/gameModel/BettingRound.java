package com.example.javaproject.AI.gameModel;
import com.example.javaproject.AI.Player;

import com.example.javaproject.AI.gameProperties.GameProperties;
import com.example.javaproject.AI.gameModel.opponentModel.ContextPlayer;
import com.example.javaproject.AI.gameModel.opponentModel.ContextAction;
import com.example.javaproject.AI.gameModel.opponentModel.ContextInfo;
import com.example.javaproject.AI.gameModel.opponentModel.ContextRaise;
import com.example.javaproject.AI.gameModel.opponentModel.ContextPotOdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BettingRound {
    private final Map<Player, Integer> playerBets = new HashMap<Player, Integer>();
    private final List<ContextInfo> contextInformations = new ArrayList<ContextInfo>();
    private int highestBet = 0;


    public void applyDecision(GameHand gameHand, ContextInfo contextInformation, GameProperties gameProperties) {
        ContextAction contextAction = contextInformation.getContextAction();
        BettingDecision bettingDecision = contextAction.getBettingDecision();
        Player player = contextAction.getPlayer();

        switch (bettingDecision) {
            case FOLD:
                break;
            case CALL:
                placeBet(player, Math.min(highestBet,player.getMoney()));
                break;
            case RAISE:
                placeBet(player, Math.min(highestBet+GameHand.raiseValue,player.getMoney()));
        }

        // Don't save context information for pre flop
        // Hand strength is always 0 b/c there's no shared cards
        if (!contextAction.getBettingRoundName().equals(BettingRoundName.PRE_FLOP)) {
            contextInformations.add(contextInformation);
        }
    }

    public void placeBet(Player player, int bet) {
        Integer playerBet = playerBets.get(player);

        if (playerBet == null) {
            player.removeMoney(bet);
        } else {
            player.removeMoney(bet - playerBet);
        }

        if (bet > highestBet) {
            highestBet = bet;

        } else if (bet < highestBet && player.getMoney()!=0) {
            throw new IllegalArgumentException(
                    "You can't bet less than the highest bet");
        }

        playerBets.put(player, bet);
    }

    public int getHighestBet() {
        return highestBet;
    }

    public List<ContextInfo> getContextInformations() {
        return contextInformations;
    }

    public int getBetForPlayer(Player player) {
        Integer bet = playerBets.get(player);
        if (bet == null) {
            return 0;
        }
        return bet;
    }

    public int getTotalBets() {
        int totalBets = 0;
        for (Integer bet : playerBets.values()) {
            totalBets += bet;
        }
        return totalBets;
    }

    public int getNumberOfRaises() {
        int numberOfRaises = 0;
        for (ContextInfo contextInformation : contextInformations) {
            if (contextInformation.getContextAction().getBettingDecision().equals(BettingDecision.RAISE)) {
                numberOfRaises++;
            }
        }
        return numberOfRaises;
    }

    public ContextAction getContextActionForPlayer(Player player) {
        for (int i = contextInformations.size(); i > 0; i--) {
            ContextInfo contextInformation = contextInformations.get(i - 1);
            ContextAction contextAction = contextInformation.getContextAction();

            if (contextAction.getPlayer().equals(player)) {
                return contextAction;
            }
        }

        return null;
    }


}
