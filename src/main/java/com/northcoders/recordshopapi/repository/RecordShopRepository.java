package com.northcoders.recordshopapi.repository;

import com.northcoders.recordshopapi.model.Album;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordShopRepository extends CrudRepository<Album, Long> {
    @Query("SELECT A FROM Album A where A.artiste = ?1")
    List<Album> findByArtiste(String artiste);

}
