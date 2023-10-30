package com.scopisto.petstore.service;

import com.github.javafaker.Faker;
import com.scopisto.petstore.model.Pet;
import com.scopisto.petstore.model.PetType;
import com.scopisto.petstore.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Optional<List<Pet>> getPets() {
        return Optional.of(petRepository.findAll());
    }

    public Optional<List<Pet>> generateRandomPets() {
        List<Pet> pets = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            Pet pet = new Pet();

            pet.setName(faker.animal().name());
            pet.setDescription(faker.lorem().fixedString(20));
            pet.setDateOfBirth(this.generateRandomDateOfBirth());
            pet.setType(faker.random().nextBoolean() ? PetType.CAT : PetType.DOG);
            pet.setRating(faker.random().nextInt(0, 10));
            pet.setOwner(null);

            pets.add(pet);
        }
        return Optional.of(petRepository.saveAll(pets));
    }

    private LocalDate generateRandomDateOfBirth() {
        Faker faker = new Faker();
        Date randomBirthdate = faker.date().birthday(0, 30);
        return randomBirthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
