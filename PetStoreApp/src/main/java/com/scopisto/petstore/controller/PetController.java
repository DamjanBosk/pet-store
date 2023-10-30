package com.scopisto.petstore.controller;

import com.scopisto.petstore.model.Pet;
import com.scopisto.petstore.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "api/pets")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getPets() {
        return this.petService.getPets()
                .map(pets -> ResponseEntity.ok().body(pets))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<List<Pet>> createPets() {
        return petService.generateRandomPets()
                .map(pets -> ResponseEntity.ok().body(pets))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
