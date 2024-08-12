package com.javarush.vrubleuski.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "staff", schema = "movie")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Byte id;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Lob
    @Column(name = "picture", columnDefinition = "blob")
    private Byte[] picture;

    @Column(name = "email", length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "active", nullable = false, columnDefinition = "tinyint(1)")
    private Boolean isActive;

    @Column(name = "username", length = 16, nullable = false)
    private String username;

    @Column(name = "password", length = 40)
    private String password;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
