package com.javarush.vrubleuski.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "film_text")
public class FilmText {

    @Id
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

}
