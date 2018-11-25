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
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Image {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NonNull
    private String image;

    @ManyToOne
    @JoinColumn(name = "gameshow_id")
    private GameShow gameShow;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contestant_image", 
      joinColumns = @JoinColumn(name = "contestant_id", 
                                referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "image_id", 
                                      referencedColumnName = "id"))
	  Set<Contestant> contestants = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag_image", 
      joinColumns = @JoinColumn(name = "tag_id", 
                                referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "image_id", 
                                      referencedColumnName = "id"))
	  Set<Tag> tags = new HashSet<>();

}