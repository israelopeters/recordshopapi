package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.service.RecordShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/albums")
public class RecordShopController {

    @Autowired
    RecordShopService recordShopService;

    @Tag(name = "get", description = "All GET methods")
    @Operation(summary = "Get all albums", description = "Get all saved albums")
    @ApiResponse(responseCode = "200",
            description = "All albums found",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Album.class)))
    )
    @GetMapping("/")
    public ResponseEntity<List<Album>> getAllAlbums() {
        return new ResponseEntity<>(recordShopService.getAllAlbums(), HttpStatus.OK);
    }

    @Tag(name = "get", description = "All GET methods")
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
    public ResponseEntity<Album> getAlbumById(
            @Parameter(description = "ID of album to retrieve", required = true) @PathVariable long id) {
        Album album = recordShopService.getAlbumById(id).get();
        return new ResponseEntity<>(album, HttpStatus.OK);
    }


    @Tag(name = "add", description = "All ADD methods")
    @Operation(summary = "Add album", description = "Add a new album")
    @ApiResponse(responseCode = "200",
            description = "Album added",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Album.class))}
    )
    @PostMapping("/add")
    public ResponseEntity<Album> addAlbum(
            @Parameter(description = "Album to add to shop", required = true) @RequestBody Album album) {
        Album albumToAdd = recordShopService.addAlbum(album);
        return new ResponseEntity<>(albumToAdd, HttpStatus.CREATED);
    }

    @Tag(name = "update", description = "All UPDATE methods")
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
    public ResponseEntity<Album> updateAlbum(
            @Parameter(description = "ID of album to update", required = true) @PathVariable long id,
            @Parameter(description = "Updated version of existing album", required = true) @RequestBody Album updatedAlbum) {
        Album albumUpdated = recordShopService.updateAlbum(id, updatedAlbum);
        return new ResponseEntity<>(albumUpdated, HttpStatus.ACCEPTED);
    }

    @Tag(name = "delete", description = "All DELETE methods")
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
    public void deleteAlbumById(
            @Parameter(description = "ID of album to delete", required = true) @PathVariable long id) {
        recordShopService.deleteAlbumById(id);
    }

    @Tag(name = "get", description = "All GET methods")
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
    public ResponseEntity<List<Album>> getAlbumsByArtist(
            @Parameter(description = "Name of artist to filter albums with", required = true) @RequestParam String artist) {
        return new ResponseEntity<>(recordShopService.getAlbumsByArtist(artist), HttpStatus.OK);
    }

    @Tag(name = "get", description = "All GET methods")
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
    public ResponseEntity<List<Album>> getAlbumsByGenre(
            @Parameter(description = "Genre to filter albums with", required = true) @RequestParam Album.Genre genre) {
        return new ResponseEntity<>(recordShopService.getAlbumsByGenre(genre), HttpStatus.OK);
    }

    @Tag(name = "get", description = "All GET methods")
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
    public ResponseEntity<List<Album>> getAlbumsByGenre(
            @Parameter(description = "Year to filter albums with", required = true) @RequestParam Year year) {
        return new ResponseEntity<>(recordShopService.getAlbumsByYear(year), HttpStatus.OK);
    }

    @Tag(name = "get", description = "All GET methods")
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
    public ResponseEntity<String> getAlbumInfoByName(
            @Parameter(description = "Name of album whose info is to be retrieved", required = true) @RequestParam String name) {
        return new ResponseEntity<>(recordShopService.getAlbumInfoByName(name), HttpStatus.OK);
    }

}
