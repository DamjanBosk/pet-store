package com.scopisto.petstore.service;

import com.scopisto.petstore.model.Pet;
import com.scopisto.petstore.model.PetType;
import com.scopisto.petstore.model.User;
import com.scopisto.petstore.repository.PetRepository;
import com.scopisto.petstore.repository.PurchaseLogRepository;
import com.scopisto.petstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PurchaseLogRepository purchaseLogRepository;
    UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, petRepository, purchaseLogRepository);
    }

    @Test
    void getUsers() {
        // when
        underTest.getUsers();

        // then
        verify(userRepository).findAll();
    }

    @Test
    void generateRandomUsers() {
        // given
        List<User> generatedUsers = new ArrayList<>();
        generatedUsers.add(this.createDummyUser());
        when(userRepository.saveAll(Mockito.any())).thenReturn(generatedUsers);

        // when
        Optional<List<User>> result = underTest.generateRandomUsers();

        // then
        assertEquals(generatedUsers, result.orElse(null));
    }

    @Test
    void buyPets() {
        // given
        List<User> users = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        users.add(this.createDummyUser());
        pets.addAll(this.createDummyPets());

        // when
        when(userRepository.findAll()).thenReturn(users);
        when(petRepository.getAllByOwnerIsNull()).thenReturn(pets);
        when(userRepository.saveAll(Mockito.any())).thenReturn(users);
        when(petRepository.saveAll(Mockito.any())).thenReturn(pets);

        BigDecimal initialUserBudget = users.get(0).getBudget();
        BigDecimal pet1Price = pets.get(0).getPrice();
        BigDecimal pet2Price = pets.get(1).getPrice();

        underTest.buyPets();

        // then
        assertEquals(users.get(0), pets.get(0).getOwner());
        assertEquals(users.get(0), pets.get(1).getOwner());
        assertEquals(users.get(0).getBudget(), initialUserBudget.subtract(pet1Price).subtract(pet2Price));
    }

    @Test
    void getPurchaseLogs() {
        //when
        underTest.getPurchaseLogs();

        // then
        verify(purchaseLogRepository).findAll();
    }

    private User createDummyUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmailAddress("john.doe@example.com");
        user.setBudget(BigDecimal.valueOf(40.00));

        return user;
    }

    private List<Pet> createDummyPets() {
        // pet1 is 10 years old and 10 rating, so price is 20.00
        Pet pet1 = new Pet();
        pet1.setName("Rex");
        pet1.setType(PetType.DOG);
        pet1.setOwner(null);
        pet1.setDateOfBirth(LocalDate.now().minusYears(10));
        pet1.setRating(10);

        // pet2 is 10 years old and rating 0, so price is 10.00
        Pet pet2 = new Pet();
        pet2.setName("Whiskers");
        pet2.setType(PetType.CAT);
        pet2.setOwner(null);
        pet2.setDateOfBirth(LocalDate.now().minusYears(10));

        List<Pet> pets = new ArrayList<>();
        pets.add(pet1);
        pets.add(pet2);
        return pets;
    }
}