package com.example.javaproject.messages;

public class JoinRoomRequest {
    public String userId;
    public int roomId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public JoinRoomRequest() {}
    public JoinRoomRequest(String userId, int roomId) {
        this.roomId = roomId;
        this.userId = userId;
    }
}
