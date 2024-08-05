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
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id", nullable = false)
    private Byte id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
