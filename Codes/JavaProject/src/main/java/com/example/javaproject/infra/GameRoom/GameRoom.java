package com.example.javaproject.infra.GameRoom;

import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.infra.Poker.CardSetNoneStatic;
import com.example.javaproject.infra.User.User;
import com.example.javaproject.messages.ActionMessage;
import org.yaml.snakeyaml.events.Event;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRoom {
    private ArrayList<User> users;
    private String ownerId;
    private ArrayList<Card> publicCards;
    private CardSetNoneStatic cards;
    private static AtomicInteger id = new AtomicInteger(0);
    private int roomId;
    public static enum GameState {IDLE, PLAYING};
    private GameState state;
    public GameRoom(String ownerId) {
        this.ownerId = ownerId;
        this.roomId = id.incrementAndGet();
        users = new ArrayList<>();
        cards = new CardSetNoneStatic();
        state = GameState.IDLE;
        publicCards = new ArrayList<>();
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

    public List<Card> getPublicCards() {
        return publicCards;
    }

    public void setPublicCards(ArrayList<Card> publicCards) {
        this.publicCards = publicCards;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void setCards(CardSetNoneStatic cards) {
        this.cards = cards;
    }
    public void assignCards() {
        // Assign cards to each player
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setCards(cards.pop(), cards.pop());
        }
        // Assign cards to public
        for (int i = 0; i < 5; i++) {
            publicCards.add(cards.pop());
        }
    }
    public void start() {
        this.state = GameState.PLAYING;
        assignCards();
    }
    public void action(ActionMessage action) {
        if (action.getAction() == "BET") {

        } else if (action.getAction() == "RAISE") {

        } else {

        }
    }
}
