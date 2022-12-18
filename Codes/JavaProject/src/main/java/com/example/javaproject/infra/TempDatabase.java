package com.example.javaproject.infra;

/*
    A temporary database, we will transfer this into a SQL database later
*/

import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.User.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TempDatabase {
    private static HashMap<String, User> registeredUsers = new HashMap<>();
    private static HashMap<Integer, GameRoom> gameRooms = new HashMap<>();

    // Below functions simulated SQL databases' interfaces.
    // they should work similar to SQL database interfaces we will have in the future

    // register user to database
    public static void registerUser(String userId) {
        registeredUsers.put(userId, new User(userId));
    }
    // Check if user is a registered user
    public static boolean checkUser(int userId) {
        return registeredUsers.containsKey(userId);
    }
    public static void addRoom(GameRoom room) {
        gameRooms.put(room.getRoomId(), room);
    }

    // Check if roomId is valid(valid roomId, also room state is IDLE, which means game has not started)
    public static boolean checkRoom(int roomId) {
        if (gameRooms.containsKey(roomId) && gameRooms.get(roomId).getState() == GameRoom.GameState.IDLE) {
            return true;
        } else {
            return false;
        }
    }
    public static GameRoom getRoom(int roomId) {
        if (checkRoom(roomId)) {
            return gameRooms.get(roomId);
        }
        return null;
    }
    public static boolean userJoinRoom(String userId, int roomId) {
        GameRoom room = gameRooms.get(roomId);
        if (!room.getUserIDs().contains(userId)) {
            return gameRooms.get(roomId).addUser(userId);
        } else {
            return false;
        }
    }
}
