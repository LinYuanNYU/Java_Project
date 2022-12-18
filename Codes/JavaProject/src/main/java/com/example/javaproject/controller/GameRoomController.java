package com.example.javaproject.controller;

import com.example.javaproject.messages.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import com.example.javaproject.infra.GameRoom.GameRoom;
import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@MessageMapping("/room")
public class GameRoomController {
    Logger logger = LoggerFactory.getLogger(GameRoomController.class);


    @PostMapping("/create")
    public int createGameRoom(@RequestBody User user) {
        logger.info(String.format("User: {%s} creates a game room", user.getId()));
        GameRoom room = new GameRoom(user.getId());
        TempDatabase.addRoom(room);
        return room.getRoomId();
    }

    @MessageMapping("/join")
    @SendTo("/topic/game_room")
    public RoomStateMessage joinGameRoom(@RequestBody JoinRoomRequest request) {
        logger.info(String.format("User: {%s} want to join room: {%d}", request.userId, request.roomId));
        /*if (!TempDatabase.checkUser(request.userId)) {
            logger.error(String.format("Invalid userId: %d", request.userId));
            return false;
        }*/
        if (!TempDatabase.checkUser(request.userId)) {
            TempDatabase.registerUser(request.userId, request.money);
        }
        if (TempDatabase.getRoom(request.roomId) == null) {
            createGameRoom(TempDatabase.getUser(request.userId));
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
    @SendTo({"/topic/in_game", "/topic/game_room"})
    public GameStartMessage gameStart(@RequestBody GameStartRequest request) {
        GameRoom room = TempDatabase.getRoom(request.roomId);
        if (room != null) {
            room.start();
        }
        return new GameStartMessage(request.roomId);
    }
    @MessageMapping("/action")
    @SendTo("/topic/in_game")
    public ActionMessage gameAction(@RequestBody ActionMessage action) {
        GameRoom room = TempDatabase.getRoom(action.getRoomId());
        room.action(action);
        return action;
    }
    @MessageMapping("/pull_cards")
    @SendTo("/topic/in_game")
    public CardsResponseMessage pullCards(@RequestBody CardsRequestMessage request) {
        return new CardsResponseMessage(request.roomId);
    }
}
