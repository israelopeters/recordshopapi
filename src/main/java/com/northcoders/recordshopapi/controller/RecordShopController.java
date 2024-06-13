package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.service.RecordShopService;
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

    @GetMapping("/")
    public ResponseEntity<List<Album>> getAllAlbums() {
        return new ResponseEntity<>(recordShopService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable long id) {
        return new ResponseEntity<>(recordShopService.getAlbumById(id).get(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Album> addAlbum(@RequestBody Album album) {
        Album albumToAdd = recordShopService.addAlbum(album);
        return new ResponseEntity<>(albumToAdd, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable long id, @RequestBody int newQuantity) {
        Album albumUpdated = recordShopService.updateAlbum(id, newQuantity);
        return new ResponseEntity<>(albumUpdated, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAlbumById(@PathVariable long id) {
        recordShopService.deleteAlbumById(id);
    }

    @GetMapping("/artiste")
    public ResponseEntity<List<Album>> getAlbumsByArtiste(@RequestParam String artiste) {
        return new ResponseEntity<>(recordShopService.getAlbumsByArtiste(artiste), HttpStatus.OK);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Album>> getAlbumsByGenre(@RequestParam Album.Genre genre) {
        return new ResponseEntity<>(recordShopService.getAlbumsByGenre(genre), HttpStatus.OK);
    }

    @GetMapping("/year")
    public ResponseEntity<List<Album>> getAlbumsByGenre(@RequestParam Year year) {
        return new ResponseEntity<>(recordShopService.getAlbumsByYear(year), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<String> getAlbumInfoByName(String name) {
        return new ResponseEntity<>(recordShopService.getAlbumInfoByName(name), HttpStatus.OK);
    }

}
