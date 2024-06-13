package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.PrepareTestInstance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.northcoders.recordshopapi.model.Album.Genre.*;
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

    @Test
    @DisplayName("getAllAlbumById() returns album of id")
    public void getAlbumByIdTest() {
        //Arrange
        Album expectedOne = new Album(1L, "Album1", "AlbumName1", ROCK, LocalDate.of(2001, 1, 1), 10, "Good Album1", 5);

        when(repository.findById(1L)).thenReturn(Optional.of(expectedOne));
        when(repository.findById(3L)).thenReturn(Optional.empty());

        //Act
        Album actualOne = recordShopServiceImpl.getAlbumById(1L).get();
        Optional<Album> actualTwo = recordShopServiceImpl.getAlbumById(3L);

        //Assert
        assertThat(actualOne).isEqualTo(expectedOne);
        assertTrue(actualTwo.isEmpty());
    }

    @Test
    @DisplayName("addAlbum() returns the album")
    public void addAlbum() {
        //Arrange
        Album album = new Album(2L, "Album2", "ArtisteName2", JAZZ, LocalDate.of(2002, 2, 2), 20, "Fine Album2", 7);
        when(repository.save(album)).thenReturn(album);

        //Act
        Album actual = recordShopServiceImpl.addAlbum(album);

        //Assert
        assertThat(actual.getId()).isEqualTo(2L);
        assertThat(actual.getName()).isEqualTo("Album2");
        assertThat(actual.getGenre()).isEqualTo(JAZZ);
    }

    @Test
    @DisplayName("updateAlbum method returns an album with a different quantity")
    public void updateAlbum() {
        //Arrange
        Album originalAlbum = new Album(2L, "Album2", "ArtisteName2", JAZZ, LocalDate.of(2002, 2, 2), 20, "Fine Album2", 7);
        Album updatedAlbum = new Album(2L, "Album2", "ArtisteName2", JAZZ, LocalDate.of(2002, 2, 2), 20, "Fine Album2", 6);

        //Set the behavior of the two method calls within the mocked object
        when(repository.findById(2L)).thenReturn(Optional.of(originalAlbum));
        when(repository.save(originalAlbum)).thenReturn(updatedAlbum);

        //Act
        Album actual = recordShopServiceImpl.updateAlbum(2L, 6);

        //Assert
        assertThat(actual).isEqualTo(updatedAlbum);

    }

    @Test
    @DisplayName("getAlbumsByArtiste() returns list of albums with the given artiste")
    public void getAlbumsByArtiste() {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtisteName1", ROCK, LocalDate.of(2001, 1, 1), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtisteName2", JAZZ, LocalDate.of(2002, 2, 2), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtisteName2", JAZZ, LocalDate.of(2003, 3, 3), 30, "Great Album3", 9)
        );
        when(repository.findByArtiste("ArtisteName2")).thenReturn(albums.subList(1,3));

        //Act
        List<Album> actual = recordShopServiceImpl.getAlbumsByArtiste("ArtisteName2");

        //Assert
        assertThat(actual).hasSize(2);
        assertThat(actual).isEqualTo(albums.subList(1,3));
    }

    @Test
    @DisplayName("getAlbumsByGenre() returns list of albums with the given genre")
    public void getAlbumsByGenre() {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtisteName1", ROCK, LocalDate.of(2001, 1, 1), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtisteName2", COUNTRY, LocalDate.of(2002, 2, 2), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtisteName2", ROCK, LocalDate.of(2003, 3, 3), 30, "Great Album3", 9)
        );
        when(repository.findByGenre(ROCK)).thenReturn(List.of(albums.get(0), albums.get(2)));

        //Act
        List<Album> actual = recordShopServiceImpl.getAlbumsByGenre(ROCK);

        //Assert
        assertThat(actual).hasSize(2);
        assertThat(actual).isEqualTo(List.of(albums.get(0), albums.get(2)));
    }

}