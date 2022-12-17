package com.example.javaproject.infra.GameRoom;

import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.infra.User.User;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRoom {
    private ArrayList<User> users;
    private CardSet cards;
    private static AtomicInteger id = new AtomicInteger(0);
    private int roomId;
    public static enum GameState {IDLE, PLAYING};
    private GameState state;
    public GameRoom() {
        this.roomId = id.incrementAndGet();
        users = new ArrayList<>();
        cards = new CardSet();
        state = GameState.IDLE;
    }

    public int getRoomId() {
        return roomId;
    }

    public boolean addUser(int userId) {
        return this.users.add(new User(userId));
    }
    public GameState getState() {
        return this.state;
    }
}
