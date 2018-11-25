package com.tarasyakubiv.myshowpics.repository;

import java.util.List;
import java.util.Optional;

import com.tarasyakubiv.myshowpics.domain.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findOptionalByName(String name);

    List<Tag> findByNameIn(List<String> names);
    
}