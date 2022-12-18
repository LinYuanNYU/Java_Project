package com.example.javaproject.messages;

public class GameStartRequest {
    public String userId;
    public int roomId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public GameStartRequest() {}
    public GameStartRequest(String userId, int roomId) {
        this.roomId = roomId;
        this.userId = userId;
    }
}
