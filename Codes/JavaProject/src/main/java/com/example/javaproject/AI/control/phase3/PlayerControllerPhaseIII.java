package com.example.javaproject.AI.control.phase3;
import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.AI.control.phase2.PlayerControllerPhaseII;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.infra.Poker.*;
import com.example.javaproject.AI.control.PlayerControl;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.control.HandPowerRanker;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.AI.gameModel.*;
import com.example.javaproject.AI.gameModel.opponentModel.*;
import com.example.javaproject.AI.Persistent.*;
import com.example.javaproject.AI.Evaluator.*;
import com.example.javaproject.AI.control.*;
import com.example.javaproject.AI.control.phase2.*;
import java.util.List;
public abstract class PlayerControllerPhaseIII extends PlayerControl {
    private final PlayerControllerPhaseIIBluffConservative playerControllerPhaseIIBluffConservative;
    private final HandStrengthEvaluator handStrengthEvaluator;
    private final OpponentModeler opponentModeler;

    protected PlayerControllerPhaseIII(PlayerControllerPhaseIIBluffConservative playerControllerPhaseIIBluffConservative, HandStrengthEvaluator
            handStrengthEvaluator, OpponentModeler opponentModeler) {
        this.playerControllerPhaseIIBluffConservative = playerControllerPhaseIIBluffConservative;
        this.handStrengthEvaluator = handStrengthEvaluator;
        this.opponentModeler = opponentModeler;
    }


    public BettingDecision decidePreFlop(Player player, GameHand gameHand, List<Card> cards) {
        return playerControllerPhaseIIBluffConservative.decidePreFlop(player, gameHand, cards);
    }


    public BettingDecision decideAfterFlop(Player player, GameHand gameHand, List<Card> cards) {
        BettingRound currentBettingRound = gameHand.getCurrentBettingRound();
        double handStrength = handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayersCount());
        int opponentsModeledCount = 0;
        //int oppponentsWithBetterEstimatedHandStrength = 0;
        int type = 0;
        double x = 0;

        for (Player opponent : gameHand.getPlayers()) {
            // Only try to model opponent
            if (!opponent.equals(player)) {
                ContextAction contextAction = currentBettingRound.getContextActionForPlayer(opponent);

                if (contextAction != null) {
                    ModelResult modelResult = opponentModeler.getEstimatedHandStrength(contextAction);

                    // If we don't have enough occurence or if the variance is big, the information is not valuable
                    if (modelResult.getNumberOfOccurences() > 3 && modelResult.getHandStrengthDeviation() <= 0.15) {
                        opponentsModeledCount++;
                        if (modelResult.getHandStrengthAverage() + modelResult.getHandStrengthDeviation() < handStrength) {
                            type = 0;
                        }else if(handStrength < modelResult.getHandStrengthAverage() - modelResult.getHandStrengthDeviation()){
                            type = 1;
                        }else if(handStrength < modelResult.getHandStrengthAverage()){
                            type = 2;
                        }else{
                            x = (handStrength - modelResult.getHandStrengthAverage())/modelResult.getHandStrengthDeviation();
                            type = 3;
                        }
                    }
                }
            }
        }

        // If we don't have enough context action in the current betting round
        if ((double) opponentsModeledCount / gameHand.getPlayersCount() < 0.5) {
            // We fallback to a phase II bot
            return playerControllerPhaseIIBluffConservative.decideAfterFlop(player, gameHand, cards);
        }

        return decideBet(gameHand, player, type, x, opponentsModeledCount);
    }

    protected abstract BettingDecision decideBet(GameHand gameHand, Player player,
                                                 int type,
                                                 double x,
                                                 int opponentsModeledCount);
}
