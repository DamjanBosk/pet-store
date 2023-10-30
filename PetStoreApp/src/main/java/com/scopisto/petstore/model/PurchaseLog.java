package com.scopisto.petstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_log")
public class PurchaseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name = "date_of_purchase")
    private LocalDateTime dateOfPurchase;

    @Getter
    @Setter
    @Column(name = "number_of_successful_purchases")
    private int numberOfSuccessfulBuys;

    @Getter
    @Setter
    @Column(name = "number_of_unsuccessful_purchases")
    private int numberOfUnsuccessfulBuys;

    public PurchaseLog() {
        this.dateOfPurchase = LocalDateTime.now();
    }

    public PurchaseLog(int numberOfSuccessfulBuys, int numberOfUnsuccessfulBuys) {
        this.dateOfPurchase = LocalDateTime.now();
        this.numberOfSuccessfulBuys = numberOfSuccessfulBuys;
        this.numberOfUnsuccessfulBuys = numberOfUnsuccessfulBuys;
    }

    public void incrementSuccessfulBuys() {
        this.numberOfSuccessfulBuys += 1;
    }

    public void incrementUnsuccessfulBuys() {
        this.numberOfUnsuccessfulBuys += 1;
    }

    @Override
    public String toString() {
        return "PurchaseLog{" +
                "id=" + id +
                ", dateOfPurchase=" + dateOfPurchase +
                ", numberOfSuccessfulBuys=" + numberOfSuccessfulBuys +
                ", numberOfUnsuccessfulBuys=" + numberOfUnsuccessfulBuys +
                '}';
    }
}
