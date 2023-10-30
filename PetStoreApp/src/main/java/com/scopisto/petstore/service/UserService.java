package com.scopisto.petstore.service;

import com.github.javafaker.Faker;
import com.scopisto.petstore.model.Pet;
import com.scopisto.petstore.model.PetType;
import com.scopisto.petstore.model.PurchaseLog;
import com.scopisto.petstore.model.User;
import com.scopisto.petstore.repository.PetRepository;
import com.scopisto.petstore.repository.PurchaseLogRepository;
import com.scopisto.petstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PurchaseLogRepository purchaseLogRepository;

    @Autowired
    public UserService(UserRepository userRepository, PetRepository petRepository, PurchaseLogRepository purchaseLogRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.purchaseLogRepository = purchaseLogRepository;
    }

    public Optional<List<User>> getUsers() {
        return Optional.of(this.userRepository.findAll());
    }

    public Optional<List<User>> generateRandomUsers() {
        List<User> users = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmailAddress(generateEmailAddress(user.getFirstName(), user.getLastName()));
            user.setBudget(this.generateRandomBudget());

            users.add(user);
        }

        return Optional.of(this.userRepository.saveAll(users));
    }

    public Optional<List<Pet>> buyPets() {
        List<Pet> pets = petRepository.getAllByOwnerIsNull();
        List<User> users = userRepository.findAll();
        PurchaseLog purchaseLog = new PurchaseLog();

        for (User user : users) {
            boolean didBuyPet = false;
            for (Pet pet : pets) {
                if (!this.canUserBuyPet(user, pet)) continue;
                pet.setOwner(user);

                BigDecimal initialBudget = user.getBudget();
                BigDecimal price = pet.getPrice();
                user.setBudget(initialBudget.subtract(price));

                this.printSuccessfulPurchase(pet);
                didBuyPet = true;
            }
            if (didBuyPet) purchaseLog.incrementSuccessfulBuys();
            else purchaseLog.incrementUnsuccessfulBuys();
        }
        this.purchaseLogRepository.save(purchaseLog);
        this.userRepository.saveAll(users);
        return Optional.of(this.petRepository.saveAll(pets));
    }

    public Optional<List<PurchaseLog>> getPurchaseLogs() {
        return Optional.of(this.purchaseLogRepository.findAll());
    }

    private BigDecimal generateRandomBudget() {
        Random random = new Random();
        double minValue = 0.0;
        double maxValue = 50.0;

        double randomValue = minValue + (maxValue - minValue) * random.nextDouble();

        return new BigDecimal(randomValue).setScale(2, RoundingMode.HALF_EVEN);
    }

    private String generateEmailAddress(String firstName, String lastName) {
        return firstName.toLowerCase(Locale.ROOT) + "."
                + lastName.toLowerCase(Locale.ROOT) + "@"
                + new Faker().internet().domainName();
    }

    private boolean canUserBuyPet(User user, Pet pet) {
        BigDecimal userBudget = user.getBudget();
        BigDecimal petPrice = pet.getPrice();
        return (pet.getOwner() == null && userBudget.compareTo(petPrice) >= 0);
    }

    private void printSuccessfulPurchase(Pet pet) {
        if (pet.getType() == PetType.DOG) {
            System.out.println("Woof, dog " + pet.getName() + " has owner " + pet.getOwner().getFirstName());
        } else {
            System.out.println("Meow, cat " + pet.getName() + " has owner " + pet.getOwner().getFirstName());
        }
    }
}
