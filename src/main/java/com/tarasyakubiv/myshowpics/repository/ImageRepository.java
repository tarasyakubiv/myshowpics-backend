package com.tarasyakubiv.myshowpics.repository;

import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.GameShow;
import com.tarasyakubiv.myshowpics.domain.Image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    Set<Image> findAllByGameShow(GameShow gameShow);
}