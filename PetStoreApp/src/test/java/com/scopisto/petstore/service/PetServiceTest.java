package com.scopisto.petstore.service;

import com.scopisto.petstore.model.Pet;
import com.scopisto.petstore.model.PetType;
import com.scopisto.petstore.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;
    private PetService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PetService(petRepository);
    }

    @Test
    void getPets() {
        //when
        underTest.getPets();

        // then
        verify(petRepository).findAll();
    }

    @Test
    void generateRandomPets() {
        // given
        List<Pet> generatedPets = new ArrayList<>();
        generatedPets.add(createDummyPet());
        when(petRepository.saveAll(Mockito.any())).thenReturn(generatedPets);

        // when
        Optional<List<Pet>> result = underTest.generateRandomPets();

        // then
        assertEquals(generatedPets, result.orElse(null));
    }

    private Pet createDummyPet() {
        Pet pet = new Pet();
        pet.setName("Fido");
        pet.setDescription("A friendly pet");
        pet.setDateOfBirth(LocalDate.of(2020, 1, 1));
        pet.setType(PetType.DOG);
        pet.setRating(5);
        pet.setOwner(null);
        return pet;
    }
}