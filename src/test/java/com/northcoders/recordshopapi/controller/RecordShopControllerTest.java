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
import com.fasterxml.jackson.datatype.jsr310.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
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
    @DisplayName("GET / returns a list of all albums")
    void getAllAlbums() throws Exception {
        //Arrange
        List<Album> albums = List.of(
                new Album(1L, "Album1", "AlbumName1", ROCK, LocalDate.of(2001, 1, 1), 10, "Good Album1", 5),
                new Album(2L, "Album2", "AlbumName2", JAZZ, LocalDate.of(2002, 2, 2), 20, "Fine Album2", 7),
                new Album(3L, "Album3", "AlbumName3", JAZZ, LocalDate.of(2003, 3, 3), 30, "Great Album3", 9)
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
    void getAlbumById() throws Exception {
        //Arrange
        Album albumOne = new Album(1L, "Album1", "Artiste1", ROCK, LocalDate.of(2001, 1, 1), 10, "Good Album1", 5);
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
        Album album = new Album(1L, "Album1", "Artiste1", ROCK, null, 10, "Good Album1", 5);
        when(recordShopServiceImpl.addAlbum(album)).thenReturn(album);

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(album))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.artiste").value("Artiste1"));

        verify(recordShopServiceImpl, times(1)).addAlbum(album);

    }

    @Test
    @DisplayName("PUT returns the updated album and the ACCEPTED status code")
    public void updateAlbum () throws Exception {
        //Arrange
        Album updatedAlbum = new Album(2L, "Album2", "ArtisteName2", JAZZ, null, 20, "Fine Album2", 6);

        when(recordShopServiceImpl.updateAlbum(2L, updatedAlbum)).thenReturn(updatedAlbum);

        //Act and Assert
        this.mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/albums/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedAlbum))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(6));

        verify(recordShopServiceImpl, times(1)).updateAlbum(2L, updatedAlbum);
    }
}