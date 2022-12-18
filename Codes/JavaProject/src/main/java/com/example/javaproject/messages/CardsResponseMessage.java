package com.example.javaproject.messages;

import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;

import java.util.ArrayList;
import java.util.HashMap;

public class CardsResponseMessage {
    private ArrayList<Card> publicCards;
    private HashMap<String, ArrayList<Card>> handCards;
    public CardsResponseMessage(int roomId) {
        GameRoom room = TempDatabase.getRoom(roomId);
        publicCards = new ArrayList<>();
        publicCards.addAll(room.getPublicCards());
        ArrayList<User> users = room.getUsers();
        for (int i = 0; i < users.size(); i++) {
            handCards.put(users.get(i).getId(), new ArrayList<>(users.get(i).getCards()));
        }
    }
    public ArrayList<Card> getPublicCards() {
        return publicCards;
    }

    public void setHandCards(HashMap<String, ArrayList<Card>> handCards) {
        this.handCards = handCards;
    }

    public void setPublicCards(ArrayList<Card> publicCards) {
        this.publicCards = publicCards;
    }

    public HashMap<String, ArrayList<Card>> getHandCards() {
        return handCards;
    }

    public void setCards(ArrayList<Card> cards) {

    }
    public CardsResponseMessage() {}
}
