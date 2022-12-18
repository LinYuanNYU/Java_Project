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


public class CardSetNoneStatic {
    private ArrayList<Card> cards;
    private ArrayList<Card> used;

    public CardSetNoneStatic() {
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
        Collections.shuffle(cards);
    }
    public Card pop() {
        used.add(cards.get(0));
        return cards.remove(0);
    }
    public void reInitialize() {
        cards.addAll(used);
        used.clear();
        shuffle();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> getUsedCards() {
        return used;
    }

}
