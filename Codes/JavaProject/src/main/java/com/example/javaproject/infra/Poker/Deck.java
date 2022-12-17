package com.example.javaproject.infra.Poker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
* Deck is built for each AI to predict what's left in the rest of cards
* To get probabilities
* */
public class Deck {
    private final List<Card> cards = new ArrayList<Card>();

    public Deck() {

        for (Card.Color suit : Card.Color.values()) {
            for (int j = 0; j < Card.rankLength; j++) {
                Card card = new Card(suit, j);
                cards.add(card);
            }
        }

        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card removeTopCard() {
        return cards.remove(0);
    }

    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    public List<List<Card>> fromDeckToCouplesOfCard(){
        List<List<Card>> couplesOfCard = new ArrayList<List<Card>>();
        int i,j;
        for(i = 0; i < this.cards.size(); i++){
            for (j = i+1; j < this.cards.size(); j++){
                List<Card> tmpCards = new ArrayList<Card>();
                tmpCards.add(this.cards.get(i));
                tmpCards.add(this.cards.get(j));
                couplesOfCard.add(tmpCards);
            }
        }
        return couplesOfCard;
    }

    public List<Card> fromDeckTooneOfCard(){
        List<Card> oneOfCard = new ArrayList<Card>();
        int i;
        for(i = 0; i < this.cards.size(); i++){
            oneOfCard.add(this.cards.get(i));
        }
        return oneOfCard;
    }
}