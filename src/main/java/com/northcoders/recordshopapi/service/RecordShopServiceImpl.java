package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordShopServiceImpl implements RecordShopService {

    @Autowired
    RecordShopRepository repository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        repository.findAll().forEach(albums::add);
        return albums;
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Album addAlbum(Album album) {
        return repository.save(album);
    }

    @Override
    public Album updateAlbum(Long id, int newQuantity) {
        Optional<Album> updatedAlbum =
                repository.findById(id)
                .map(oldAlbum -> {
                    oldAlbum.setQuantity(newQuantity);
                    return repository.save(oldAlbum);
                });
        return updatedAlbum.orElse(null);
    }

}
