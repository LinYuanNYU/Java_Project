package com.example.javaproject.infra.Poker;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
        Public Interfaces:
                    1. rankAsString(): Transfer integer rank to string(1->"Ace", 2->"2", ..., 13 -> "King")
                    2. Creator: Card(enum Color suit, int rank)
                    3. Getters: getRank, getRankString, getSuit

*/

public class Card implements Comparable<Card>{

    public static enum Color {SPADE, HEART, DIAMOND, CLUB};
    public final static int rankLength = 13;
    private static String ranks[] = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    public static String rankAsString(int rank) {
        if (rank < 0 || rank >= Card.ranks.length) {
            return null;
        }
        return Card.ranks[rank];
    }

    private Color suits;
    private int rank;
    private Logger logger = LoggerFactory.getLogger(Card.class);
    public Card(Color color, int rank) {
        if (rank < 0 || rank > Card.ranks.length) {
            logger.error("rank of card out of bound, should be [0, 13], get: " + String.valueOf(rank));
        }
        this.suits = color;
        this.rank = rank;
    }

    public Color getSuits() {
        return suits;
    }
    public String getRankString() {
        return Card.rankAsString(this.rank);
    }
    public int getRank() {
        return this.rank;
    }

    @Override
    public int compareTo(Card card) {
        return this.rank - card.rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }

        Card other = (Card) obj;

        return suits.equals(other.suits) && (rank==other.rank);
    }


}
