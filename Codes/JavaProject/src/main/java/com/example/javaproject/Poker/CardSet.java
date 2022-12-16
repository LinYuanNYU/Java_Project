package com.example.javaproject.Poker;

import java.util.ArrayList;
import java.util.Collections;

public class CardSet {
    private ArrayList<Card> cards;
    private ArrayList<Card> used;
    public CardSet() {
        cards = new ArrayList<>();
        for (Card.Color color : Card.Color.values()) {
            for (int j = 0; j < Card.rankLength; j++) {
                cards.add(new Card(color, j));
            }
        }
        used = new ArrayList<>();
    }
    public void Shuffle() {
        Collections.shuffle(this.cards);
    }
    public Card pop() {
        this.used.add(cards.get(0));
        return cards.remove(0);
    }
    public void reInitialize() {
        cards.addAll(this.used);
        this.used.clear();
        this.Shuffle();
    }
}
