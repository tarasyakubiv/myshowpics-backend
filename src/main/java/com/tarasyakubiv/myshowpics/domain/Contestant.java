package com.tarasyakubiv.myshowpics.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"images"})
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Contestant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NonNull
    private String name;

    @ManyToMany(mappedBy = "contestants", cascade = {CascadeType.PERSIST})
    private Set<Image> images = new HashSet<>();

    @ManyToMany(mappedBy = "contestants")
    private Set<GameShow> gameShows = new HashSet<>();

}