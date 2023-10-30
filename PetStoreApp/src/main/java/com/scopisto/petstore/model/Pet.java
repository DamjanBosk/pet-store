package com.scopisto.petstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "pets")
public class Pet {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private PetType type;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Transient
    private BigDecimal price;

    @Getter
    private int rating;

    public Pet() {
    }

    public Pet(User owner, String name, PetType type, String description, LocalDate dateOfBirth, int rating) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.setRating(rating);
    }

    public BigDecimal getPrice() {
        if (this.type == PetType.CAT)
            return this.calculateCatPrice();
        else
            return this.calculateDogPrice();
    }

    public void setRating(int rating) {
        this.rating = (type == PetType.DOG && rating >= 0 && rating <= 10) ? rating : 0;
    }

    private BigDecimal calculateCatPrice() {
        return BigDecimal.valueOf(this.calculateAge());
    }

    private BigDecimal calculateDogPrice() {
        return BigDecimal.valueOf(this.calculateAge())
                .add(BigDecimal.valueOf(rating));
    }

    private int calculateAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }
}
