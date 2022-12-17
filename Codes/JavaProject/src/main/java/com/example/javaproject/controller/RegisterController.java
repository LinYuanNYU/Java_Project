package com.example.javaproject.controller;

import com.example.javaproject.infra.TempDatabase;
import com.example.javaproject.infra.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RegisterController {
    Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @GetMapping("/register")
    public User register(@RequestBody User user) {
        logger.info("User id: " + String.valueOf(user.getId()));
        TempDatabase.registerUser(user.getId());
        return user;
    }
}
