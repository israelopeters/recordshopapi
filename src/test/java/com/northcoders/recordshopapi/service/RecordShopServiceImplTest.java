package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.exception.AlbumNotFoundException;
import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;
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
                new Album(1L, "Album1", "AlbumName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "AlbumName2", JAZZ, Year.of(2002), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "AlbumName3", JAZZ, Year.of(2003), 30, "Great Album3", 9)
        );
        when(repository.findAll()).thenReturn(albums);

        //Act
        List<Album> actual = recordShopServiceImpl.getAllAlbums();

        //Assert
        assertThat(actual).hasSize(3);
        assertThat(actual).isEqualTo(albums);
    }

    @Test
    @DisplayName("getAlbumById() returns album of id")
    public void getAlbumByIdTest() {
        //Arrange
        Album expectedOne = new Album(1L, "Album1", "AlbumName1", ROCK, Year.of(2001), 10, "Good Album1", 5);

        when(repository.findById(1L)).thenReturn(Optional.of(expectedOne));
        when(repository.findById(3L)).thenThrow(AlbumNotFoundException.class);

        //Act
        Album actualOne = recordShopServiceImpl.getAlbumById(1L).get();

        //Assert
        assertThat(actualOne).isEqualTo(expectedOne);
        assertThrows(AlbumNotFoundException.class, () -> recordShopServiceImpl.getAlbumById(3L));
    }

    @Test
    @DisplayName("addAlbum() returns the album")
    public void addAlbum() {
        //Arrange
        Album album = new Album(2L, "Album2", "ArtistName2", JAZZ, Year.of(2002), 20, "Fine Album2", 7);
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
        Album originalAlbum = new Album(2L, "Album2", "ArtistName2", JAZZ, Year.of(2002), 20, "Fine Album2", 7);
        Album updatedAlbum = new Album(2L, "Album2", "ArtistName2", JAZZ, Year.of(2002), 20, "Fine Album2", 6);

        //Set the behavior of the two method calls within the mocked object
        when(repository.findById(2L)).thenReturn(Optional.of(originalAlbum));
        when(repository.save(originalAlbum)).thenReturn(updatedAlbum);

        //Act
        Album actual = recordShopServiceImpl.updateAlbum(2L, updatedAlbum);

        //Assert
        assertThat(actual).isEqualTo(updatedAlbum);

    }

    @Test
    @DisplayName("getAlbumsByArtist() returns list of albums with the given artist")
    public void getAlbumsByArtist() {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtistName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtistName2", JAZZ, Year.of(2002), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtistName2", JAZZ, Year.of(2003), 30, "Great Album3", 9)
        );
        when(repository.findByArtist("ArtistName2")).thenReturn(albums.subList(1,3));

        //Act
        List<Album> actual = recordShopServiceImpl.getAlbumsByArtist("ArtistName2");

        //Assert
        assertThat(actual).hasSize(2);
        assertThat(actual).isEqualTo(albums.subList(1,3));
    }

    @Test
    @DisplayName("getAlbumsByGenre() returns list of albums with the given genre")
    public void getAlbumsByGenre() {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtistName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtistName2", COUNTRY, Year.of(2002), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtistName2", ROCK, Year.of(2003), 30, "Great Album3", 9)
        );
        when(repository.findByGenre(ROCK)).thenReturn(List.of(albums.get(0), albums.get(2)));

        //Act
        List<Album> actual = recordShopServiceImpl.getAlbumsByGenre(ROCK);

        //Assert
        assertThat(actual).hasSize(2);
        assertThat(actual).isEqualTo(List.of(albums.get(0), albums.get(2)));
    }


    @Test
    @DisplayName("getAlbumsByYear() returns list of albums with the given year")
    public void getAlbumsByYear() {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtistName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtistName2", COUNTRY, Year.of(2002), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtistName3", ROCK, Year.of(2002), 30, "Great Album3", 9)
        );
        when(repository.findByYear(Year.of(2002))).thenReturn(albums.subList(1,3));

        //Act
        List<Album> actual = recordShopServiceImpl.getAlbumsByYear(Year.of(2002));

        //Assert
        assertThat(actual).hasSize(2);
        assertThat(actual).isEqualTo(List.of(albums.get(1), albums.get(2)));
    }

    @Test
    @DisplayName("getAlbumInfoByName() returns the album info with the given name")
    public void getAlbumInfoByName() {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtistName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtistName2", COUNTRY, Year.of(2002), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtistName3", ROCK, Year.of(2002), 30, "Great Album3", 9)
        );
        when(repository.findByName("Album2")).thenReturn(albums.get(1));

        //Act
        String actual = recordShopServiceImpl.getAlbumInfoByName("Album2");

        //Assert
        assertThat(actual).isEqualTo(albums.get(1).toString());
    }

}