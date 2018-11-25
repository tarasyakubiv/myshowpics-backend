package com.tarasyakubiv.myshowpics.repository;

import com.tarasyakubiv.myshowpics.domain.Image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}