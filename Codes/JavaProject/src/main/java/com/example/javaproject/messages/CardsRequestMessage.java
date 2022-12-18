package com.example.javaproject.messages;

import com.example.javaproject.infra.Poker.Card;

import java.util.ArrayList;

public class CardsRequestMessage {
    public String userId;
    public int roomId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public CardsRequestMessage() {}
    public CardsRequestMessage(String userId, int roomId) {
        this.roomId = roomId;
        this.userId = userId;
    }
}
