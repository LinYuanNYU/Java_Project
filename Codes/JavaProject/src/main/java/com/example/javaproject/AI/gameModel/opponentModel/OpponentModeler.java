package com.example.javaproject.AI.gameModel.opponentModel;


import com.example.javaproject.AI.gameProperties.GameProperties;
import com.example.javaproject.AI.gameModel.opponentModel.ContextPlayer;
import com.example.javaproject.AI.gameModel.opponentModel.ContextAction;
import com.example.javaproject.AI.gameModel.opponentModel.ContextInfo;
import com.example.javaproject.AI.gameModel.opponentModel.ContextRaise;
import com.example.javaproject.AI.gameModel.opponentModel.ContextPotOdd;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.gameModel.*;
import com.example.javaproject.AI.Persistent.*;
import java.util.*;

public class OpponentModeler {
    private static final Map<Player, List<ContextAggregate>> playerModels = new HashMap<Player, List<ContextAggregate>>();

    public OpponentModeler() {
    }

    public static void save(GameHand gameHand) {
        Deque<Player> showdownPlayers = gameHand.getPlayers();

        for (BettingRound bettingRound : gameHand.getBettingRounds()) {
            for (ContextInfo contextInformation : bettingRound.getContextInformations()) {
                Player player = contextInformation.getContextAction().getPlayer();

                if (showdownPlayers.contains(player)) {
                    // Only save context opponent modeling for players who reach showdown
                    addToPlayerModel(contextInformation);
                }
            }
        }
    }

    public static ModelResult getEstimatedHandStrength(ContextAction contextAction) {
        return OpponentsModelPersistence.retrieve(contextAction);
    }

    public static Map<Player, List<ContextAggregate>> getPlayerModels() {
        return playerModels;
    }

    private static void addToPlayerModel(ContextInfo contextInformation) {
        ContextAggregate contextAggregate = getContextAggregate(contextInformation.getContextAction());
        contextAggregate.addOccurrence(contextInformation.getHandStrength());
    }

    private static ContextAggregate getContextAggregate(ContextAction contextAction) {
        Player player = contextAction.getPlayer();

        List<ContextAggregate> contextAggregates = playerModels.get(player);

        if (contextAggregates == null) {
            contextAggregates = new ArrayList<ContextAggregate>();
            playerModels.put(player, contextAggregates);
        }


        for (ContextAggregate contextAggregate : contextAggregates) {
            if (contextAggregate.getContextAction().equals(contextAction)) {
                return contextAggregate;
            }
        }

        ContextAggregate contextAggregate = new ContextAggregate(contextAction);
        contextAggregates.add(contextAggregate);

        return contextAggregate;
    }
}