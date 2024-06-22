package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.service.RecordShopService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@Tag(name = "API controller")
@RequestMapping("/api/v1/albums")
public class RecordShopController {

    @Autowired
    RecordShopService recordShopService;

    @Operation(summary = "Get all albums", description = "Get all saved albums")
    @ApiResponse(responseCode = "200",
            description = "All albums found",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Album.class)))
    )
    @GetMapping("/")
    public ResponseEntity<List<Album>> getAllAlbums() {
        return new ResponseEntity<>(recordShopService.getAllAlbums(), HttpStatus.OK);
    }

    @Operation(summary = "Get album by id", description = "Get an album by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Album found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Album.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Album not found",
                    content = @Content)}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable long id) {
        Album album = recordShopService.getAlbumById(id).get();
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @Operation(summary = "Add album", description = "Add a new album")
    @ApiResponse(responseCode = "200",
            description = "Album added",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Album.class))}
    )
    @PostMapping("/add")
    public ResponseEntity<Album> addAlbum(@RequestBody Album album) {
        Album albumToAdd = recordShopService.addAlbum(album);
        return new ResponseEntity<>(albumToAdd, HttpStatus.CREATED);
    }

    @Operation(summary = "Update album", description = "Update an album by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Album updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Album.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Album not found",
                    content = @Content)}
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable long id, @RequestBody Album updatedAlbum) {
        Album albumUpdated = recordShopService.updateAlbum(id, updatedAlbum);
        return new ResponseEntity<>(albumUpdated, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete album", description = "Delete an album by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Album deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Album.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Album not found",
                    content = @Content)}
    )
    @DeleteMapping("/delete/{id}")
    public void deleteAlbumById(@PathVariable long id) {
        recordShopService.deleteAlbumById(id);
    }

    @Operation(summary = "Get albums by artist", description = "Get all albums by a particular artist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Albums found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Album.class)))),
            @ApiResponse(responseCode = "404",
                    description = "Artist not found",
                    content = @Content)}
    )
    @GetMapping("/artist")
    public ResponseEntity<List<Album>> getAlbumsByArtist(@RequestParam String artist) {
        return new ResponseEntity<>(recordShopService.getAlbumsByArtist(artist), HttpStatus.OK);
    }

    @Operation(summary = "Get albums by genre", description = "Get all albums by a particular genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Albums found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Album.class)))),
            @ApiResponse(responseCode = "404",
                    description = "Genre not found",
                    content = @Content)}
    )
    @GetMapping("/genre")
    public ResponseEntity<List<Album>> getAlbumsByGenre(@RequestParam Album.Genre genre) {
        return new ResponseEntity<>(recordShopService.getAlbumsByGenre(genre), HttpStatus.OK);
    }

    @Operation(summary = "Get albums by year", description = "Get all albums by a release year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Albums found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Album.class)))),
            @ApiResponse(responseCode = "404",
                    description = "Year not found",
                    content = @Content)}
    )
    @GetMapping("/year")
    public ResponseEntity<List<Album>> getAlbumsByGenre(@RequestParam Year year) {
        return new ResponseEntity<>(recordShopService.getAlbumsByYear(year), HttpStatus.OK);
    }

    @Operation(summary = "Get album info", description = "Get the info of an album by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Album found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Album.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Album name not found",
                    content = @Content)}
    )
    @GetMapping("/name")
    public ResponseEntity<String> getAlbumInfoByName(String name) {
        return new ResponseEntity<>(recordShopService.getAlbumInfoByName(name), HttpStatus.OK);
    }

}
