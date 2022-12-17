package com.example.javaproject.controller;

import com.example.javaproject.messages.JoinRoomRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameRoomController {
    Logger logger = LoggerFactory.getLogger(GameRoomController.class);


    @PostMapping("/create")
    public int createGameRoom(@RequestBody User user) {
        logger.info(String.format("User: {%d} creates a game room", user.getId()));
        GameRoom room = new GameRoom();
        room.addUser(user.getId());
        TempDatabase.addRoom(room);
        return room.getRoomId();
    }

    @MessageMapping("/room")
    @SendTo("/topic/game_room")
    public boolean joinGameRoom(@RequestBody JoinRoomRequest request) {
        logger.info(String.format("User: {%d} want to join room: {%d}", request.userId, request.roomId));
        if (!TempDatabase.checkUser(request.userId)) {
            logger.error(String.format("Invalid userId: %d", request.userId));
            return false;
        }
        if (TempDatabase.getRoom(request.roomId) == null) {
            createGameRoom(new User(request.userId));
        }
        if (!TempDatabase.checkRoom(request.roomId)) {
            logger.error(String.format("Invalid roomId: %d", request.roomId));
            return false;
        }
        return TempDatabase.userJoinRoom(request.userId, request.roomId);
    }
    @PostMapping("/start")
    public GameRoom.GameState queryGameRoomState(@RequestBody User user, @RequestParam(name="roomId") int roomId) {
        return TempDatabase.getRoom(roomId).getState();
    }
}
