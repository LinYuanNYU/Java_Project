package com.example.javaproject.infra.GameRoom;

import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.infra.User.User;
import com.example.javaproject.messages.ActionMessage;
import org.yaml.snakeyaml.events.Event;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRoom {
    private ArrayList<User> users;
    private String ownerId;
    private CardSet cards;
    private static AtomicInteger id = new AtomicInteger(0);
    private int roomId;
    public static enum GameState {IDLE, PLAYING};
    private GameState state;
    public GameRoom(String ownerId) {
        this.ownerId = ownerId;
        this.roomId = id.incrementAndGet();
        users = new ArrayList<>();
        cards = new CardSet();
        state = GameState.IDLE;
    }

    public int getRoomId() {
        return roomId;
    }
    public String getOwnerId() {
        return ownerId;
    }

    public boolean addUser(User user) {
        return this.users.add(user);
    }
    public GameState getState() {
        return this.state;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public ArrayList<String> getUserIDs() {
        ArrayList<String> ids = new ArrayList<>();
        for (User user : users) {
            ids.add(user.getId());
        }
        return ids;
    }
    public void start() {

    }
    public void action(ActionMessage action) {


    }
}
