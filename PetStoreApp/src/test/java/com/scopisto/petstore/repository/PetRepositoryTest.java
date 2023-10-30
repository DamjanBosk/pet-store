package com.scopisto.petstore.repository;

import com.scopisto.petstore.model.Pet;
import com.scopisto.petstore.model.PetType;
import com.scopisto.petstore.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PetRepositoryTest {

    @Autowired
    private PetRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetAllByOwnerIsNullWithNullOwner() {
        // given
        Pet pet = new Pet(null, "name", PetType.CAT, "desciption", LocalDate.now(), 0);
        underTest.save(pet);

        // when
        List<Pet> expected = underTest.getAllByOwnerIsNull();

        // then
        assertThat(expected.size()).isGreaterThan(0);
    }

    @Test
    void testGetAllByOwnerIsNullWithNonNullOwner() {
        // given
        User owner = new User("firstName", "lastName", "user@example.com", BigDecimal.valueOf(100.00));
        userRepository.save(owner);
        Pet pet = new Pet(owner, "name", PetType.CAT, "desciption", LocalDate.now(), 0);
        underTest.save(pet);

        // when
        List<Pet> expected = underTest.getAllByOwnerIsNull();

        // then
        assertThat(expected.size()).isEqualTo(0);
    }
}