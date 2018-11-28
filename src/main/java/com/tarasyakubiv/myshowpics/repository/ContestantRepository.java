package com.tarasyakubiv.myshowpics.repository;

import java.util.List;
import java.util.Optional;

import com.tarasyakubiv.myshowpics.domain.Contestant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestantRepository extends JpaRepository<Contestant, Integer> {

    Optional<Contestant> findOptionalByName(String name);

    List<Contestant> findByNameIn(List<String> names);
}