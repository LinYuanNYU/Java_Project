package com.example.javaproject.messages;

public class ActionMessageResponse {
    private String userId;
    private int roomId;
    private int raiseTo;
    private String type = "ActionMessage";
    String action; // "FOLD", "BET", "RAISE"
    public ActionMessageResponse(String userId, String action, int roomId, int raiseTo) {
        this.userId = userId;
        this.action = action;
        this.raiseTo = raiseTo;
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

    public ActionMessageResponse() {}

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRaiseTo() {
        return raiseTo;
    }

    public void setRaiseTo(int raiseTo) {
        this.raiseTo = raiseTo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
