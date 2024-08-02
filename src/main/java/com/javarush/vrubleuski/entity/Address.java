package com.javarush.vrubleuski.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @Column(name = "district", length = 20, nullable = false)
    private String district;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "postal_code", length = 10)
    private String postal_code;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

}
