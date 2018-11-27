package com.tarasyakubiv.myshowpics.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"images", "contestants"})
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class GameShow {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Integer id;

    @Column
    @NonNull
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "gameshow_contestant", 
      joinColumns = @JoinColumn(name = "gameshow_id", 
                                referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "contestant_id", 
                                      referencedColumnName = "id"))
	Set<Contestant> contestants = new HashSet<>();

    @OneToMany(mappedBy = "gameShow", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Image> images = new HashSet<>();


}