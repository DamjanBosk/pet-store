package com.scopisto.petstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "first_name")
    private String firstName;


    @Getter
    @Setter
    @Column(name = "last_name")
    private String lastName;


    @Getter
    @Setter
    @Column(name = "email_address")
    private String emailAddress;


    @Getter
    @Setter
    private BigDecimal budget;

    public User() {
    }

    public User(String firstName, String lastName, String emailAddress, BigDecimal budget) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", budget=" + budget +
                '}';
    }
}
