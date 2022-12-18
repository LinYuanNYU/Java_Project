package com.example.javaproject.infra.Poker;

import java.util.ArrayList;
import java.util.List;

public class EquivalenceClassSuited extends EquivalenceClass {

    public EquivalenceClassSuited(Integer number1, Integer number2) {
        super(number1, number2);
    }

    @Override
    public List<Card> equivalence2cards() {
        List<Card> cards = new ArrayList<Card>();
        Card card1, card2;
        card1 = new Card(Card.Color.HEART, this.getNumber1());
        card2 = new Card(Card.Color.SPADE, this.getNumber1());
        cards.add(card1);
        cards.add(card2);
        return cards;
    }

    @Override
    public String getType() {
        return "SUITED";
    }

    @Override
    public String toString() {
        return "Equivalence Class Suited (" + getNumber1() + ","
                + getNumber2() + ")";
    }

}
