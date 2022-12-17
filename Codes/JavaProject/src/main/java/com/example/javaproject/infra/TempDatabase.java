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
    private static HashMap<Integer, User> registeredUsers = new HashMap<>();
    private static HashMap<Integer, GameRoom> gameRooms = new HashMap<>();

    // Below functions simulated SQL databases' interfaces.
    // they should work similar to SQL database interfaces we will have in the future

    // register user to database
    public static void registerUser(int userId) {
        registeredUsers.put(userId, new User(userId));
    }
    // Check if user is a registered user
    public static boolean checkUser(int userId) {
        return registeredUsers.containsKey(userId);
    }
    public static void createRoom(int roomId) {
        GameRoom room = new GameRoom();
        gameRooms.put(room.getRoomId(), room);
    }
    public static boolean hasRoom(int roomId) {
        return gameRooms.containsKey(roomId);
    }
    // Check if roomId is valid(valid roomId, also room state is IDLE, which means game has not started)
    public static boolean checkRoom(int roomId) {
        if (gameRooms.containsKey(roomId) && gameRooms.get(roomId).getState() == GameRoom.GameState.IDLE) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean userJoinRoom(int userId, int roomId) {
        return gameRooms.get(roomId).addUser(userId);
    }
}
