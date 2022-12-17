package com.example.javaproject.messages;

public class JoinRoomRequest {
    public int userId;
    public int roomId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public JoinRoomRequest() {}
    public JoinRoomRequest(int userId, int roomId) {
        this.roomId = roomId;
        this.userId = userId;
    }
}
