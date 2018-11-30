package com.tarasyakubiv.myshowpics.repository;

import java.util.Optional;

import com.tarasyakubiv.myshowpics.domain.Image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    Optional<Image> findOptionalByImage(String image);

}