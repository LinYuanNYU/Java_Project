package com.example.javaproject.messages;

public class GameStartRequest {
    public int userId;
    public int roomId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public GameStartRequest() {}
    public GameStartRequest(int userId, int roomId) {
        this.roomId = roomId;
        this.userId = userId;
    }
}
