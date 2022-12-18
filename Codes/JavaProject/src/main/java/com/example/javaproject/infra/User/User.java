package com.example.javaproject.infra.User;

import com.example.javaproject.infra.Poker.Card;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private int money;
    private ArrayList<Card> cards;
    public User(String userId, int initialMoney) {
        this.userId = userId;
        money = initialMoney;
        cards = new ArrayList<>();
    }
    public User() {}
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getId() {
        return userId;
    }
    public void changeMoney(int amount) {
        this.money += amount;
    }
    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public void setCards(Card a, Card b) {
        cards.add(a);
        cards.add(b);
    }
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
