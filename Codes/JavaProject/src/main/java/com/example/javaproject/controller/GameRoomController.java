package com.example.javaproject.controller;

import com.example.javaproject.messages.GameStateMessage;
import com.example.javaproject.messages.JoinRoomRequest;
import com.example.javaproject.messages.RoomStateMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@MessageMapping("/room")
public class GameRoomController {
    Logger logger = LoggerFactory.getLogger(GameRoomController.class);


    @PostMapping("/create")
    public int createGameRoom(@RequestBody User user) {
        logger.info(String.format("User: {%d} creates a game room", user.getId()));
        GameRoom room = new GameRoom(user.getId());
        room.addUser(user.getId());
        TempDatabase.addRoom(room);
        return room.getRoomId();
    }

    @MessageMapping("/join")
    @SendTo("/topic/game_room")
    public RoomStateMessage joinGameRoom(@RequestBody JoinRoomRequest request) {
        logger.info(String.format("User: {%d} want to join room: {%d}", request.userId, request.roomId));
        /*if (!TempDatabase.checkUser(request.userId)) {
            logger.error(String.format("Invalid userId: %d", request.userId));
            return false;
        }*/
        if (TempDatabase.getRoom(request.roomId) == null) {
            createGameRoom(new User(request.userId));
        }
        if (!TempDatabase.checkRoom(request.roomId)) {
            String res = String.format("Invalid roomId: %d", request.roomId);
            logger.error(res);
            return null;
        }
        TempDatabase.userJoinRoom(request.userId, request.roomId);
        return new RoomStateMessage(request.roomId);
    }
    @MessageMapping("/start")
    @SendTo("/topic/game_room")
    public GameStateMessage gameStart(@RequestBody User user) {
        return null;
    }
}