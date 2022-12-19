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
    private static ArrayList<Card> cards;
    private static ArrayList<Card> used;
    private static ArrayList<Card> flopCards;
    private static Card turnCard;
    private static Card riverCard;

    public CardSet() {
        cards = new ArrayList<>();
        for (Card.Color color : Card.Color.values()) {
            for (int j = 0; j < Card.rankLength; j++) {
                cards.add(new Card(color, j));
            }
        }
        used = new ArrayList<>();
        this.shuffle();
        flopCards = new ArrayList<Card>();
//        turnCard = new Card();


    }
    public static void shuffle() {
        Collections.shuffle(cards);
    }
    public static Card pop() {
        used.add(cards.get(0));
        return cards.remove(0);
    }
    public static void reInitialize() {
        cards.addAll(used);
        used.clear();
        flopCards.clear();
        turnCard = null;
        riverCard = null;
        shuffle();
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static ArrayList<Card> getUsedCards() {
        return used;
    }

    public static void popFlopCards() {
        flopCards.add(cards.get(0));
        cards.remove(0);
        flopCards.add(cards.get(0));
        cards.remove(0);
        flopCards.add(cards.get(0));
        cards.remove(0);
    }

    public static void popTurnCards() {
        turnCard = cards.get(0);
        cards.remove(0);
    }

    public static void popRiverCards() {
        riverCard = cards.get(0);
        cards.remove(0);
    }

    public static ArrayList<Card> getFlopCards() {
        return flopCards;
    }

    public static Card getTurnCard() {
        return turnCard;
    }

    public static Card getRiverCard() {
        return turnCard;
    }


}
