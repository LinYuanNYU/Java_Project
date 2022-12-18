package com.example.javaproject.messages;

import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;

import java.util.ArrayList;

public class RoomStateMessage {
    private String ownerId;
    private int roomId;
    private ArrayList<User> members;
    public RoomStateMessage(int roomId) {
        this.roomId = roomId;
        GameRoom room = TempDatabase.getRoom(roomId);
        this.ownerId = room.getOwnerId();
        members = new ArrayList<>();
        members.addAll(room.getUsers());
    }
    public RoomStateMessage() {}

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    public String getOwnerId() {
        return ownerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }
}
