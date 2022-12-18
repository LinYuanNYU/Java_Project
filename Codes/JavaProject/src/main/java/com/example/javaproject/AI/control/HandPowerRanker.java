package com.example.javaproject.AI.control;



import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.infra.Poker.CardSet;
import com.example.javaproject.AI.gameModel.*;
import com.example.javaproject.AI.utils.MapList;


import java.util.*;

public class HandPowerRanker {
    private final Comparator<Integer> cardNumberComparator = new Comparator<Integer>() {

        public int compare(Integer cardNumber1, Integer cardNumber2) {
            return cardNumber1 - cardNumber2;
        }
    }

    public HandPower rank(List<Card> cards) {
        MapList<Integer, Card> numberGroup = getNumberGroup(cards);
        MapList<Card.Color, Card> suitGroup = getSuitGroup(cards);
        List<Card> cardsSortedByNumber = getCardsSortedByNumber(cards);

        Integer straightFlushNumber = getStraightFlushNumber(suitGroup);

        // Straight flush
        if (straightFlushNumber != null) {
            return new HandPower(HandPowerType.STRAIGHT_FLUSH,
                    Arrays.asList(straightFlushNumber));
        }

        Integer cardNumberForFour = getCardNumberForCount(4, numberGroup);
        // Four of a kind
        if (cardNumberForFour != null) {
            return new HandPower(HandPowerType.FOUR_OF_A_KIND,
                    calculateSameKindTie(4, cardNumberForFour,
                            cardsSortedByNumber));
        }

        List<Integer> fullHouseCardNumbers = getFullHouse(numberGroup);
        // Full house
        if (fullHouseCardNumbers.size() == 2) {
            return new HandPower(HandPowerType.FULL_HOUSE, fullHouseCardNumbers);
        }

        // Flush
        Card.Color flushSuit = getFlush(suitGroup);
        if (flushSuit != null) {
            return new HandPower(HandPowerType.FLUSH, calculateFlushTie(
                    flushSuit, suitGroup));
        }

        // Straight
        Integer straightNumber = getStraight(numberGroup);
        if (straightNumber != null) {
            return new HandPower(HandPowerType.STRAIGHT,
                    Arrays.asList(straightNumber));
        }

        // Three of a kind
        Integer cardNumberForThree = getCardNumberForCount(3, numberGroup);
        if (cardNumberForThree != null) {
            return new HandPower(HandPowerType.THREE_OF_A_KIND,
                    calculateSameKindTie(3, cardNumberForThree,
                            cardsSortedByNumber));
        }

        // Pair(s)
        Integer cardNumberForTwo = getCardNumberForCount(2, numberGroup);
        if (cardNumberForTwo != null) {
            List<Integer> pairsCardNumber = getPairs(numberGroup);
            // Two pair
            if (pairsCardNumber.size() >= 2) {

                return new HandPower(HandPowerType.TWO_PAIR,
                        calculateTwoPairsTie(pairsCardNumber,
                                cardsSortedByNumber));
            }
            // One Pair
            else {
                return new HandPower(HandPowerType.ONE_PAIR,
                        calculateSameKindTie(2, cardNumberForTwo,
                                cardsSortedByNumber));
            }
        }

        // High Card
        return new HandPower(HandPowerType.HIGH_CARD,
                bestCardsNumberInList(cardsSortedByNumber));
    }

    private List<Integer> getFullHouse(MapList<Integer, Card> numberGroup) {
        List<Integer> fullHouseCardNumbers = new ArrayList<Integer>();

        List<Integer> cardNumbers = new ArrayList<Integer>(
                numberGroup.keySet());
        Collections.sort(cardNumbers, cardNumberComparator);
        Collections.reverse(cardNumbers);

        // Find the best cards for the triple
        for (Integer cardNumber : cardNumbers) {
            if (numberGroup.get(cardNumber).size() >= 3) {
                fullHouseCardNumbers.add(cardNumber);
                break;
            }
        }

        // Find the best card for the pair
        if (fullHouseCardNumbers.size() > 0) {
            for (Integer cardNumber : cardNumbers) {
                if (numberGroup.get(cardNumber).size() >= 2
                        && !cardNumber.equals(fullHouseCardNumbers.get(0))) {
                    fullHouseCardNumbers.add(cardNumber);
                    break;
                }
            }
        }

        return fullHouseCardNumbers;
    }

    private List<Integer> calculateTwoPairsTie(
            List<Integer> pairsCardNumber, List<Card> cardsSortedByNumber) {
        Collections.sort(pairsCardNumber, cardNumberComparator);
        Collections.reverse(pairsCardNumber);
        List<Integer> tieBreakingInformation = new ArrayList<Integer>(
                pairsCardNumber);

        for (int i = cardsSortedByNumber.size() - 1; i >= 0; i--) {
            Integer cardNumber = cardsSortedByNumber.get(i).getRank();
            if (!pairsCardNumber.contains(cardNumber)) {
                tieBreakingInformation.add(cardNumber);
                return tieBreakingInformation;
            }
        }
        return null;
    }

    private List<Integer> getPairs(MapList<Integer, Card> numberGroup) {
        List<Integer> pairsCardNumber = new ArrayList<Integer>();
        for (List<Card> cards : numberGroup) {
            if (cards.size() == 2) {
                pairsCardNumber.add(cards.get(0).getRank());
            }
        }
        Collections.sort(pairsCardNumber, cardNumberComparator);
        Collections.reverse(pairsCardNumber);

        if (pairsCardNumber.size() > 2) {
            pairsCardNumber.remove(pairsCardNumber.size() - 1);
        }

        return pairsCardNumber;
    }

    private List<Integer> calculateFlushTie(Card.Color flushSuit,
                                               MapList<Card.Color, Card> suitGroup) {
        List<Card> cards = suitGroup.get(flushSuit);
        return bestCardsNumberInList(cards);
    }

    private List<Integer> bestCardsNumberInList(List<Card> cards) {
        List<Integer> cardNumbers = cardsToCardNumber(cards);
        Collections.sort(cardNumbers, cardNumberComparator);
        Collections.reverse(cardNumbers);
        return cardNumbers.subList(0, 5);
    }

    private List<Card> getCardsSortedByNumber(List<Card> cards) {
        List<Card> cardsSortedByNumber = new ArrayList<Card>(cards);
        Collections.sort(cardsSortedByNumber);

        return cardsSortedByNumber;
    }

    private List<Integer> calculateSameKindTie(Integer sameKindCount,
                                               Integer sameKindCardNumber, List<Card> cardsSortedByNumber) {
        List<Integer> tieBreakingInformation = new ArrayList<Integer>();
        tieBreakingInformation.add(sameKindCardNumber);

        int left = 5 - sameKindCount;
        for (int i = cardsSortedByNumber.size() - 1; i >= 0; i--) {
            Card card = cardsSortedByNumber.get(i);

            if (!(card.getRank() == sameKindCardNumber) && left > 0) {
                tieBreakingInformation.add(card.getRank());
                left--;
            }
        }

        return tieBreakingInformation;
    }

    private Integer getCardNumberForCount(Integer count,
                                             MapList<Integer, Card> numberGroup) {
        for (Map.Entry<Integer, List<Card>> entry : numberGroup.entrySet()) {
            if (entry.getValue().size() == count) {
                return entry.getKey();
            }
        }
        return null;
    }

    private Integer getStraight(MapList<Integer, Card> numberGroup) {
        List<Integer> cardNumbers = new ArrayList<Integer>(
                numberGroup.keySet());
        return getStraightNumber(cardNumbers);
    }

    private Integer getStraightFlushNumber(MapList<Card.Color, Card> suitGroup) {
        Card.Color flushSuit = getFlush(suitGroup);
        if (flushSuit == null) {
            return null;
        }

        List<Card> cards = suitGroup.get(flushSuit);
        List<Integer> cardNumbers = cardsToCardNumber(cards);

        return getStraightNumber(cardNumbers);
    }

    private List<Integer> cardsToCardNumber(List<Card> cards) {
        List<Integer> cardNumbers = new ArrayList<Integer>();

        for (Card card : cards) {
            cardNumbers.add(card.getRank());
        }
        return cardNumbers;
    }

    private Integer getStraightNumber(List<Integer> cardNumbers) {
        Integer straightNumber = null;
        int straightCount = 1;
        int prevPower = 0;
        Collections.sort(cardNumbers, cardNumberComparator);
        for (Integer cardNumber : cardNumbers) {
            if (cardNumber == prevPower + 1) {
                straightCount++;
                if (straightCount >= 5) {
                    straightNumber = cardNumber;
                }
            } else {
                straightCount = 1;
            }
            prevPower = cardNumber;
        }
        return straightNumber;
    }

    private Card.Color getFlush(MapList<Card.Color, Card> suitGroup) {
        for (List<Card> cards : suitGroup) {
            if (cards.size() >= 5) {
                return cards.get(0).getSuits();
            }
        }
        return null;
    }

    private MapList<Integer, Card> getNumberGroup(List<Card> cards) {
        MapList<Integer, Card> numberGroup = new MapList<Integer, Card>();
        for (Card card : cards) {
            numberGroup.add(card.getRank(), card);
        }
        return numberGroup;
    }

    private MapList<Card.Color, Card> getSuitGroup(List<Card> cards) {
        MapList<Card.Color, Card> suitGroup = new MapList<Card.Color, Card>();
        for (Card card : cards) {
            suitGroup.add(card.getSuits(), card);
        }
        return suitGroup;
    }
}
