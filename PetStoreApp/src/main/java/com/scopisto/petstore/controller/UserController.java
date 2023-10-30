package com.scopisto.petstore.controller;

import com.scopisto.petstore.model.Pet;
import com.scopisto.petstore.model.PurchaseLog;
import com.scopisto.petstore.model.User;
import com.scopisto.petstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return this.userService.getUsers()
                .map(users -> ResponseEntity.ok().body(users))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<List<User>> createUsers() {
        return this.userService.generateRandomUsers()
                .map(users -> ResponseEntity.ok().body(users))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/buy")
    public ResponseEntity<List<Pet>> buyPets() {
        return this.userService.buyPets()
                .map(pets -> ResponseEntity.ok().body(pets))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/purchase-log")
    public ResponseEntity<List<PurchaseLog>> getPurchaseLogs() {
        return this.userService.getPurchaseLogs()
                .map(logs -> ResponseEntity.ok().body(logs))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
