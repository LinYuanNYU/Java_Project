package com.example.javaproject.infra.User;

import com.example.javaproject.infra.Poker.Card;

import java.util.List;

public class User {
    private String userId;
    private int money;
    private List<Card> cards;
    public User(String userId) {
        this.userId = userId;
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
}
