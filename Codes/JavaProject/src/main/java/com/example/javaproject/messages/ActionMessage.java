package com.example.javaproject.messages;

public class ActionMessage {
    private String userId;
    private int roomId;
    private int raiseAmount;
    private String type = "ActionMessage";
    String action; // "FOLD", "BET", "RAISE"
    public ActionMessage(String userId, String action, int roomId, int raiseAmount) {
        this.userId = userId;
        this.action = action;
        this.raiseAmount = raiseAmount;
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getAction() {
        return this.action;
    }
    public void setType(String type) {
        this.type = type;
    }

    public ActionMessage() {}

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRaiseAmount() {
        return raiseAmount;
    }

    public void setRaiseAmount(int raiseAmount) {
        this.raiseAmount = raiseAmount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
