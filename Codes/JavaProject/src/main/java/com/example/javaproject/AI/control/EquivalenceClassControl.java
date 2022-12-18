package com.example.javaproject.AI.control;
import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.infra.Poker.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EquivalenceClassControl {
    private final Collection<EquivalenceClass> equivalenceClasses;


    public EquivalenceClassControl() {
        equivalenceClasses = new ArrayList<EquivalenceClass>();
    }

    /**
     * Converts two cards into their corrispondent equivalence class
     * */
    public EquivalenceClass cards2Equivalence(Card card1, Card card2) {
        EquivalenceClass equivalenceClass;
        if (card1.getSuits().equals(card2.getSuits())) { // suited
            equivalenceClass = new EquivalenceClassSuited(card1.getRank(),card2.getRank());
        } else {// unsuited
            equivalenceClass = new EquivalenceClassUnsuited(card1.getRank(),card2.getRank());
        }
        return equivalenceClass;
    }

    public Collection<EquivalenceClass> getEquivalenceClasses() {
        return equivalenceClasses;
    }

    public void generateAllEquivalenceClass()  {
        EquivalenceClass equivalenceClass;
        List<Integer> allCardNumbers = new ArrayList<Integer>();

        //generateThePairs
        for (int j = 0; j < Card.rankLength; j++) {
            equivalenceClass = new EquivalenceClassUnsuited(j,j);
            equivalenceClasses.add(equivalenceClass);
            allCardNumbers.add(j);
        }

        //generate other equivalences
        for(int i = 0; i < allCardNumbers.size(); i++){
            for(int j = i+1; j < allCardNumbers.size();j++){
                equivalenceClass = new EquivalenceClassUnsuited(allCardNumbers.get(i),allCardNumbers.get(j));
                equivalenceClasses.add(equivalenceClass);
                equivalenceClass = new EquivalenceClassSuited(allCardNumbers.get(i),allCardNumbers.get(j));
                equivalenceClasses.add(equivalenceClass);
            }
        }
    }
}