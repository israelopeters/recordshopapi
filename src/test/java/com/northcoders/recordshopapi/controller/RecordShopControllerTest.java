package com.northcoders.recordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import com.northcoders.recordshopapi.service.RecordShopService;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.Year;
import java.util.List;
import java.util.Optional;

import static com.northcoders.recordshopapi.model.Album.Genre.JAZZ;
import static com.northcoders.recordshopapi.model.Album.Genre.ROCK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class RecordShopControllerTest {

    @Mock
    private RecordShopServiceImpl recordShopServiceImpl;

    @InjectMocks
    private RecordShopController recordShopController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(recordShopController).build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET / returns a list of all albums and the OK status code")
    void getAllAlbums() throws Exception {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "AlbumName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "AlbumName2", JAZZ, Year.of(2002), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "AlbumName3", JAZZ, Year.of(2003), 30, "Great Album3", 9)
        );
        when(recordShopServiceImpl.getAllAlbums()).thenReturn(albums);

        //Act and Assert
        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/albums/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Album2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].genre").value("JAZZ"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(5));
    }

    @Test
    @DisplayName("GET /{id} returns the album with given id and the OK status code")
    void getAlbumById() throws Exception {
        //Arrange
        Album albumOne = new Album(1L, "Album1", "Artiste1", ROCK, Year.of(2001), 10, "Good Album1", 5);
        when(recordShopServiceImpl.getAlbumById(1L)).thenReturn(Optional.of(albumOne));

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.artiste").value("Artiste1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Good Album1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("ROCK"));
    }

    @Test
    @DisplayName("POST returns the album and a CREATED status code")
    public void addAlbum() throws Exception {
        //Arrange
        Album album = new Album(1L, "Album1", "Artiste1", ROCK, Year.of(2001), 10, "Good Album1", 5);
        when(recordShopServiceImpl.addAlbum(album)).thenReturn(album);

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/albums/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(album))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.artiste").value("Artiste1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value("2001"));

        verify(recordShopServiceImpl, times(1)).addAlbum(album);

    }

    @Test
    @DisplayName("PUT returns the updated album and the ACCEPTED status code")
    public void updateAlbum () throws Exception {
        //Arrange
        Album originalAlbum = new Album(2L, "Album2", "ArtisteName2", JAZZ, Year.of(2002), 20, "Fine Album2", 7);
        Album updatedAlbum = new Album(2L, "Album2", "ArtisteName2", JAZZ, Year.of(2002), 20, "Fine Album2", 6);

        when(recordShopServiceImpl.updateAlbum(2L, 6)).thenReturn(updatedAlbum);

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/albums/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(6))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(6));

        verify(recordShopServiceImpl, times(1)).updateAlbum(2L, 6);
    }

    @Test
    @DisplayName("DEL returns the OK status code.")
    public void deleteAlbumById() throws Exception {
        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/albums/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("getAlbumByArtiste returns a list of albums with the given artiste name and the OK status code")
    void getAlbumByArtiste() throws Exception {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtisteName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtisteName2", JAZZ, Year.of(2002), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtisteName2", JAZZ, Year.of(2003), 30, "Great Album3", 9)
        );
        when(recordShopServiceImpl.getAlbumsByArtiste("ArtisteName2")).thenReturn(albums.subList(1,3));

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/artiste?artiste=ArtisteName2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artiste").value("ArtisteName2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artiste").value("ArtisteName2"));
    }

    @Test
    @DisplayName("getAlbumByGenre returns a list of albums with the given genre and the OK status code")
    void getAlbumByGenre() throws Exception {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtisteName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtisteName2", JAZZ, Year.of(2002), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtisteName2", JAZZ, Year.of(2003), 30, "Great Album3", 9)
        );
        when(recordShopServiceImpl.getAlbumsByGenre(Album.Genre.JAZZ)).thenReturn(albums.subList(1, 3));

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/genre?genre=JAZZ"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("JAZZ"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value("JAZZ"));
    }

    @Test
    @DisplayName("getAlbumByYear returns a list of albums with the given year and the OK status code")
    void getAlbumByYear() throws Exception {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "ArtisteName1", ROCK, Year.of(2001), 10, "Good Album1", 5),
                new Album(2L, "Album2", "ArtisteName2", JAZZ, Year.of(2001), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "ArtisteName2", JAZZ, Year.of(2003), 30, "Great Album3", 9)
        );
        when(recordShopServiceImpl.getAlbumsByYear(Year.of(2001))).thenReturn(albums.subList(0, 2));

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/albums/year?year=2001"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value("2001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].year").value("2001"));
    }
}