package com.example.javaproject.messages;

import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;

import java.util.ArrayList;
import java.util.Random;

public class GameStartMessage {
    int money;
    ArrayList<User> players;
    int waitingForUserId;

    public GameStartMessage() {}
    public GameStartMessage(int roomId) {
        GameRoom room = TempDatabase.getRoom(roomId);
        players = new ArrayList<>();
        players.addAll(room.getUsers());
        int idx = new Random().nextInt(players.size() - 1);
        waitingForUserId = players.get(idx).getId();
        money = 0;
    }

    public int getMoney() {
        return money;
    }

    public int getWaitingForUserId() {
        return waitingForUserId;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setWaitingForUserId(int waitingForUserId) {
        this.waitingForUserId = waitingForUserId;
    }
    public ArrayList<User> getPlayers() {
        return this.players;
    }
}
