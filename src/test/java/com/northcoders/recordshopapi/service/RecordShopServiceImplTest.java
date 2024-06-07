package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static com.northcoders.recordshopapi.model.Album.Genre.JAZZ;
import static com.northcoders.recordshopapi.model.Album.Genre.ROCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class RecordShopServiceImplTest {

    @Mock
    private RecordShopRepository repository;

    @InjectMocks
    private RecordShopServiceImpl recordShopServiceImpl;

    @Test
    @DisplayName("getAllAlbums() returns list of albums")
    public void getAllAlbumsTest() {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "AlbumName1", ROCK, LocalDate.of(2001, 1, 1), 10, "Good Album1", 5),
                new Album(2L, "Album2", "AlbumName2", JAZZ, LocalDate.of(2002, 2, 2), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "AlbumName3", JAZZ, LocalDate.of(2003, 3, 3), 30, "Great Album3", 9)
        );
        when(repository.findAll()).thenReturn(albums);

        //Act
        List<Album> actual = recordShopServiceImpl.getAllAlbums();

        //Assert
        assertThat(actual).hasSize(3);
        assertThat(actual).isEqualTo(albums);
    }
}