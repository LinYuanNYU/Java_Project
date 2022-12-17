package com.example.javaproject.messages;

import com.example.javaproject.infra.Poker.Card;
import com.example.javaproject.infra.User.User;

import java.util.ArrayList;
import java.util.List;

public class GameStateMessage {
    int money;
    ArrayList<User> players;
    List<Card> publicCards;
    int waitingForUserId;
    
}
