package com.example.javaproject.messages;

public class ActionMessage {
    private String userId;
    private int roomId;
    private enum Action {FOLD, BET, RAISE}
    private int raiseTo;
    private String type = "ActionMessage";
    Action action;
    public ActionMessage(String userId, Action action, int raiseTo) {
        this.userId = userId;
        this.action = action;
        this.raiseTo = raiseTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ActionMessage() {}

    public Action getAction() {
        return action;
    }

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

    public void setAction(Action action) {
        this.action = action;
    }
}
