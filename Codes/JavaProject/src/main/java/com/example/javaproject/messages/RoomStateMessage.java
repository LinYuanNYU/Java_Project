package com.example.javaproject.messages;

import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;

import java.util.ArrayList;

public class RoomStateMessage {
    private int ownerId;
    private int roomId;
    private ArrayList<User> members;
    public RoomStateMessage(int roomId) {
        this.roomId = roomId;
        GameRoom room = TempDatabase.getRoom(roomId);
        this.ownerId = room.getOwnerId();
        members = new ArrayList<>();
        members.addAll(room.getUsers());
    }
}
