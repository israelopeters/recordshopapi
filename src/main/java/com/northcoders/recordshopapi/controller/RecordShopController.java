package com.northcoders.recordshopapi.controller;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.service.RecordShopService;
import com.northcoders.recordshopapi.service.RecordShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
