package com.javarush.vrubleuski.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

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

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
