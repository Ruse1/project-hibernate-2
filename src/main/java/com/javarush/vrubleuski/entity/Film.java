package com.javarush.vrubleuski.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "film", schema = "project_hibernate_2")
public class Film {
    @Id()
    @Column(name = "film_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "rental_duration", nullable = false)
    private Byte rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Short length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal replacementCost;

    @Column(name = "rating")
    private String rating;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "film", joinColumns = @JoinColumn(name = "film_id"))
    @Column(name = "special_features")
    private Set<String> specialFeatures;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private FilmText filmText;

    @ManyToMany
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

}
