package com.example.javaproject.infra;

/*
    A temporary database, we will transfer this into a SQL database later
*/

import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.User.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TempDatabase {
    private static ConcurrentHashMap<String, User> registeredUsers = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, GameRoom> gameRooms = new ConcurrentHashMap<>();

    // Below functions simulated SQL databases' interfaces.
    // they should work similar to SQL database interfaces we will have in the future

    // register user to database
    public static void registerUser(String userId, int initialMoney) {
        registeredUsers.put(userId, new User(userId, initialMoney));
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
        if (gameRooms.containsKey(roomId)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean checkUser(String userId) {
        return registeredUsers.containsKey(userId);
    }
    public static User getUser(String userId) {
        return registeredUsers.getOrDefault(userId, null);
    }
    public static GameRoom getRoom(int roomId) {
        if (checkRoom(roomId)) {
            return gameRooms.get(roomId);
        }
        return null;
    }
    public static boolean userJoinRoom(String userId, int roomId) {
        GameRoom room = gameRooms.get(roomId);
        User user = TempDatabase.getUser(userId);
        if (!room.getUserIDs().contains(userId)) {
            return gameRooms.get(roomId).addUser(user);
        } else {
            return false;
        }
    }
}
