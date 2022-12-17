package com.example.javaproject.infra.Poker;

import java.util.ArrayList;
import java.util.Collections;

/*
        Public Interfaces:
                    1. Creator: CardSet()
                    2. shuffle(): shuffle cards
                    3. pop(): pop next card
                    4. reInitialize(): discard current cards, regenerate and shuffle a new card set
*/
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
        this.shuffle();
    }
    public void shuffle() {
        Collections.shuffle(this.cards);
    }
    public Card pop() {
        this.used.add(cards.get(0));
        return cards.remove(0);
    }
    public void reInitialize() {
        cards.addAll(this.used);
        this.used.clear();
        this.shuffle();
    }
}
