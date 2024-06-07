package com.northcoders.recordshopapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String artiste;

    @Column(nullable = false)
    Genre genre;

    @Column(nullable = false)
    LocalDate year;

    @Column
    int tracks;

    @Column
    String description;

    @Column
    int quantity;

    public enum Genre {
        ROCK,
        CLASSICAL,
        POP,
        AFROBEAT,
        COUNTRY,
        ELECTRONIC,
        HIP_HOP,
        JAZZ,
        DISCO,
        BLUES
    }

}
