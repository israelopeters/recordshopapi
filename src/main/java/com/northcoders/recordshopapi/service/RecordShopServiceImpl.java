package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.exception.AlbumNotFoundException;
import com.northcoders.recordshopapi.model.Album;
import com.northcoders.recordshopapi.repository.RecordShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.Year;
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
    @Cacheable(value = "album", key = "#id")
    public Optional<Album> getAlbumById(Long id) {
        System.out.println("Retrieving album...");
        Optional<Album> album = repository.findById(id);
        if (album.isPresent()) {
            return album;
        } else {
            throw new AlbumNotFoundException(String.format("Cannot find Album with id '%d'", id));
        }
    }

    @Override
    public Album addAlbum(Album album) {
        return repository.save(album);
    }

    @Override
    @CachePut(value = "album", key = "#id")
    public Album updateAlbum(Long id, Album updatedAlbum) {
        Optional<Album> album = repository.findById(id);
        if (album.isPresent()) {
            album = album.map(oldAlbum -> {
                oldAlbum.setName(updatedAlbum.getName());
                oldAlbum.setArtist(updatedAlbum.getArtist());
                oldAlbum.setGenre(updatedAlbum.getGenre());
                oldAlbum.setYear(updatedAlbum.getYear());
                oldAlbum.setTracks(updatedAlbum.getTracks());
                oldAlbum.setDescription(updatedAlbum.getDescription());
                oldAlbum.setQuantity(updatedAlbum.getQuantity());
                return repository.save(oldAlbum);}
            );
        } else {
            throw new AlbumNotFoundException(String.format("Cannot find Album with id '%d'", id));
        }
        return album.get();
    }

    @Override
    public void deleteAlbumById(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new AlbumNotFoundException(String.format("Cannot find Album with id '%d'", id));
        }
    }

    @Override
    public List<Album> getAlbumsByArtist(String artist) {
        if (repository.findByArtist(artist).isEmpty()) {
            throw new AlbumNotFoundException(String.format("Cannot find any album with artist name '%s'", artist));
        }
        return repository.findByArtist(artist);
    }

    @Override
    public List<Album> getAlbumsByGenre(Album.Genre genre) {
        if (repository.findByGenre(genre).isEmpty()) {
            throw new AlbumNotFoundException(String.format("Cannot find any album belonging to the genre '%s'", genre));
        } else {
            return repository.findByGenre(genre);
        }
    }

    @Override
    public List<Album> getAlbumsByYear(Year year) {
        if (repository.findByYear(year).isEmpty()) {
            throw new AlbumNotFoundException(String.format("Cannot find any albums with release year '%s'", year));
        } else {
            return repository.findByYear(year);
        }
    }

    @Override
    public String getAlbumInfoByName(String name) {
        if (repository.findByName(name) == null) {
            throw new AlbumNotFoundException(String.format("Cannot find any album with name '%s'", name));
        } else {
            return repository.findByName(name).toString();
        }
    }
}
