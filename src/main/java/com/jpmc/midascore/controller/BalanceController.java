package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {
    private final UserRepository userRepository;

    // Spring Boot will automatically provide the UserRepository here
    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam long userId) {
        // 1. Look up the user in the database
        UserRecord user = userRepository.findById(userId);

        // 2. Return their balance, or 0 if they don't exist
        if (user != null) {
            return new Balance(user.getBalance());
        } else {
            return new Balance(0f);
        }
    }
}