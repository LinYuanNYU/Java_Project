package com.example.javaproject.AI.control;
import com.example.javaproject.AI.Game;
import com.example.javaproject.AI.gameProperties.GameProperties;
import com.example.javaproject.infra.Poker.CardSet;


public class PokerControl {
    private final Game game;
    private final GameProperties gameProperties;
    private final GameHandControl gameHandController;


    /*
     * Use Game Controller to set grames properties for a singel hand game.
     *  Each hand will init a new gameHandController.
     *
     * */

    public PokerControl(final GameHandControl gameHandController,
                        final GameProperties gameProperties) {
        this.gameHandController = gameHandController;
        this.gameProperties = gameProperties;

        game = new Game(gameProperties.getPlayers());

    }

    public void play() {
        for (int i = 0; i < gameProperties.getNumberOfHands(); i++) {
            CardSet carSet = new CardSet();
            gameHandController.play(game);
            game.setNextDealer();
        }
    }

}