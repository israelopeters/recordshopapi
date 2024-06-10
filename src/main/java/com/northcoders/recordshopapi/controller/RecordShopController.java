package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.service.RecordShopService;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<Album> updateAlbum(@PathVariable("id") long id, @RequestBody int newQuantity) {
        Album albumUpdated = recordShopService.updateAlbum(id, newQuantity);
        return new ResponseEntity<>(albumUpdated, HttpStatus.ACCEPTED);
    }
}
