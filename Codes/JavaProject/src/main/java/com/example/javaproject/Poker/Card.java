package com.example.javaproject.Poker;


import com.fasterxml.jackson.databind.KeyDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Card {
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
}
