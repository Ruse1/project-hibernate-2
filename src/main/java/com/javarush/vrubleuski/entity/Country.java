package com.javarush.vrubleuski.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "country_id")
    private Short id;

    @Column(name = "country", length = 50, nullable = false)
    private String country;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
