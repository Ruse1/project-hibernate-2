package com.javarush.vrubleuski.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rental_id")
    private Integer id;

    @CreationTimestamp
    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventoryRental;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerRental;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staffRental;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
