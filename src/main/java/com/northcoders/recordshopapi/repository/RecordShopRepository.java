package com.northcoders.recordshopapi.repository;

import com.northcoders.recordshopapi.model.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface RecordShopRepository extends CrudRepository<Album, Long> {
    @Query("SELECT A FROM Album A where A.artiste = ?1")
    List<Album> findByArtiste(String artiste);

    @Query("SELECT G FROM Album G where G.genre = ?1")
    List<Album> findByGenre(Album.Genre genre);

    @Query("SELECT Y FROM Album Y where Y.year = ?1")
    List<Album> findByYear(Year year);

    Album findByName(String name);
}
