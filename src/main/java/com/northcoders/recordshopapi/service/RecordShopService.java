package com.northcoders.recordshopapi.service;

import com.northcoders.recordshopapi.model.Album;

import java.util.List;
import java.util.Optional;

public interface RecordShopService {
    List<Album> getAllAlbums();
    Optional<Album> getAlbumById(Long id);
    Album addAlbum(Album album);
}
