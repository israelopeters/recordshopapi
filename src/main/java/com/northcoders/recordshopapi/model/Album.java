package com.northcoders.recordshopapi.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Year;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album implements Serializable {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String artist;

    @Column(nullable = false)
    Genre genre;

    @JsonSerialize(using = YearSerializer.class)
    @Column(nullable = false)
    Year year;

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
