package com.lib.repository;

import com.lib.domain.ImageFile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile,String> {


    @EntityGraph(attributePaths = "id")
    Optional<ImageFile> findImageById(String imageId);

    @EntityGraph(attributePaths = "id")
    List<ImageFile> findAll();



}
