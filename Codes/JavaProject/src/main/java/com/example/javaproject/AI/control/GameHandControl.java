package com.example.javaproject.AI.control;




import com.example.javaproject.AI.gameModel.*;
import com.example.javaproject.AI.Game;
import com.example.javaproject.AI.Player;
import com.example.javaproject.AI.gameProperties.GameProperties;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.Evaluator.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.javaproject.AI.Persistent.*;
import com.example.javaproject.AI.gameModel.opponentModel.*;
import com.example.javaproject.infra.Poker.CardSet;


public class GameHandControl {

    private final GameProperties gameProperties;


    public GameHandControl(
                              final GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    public void play(Game game) {

        //System.out.println(game.getPlayers());
        Player[] q = new Player[game.getPlayers().size()];
        int i = 0;
        for (Player p: game.getPlayers()){
            if (p.getMoney() <= 0){
                q[i] = p;
                i++;
            }
        }
        for (int j = 0;j < i;j++){
            game.kickPlayer(q[j]);
        }


        if(game.getPlayers().size() == 1){
            /*Only one player left. TODO */
            System.out.println("There is only one player left! Game Over!");
            System.exit(0);
        }else{
            /*More than one player on the game. Continue. TODO*/

            for (Player player : game.getPlayers()) {
                System.out.println("Player" + player.number + " (" + player.getPlayerController().toString() + ")" + ": " + player
                        .getMoney() + "$");
            }
        }

        GameHand gameHand = createGameHand(game);


        Boolean haveWinner = false;

        while (!gameHand.getBettingRoundName().equals(
                BettingRoundName.POST_RIVER)
                && !haveWinner) {
            haveWinner = playRound(gameHand);
        }
        CardSet.reInitialize();

        if (!haveWinner) {
            showDown(gameHand);
        }
    }


    private GameHand createGameHand(Game game) {
        GameHand gameHand = new GameHand(game.getPlayers());
        game.addGameHand(gameHand);
        return gameHand;
    }

    protected Boolean playRound(GameHand gameHand) {
        gameHand.nextRound();
        logBettingRound(gameHand);
        int toPlay = gameHand.getPlayersCount();
        if (gameHand.getBettingRoundName().equals(BettingRoundName.PRE_FLOP)) {
            takeBlinds(gameHand);
            toPlay--; // Big blinds don't have to call on himself if no raise :)
        }

        while (toPlay > 0) {
            Player player = gameHand.getNextPlayer();
            BettingDecision bettingDecision = player.decide(gameHand);
            //System.out.println(player.number);
            //System.out.println(player.getMoney());
            //We can't raise at second turn
            //if (turn > numberOfPlayersAtBeginningOfRound
            //  && bettingDecision.equals(BettingDecision.RAISE)) {
            //	bettingDecision = BettingDecision.CALL;
            //}

            // After a raise, every active players after the raiser must play
            if (bettingDecision.equals(BettingDecision.RAISE)) {
                // toPlay = gameHand.getPlayersCount() - 1;
                // make sure the one wants to raise has enough money
                if(player.getMoney() < gameHand.getCurrentBettingRound().getHighestBet() + gameProperties.getBigBlind()){
                    if(player.getMoney() >= gameHand.getCurrentBettingRound().getHighestBet()) {
                        bettingDecision = BettingDecision.CALL;
                    }
                    else{
                        bettingDecision = BettingDecision.FOLD;
                    }

                }
                else{
                    toPlay = gameHand.getPlayersCount();

                }
            }

            if(bettingDecision.equals(BettingDecision.CALL) && player.getMoney() < gameHand.getCurrentBettingRound().getHighestBet())
                bettingDecision = BettingDecision.FOLD;

            applyDecision(gameHand, player, bettingDecision);
            toPlay--;

        }

        // Check if we have a winner
        if (gameHand.getPlayersCount() == 1) {
            Player winner = gameHand.getCurrentPlayer();
            winner.addMoney(gameHand.getTotalBets());
            System.out.println("WINNER:" + winner + ": WIN! +" + gameHand.getTotalBets() + "$");

            return true;
        }
        return false;
    }

    private void logBettingRound(GameHand gameHand) {
        String logMsg = "---" + gameHand.getBettingRoundName();
        logMsg += " (" + gameHand.getPlayersCount() + " players, ";
        logMsg += gameHand.getTotalBets() + "$)";
        if (!gameHand.getSharedCards().isEmpty()) {
            logMsg += " " + gameHand.getSharedCards();
        }
        System.out.println(logMsg);
    }

    private void takeBlinds(GameHand gameHand) {
        Player smallBlindPlayer = gameHand.getNextPlayer();
        Player bigBlindPlayer = gameHand.getNextPlayer();
        bigBlindPlayer.bigBlind = true;
        int sb = Math.min(smallBlindPlayer.getMoney(), gameProperties.getSmallBlind());
        int bb = Math.min(bigBlindPlayer.getMoney(), gameProperties.getBigBlind());

        System.out.println(smallBlindPlayer + ": Small blind "
                + sb + "$");
        System.out.println(bigBlindPlayer + ": Big blind "
                + bb + "$");

        gameHand.getCurrentBettingRound().placeBet(smallBlindPlayer,
                sb);
        gameHand.getCurrentBettingRound().placeBet(bigBlindPlayer,
                bb);
    }

    private void applyDecision(GameHand gameHand, Player player, BettingDecision bettingDecision) {
        double handStrength = HandStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
                gameHand.getPlayersCount());
        gameHand.applyDecision(player, bettingDecision, gameProperties, handStrength);

        BettingRound bettingRound = gameHand.getCurrentBettingRound();
        System.out.println(player + ": " + bettingDecision + " "
                + bettingRound.getBetForPlayer(player) + "$");
    }

    private List<Player> getWinners(GameHand gameHand) {
        Iterable<Player> activePlayers = gameHand.getPlayers();
        List<Card> sharedCards = gameHand.getSharedCards();

        HandPower bestHandPower = null;
        List<Player> winners = new ArrayList<Player>();
        for (Player player : activePlayers) {
            List<Card> mergeCards = new ArrayList<Card>(player.getHoleCards());
            mergeCards.addAll(sharedCards);
            HandPower handPower = HandPowerRanker.rank(mergeCards);

            System.out.println(player + ": " + handPower);

            if (bestHandPower == null || handPower.compareTo(bestHandPower) > 0) {
                winners.clear();
                winners.add(player);
                bestHandPower = handPower;
            } else if (handPower.equals(bestHandPower)) {
                winners.add(player);
            }
        }
        return winners;
    }

    protected void showDown(GameHand gameHand) {
        System.out.println("--- Showdown");

        // Showdown
        List<Player> winners = getWinners(gameHand);

        // Gains
        int gain = gameHand.getTotalBets() / winners.size();
        int modulo = gameHand.getTotalBets() % winners.size();
        for (Player winner : winners) {
            int gainAndModulo = gain;
            if (modulo > 0) {
                gainAndModulo += modulo;
            }
            winner.addMoney(gainAndModulo);
            System.out.println("WINNER: "+winner + ": WIN! +" + gainAndModulo + "$");

            modulo--;
        }

        // Opponent modeling
        OpponentModeler.save(gameHand);


    }
}