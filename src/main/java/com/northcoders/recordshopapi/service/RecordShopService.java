package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface RecordShopService {
    List<Album> getAllAlbums();
    Optional<Album> getAlbumById(Long id);
    Album addAlbum(Album album);
    Album updateAlbum(Long id, Album updatedAlbum);
    void deleteAlbumById(Long id);
    List<Album> getAlbumsByArtist(String artist);
    List<Album> getAlbumsByGenre(Album.Genre genre);
    List<Album> getAlbumsByYear(Year year);
    String getAlbumInfoByName(String name);

}
