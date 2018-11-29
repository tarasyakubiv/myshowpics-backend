package com.tarasyakubiv.myshowpics.repository;

import java.util.Optional;

import com.tarasyakubiv.myshowpics.domain.GameShow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameShowRepository extends JpaRepository<GameShow, Integer> {

    Optional<GameShow> findOptionalByName(String name);

}