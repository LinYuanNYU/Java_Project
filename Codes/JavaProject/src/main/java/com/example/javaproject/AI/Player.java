package com.example.javaproject.AI;

import java.util.Arrays;
import java.util.List;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.AI.gameModel.BettingDecision;
import com.example.javaproject.AI.control.PlayerControl;
import com.example.javaproject.AI.gameModel.GameHand;



public class Player {
    public final int number;
    private final PlayerControl playerController;
    public int money;
    private List<Card> holeCards;
    public boolean bigBlind = false;

    public Player(int number, int initialMoney,
                  PlayerControl playerController) {
        this.number = number;
        this.money = initialMoney;
        this.playerController = playerController;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }

        Player otherPlayer = (Player) o;

        return number == otherPlayer.number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Player #");
        stringBuilder.append(getNumber());

        if (holeCards != null) {
            stringBuilder.append(holeCards.toString());
        }

        return stringBuilder.toString();
    }

    public BettingDecision decide(GameHand gameHand) {
        return playerController.decide(this, gameHand);
    }

    public int getNumber() {
        return number;
    }

    public int getMoney() {
        return money;
    }

    public void removeMoney(int amount) {
        money -= amount;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void setHoleCards(Card hole1, Card hole2) {
        holeCards = Arrays.asList(hole1, hole2);
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public PlayerControl getPlayerController() {
        return playerController;
    }


}
