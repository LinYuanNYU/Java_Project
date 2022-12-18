package com.example.javaproject.messages;

import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;

import java.util.ArrayList;
import java.util.Random;

public class GameStartMessage {
    boolean gameStartFlag;
    int money;
    ArrayList<User> players;
    String waitingForUserId;
    private String type = "GameStartMessage";

    public GameStartMessage() {}
    public GameStartMessage(int roomId) {
        GameRoom room = TempDatabase.getRoom(roomId);
        players = new ArrayList<>();
        players.addAll(room.getUsers());
        int idx = new Random().nextInt(players.size() - 1);
        waitingForUserId = players.get(idx).getId();
        money = 0;
        gameStartFlag = true;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setGameStartFlag(boolean gameStartFlag) {
        this.gameStartFlag = gameStartFlag;
    }

    public boolean isGameStartFlag() {
        return gameStartFlag;
    }

    public int getMoney() {
        return money;
    }

    public String getWaitingForUserId() {
        return waitingForUserId;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setWaitingForUserId(String waitingForUserId) {
        this.waitingForUserId = waitingForUserId;
    }
    public ArrayList<User> getPlayers() {
        return this.players;
    }
}
