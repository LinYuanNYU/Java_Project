package com.example.javaproject.controller;

import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameRoomController {
    Logger logger = LoggerFactory.getLogger(GameRoomController.class);

    @PostMapping("/room")
    public boolean joinGameRoom(@RequestBody User user, @RequestParam(name="roomId") int roomId) {
        logger.info(String.format("User: {%d} want to join room: {%d}", user.getId(), roomId));
        if (!TempDatabase.checkUser(user.getId())) {
            logger.error(String.format("Invalid userId: %d", user.getId()));
            return false;
        }
        if (!TempDatabase.hasRoom(roomId)) {
            TempDatabase.createRoom(roomId);
        }
        if (!TempDatabase.checkRoom(roomId)) {
            logger.error(String.format("Invalid roomId: %d", roomId));
            return false;
        }
        return TempDatabase.userJoinRoom(user.getId(), roomId);
    }
}
