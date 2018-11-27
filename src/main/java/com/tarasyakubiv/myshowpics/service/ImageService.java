package com.tarasyakubiv.myshowpics.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.domain.GameShow;
import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.domain.Tag;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;
import com.tarasyakubiv.myshowpics.repository.ContestantRepository;
import com.tarasyakubiv.myshowpics.repository.GameShowRepository;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;
import com.tarasyakubiv.myshowpics.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ContestantRepository contestantRepository;

    @Autowired
    private GameShowRepository showRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImage(Integer id) {
        return imageRepository.findById(id).
                            orElseThrow(() -> new ResourceNotFoundException("Image"));
    }

    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    public Image updateImage(Image oldImage, Image newImage) {
        newImage.setId(oldImage.getId());
        return imageRepository.save(newImage);
    }

    public void deleteImage(Image image) {
        image.getContestants().clear();
        image.getTags().clear();
        image.setGameShow(null);
        imageRepository.delete(image);
    }

    public Image createImageTag(Image image, Integer tagId) {
        Tag tag = tagRepository.findById(tagId).
                        orElseThrow(() -> new ResourceNotFoundException("Tag"));
        image.getTags().add(tag);
        return imageRepository.save(image);
    }

    public Image addContestant(Image image, Integer contestantId) {
        Contestant contestant = contestantRepository.findById(contestantId).
                        orElseThrow(() -> new ResourceNotFoundException("Contestant"));
        image.getContestants().add(contestant);
        return imageRepository.save(image);
    }

    public Image setShow(Image image, Integer showId) {
        GameShow gameShow = showRepository.findById(showId).
                                orElseThrow(() -> new ResourceNotFoundException("GameShow"));
        image.setGameShow(gameShow);
        return imageRepository.save(image);
    }

    public Image deleteShow(Image image) {
        image.setGameShow(null);
        return imageRepository.save(image);
    }

    public Image deleteContestant(Image image, Integer contestantId) {
        Contestant contestant = contestantRepository.findById(contestantId).
                                                orElseThrow(() -> new ResourceNotFoundException("Contestant"));
        image.getContestants().remove(contestant);
        return imageRepository.save(image);
    }

    public Image deleteImageTag(Image image, Integer tagId) {
        Tag tag = tagRepository.findById(tagId).
                        orElseThrow(() -> new ResourceNotFoundException("Tag"));
        image.getTags().remove(tag);
        return imageRepository.save(image);
    }

    public Set<Image> findImages(Optional<String> show, Optional<String> tags, Optional<String> contestants, Optional<Boolean> tagsAnd, Optional<Boolean> contestantsAnd) {
        final List<Tag> tagsList;
        final List<Contestant> contestantsList;
        if(tags.isPresent()) {
            tagsList = tagRepository.findByNameIn(Arrays.asList(tags.get().split(",")));
        } else {
            tagsList = new ArrayList<>();
        }
        if(contestants.isPresent()) {
            contestantsList = contestantRepository.findByFullNameIn(Arrays.asList(contestants.get().split(",")));
        } else {
            contestantsList = new ArrayList<>();
        }
        Set<Image> resultImages = new HashSet<>();
        imageRepository.findAll().parallelStream().forEach(image -> {
            if(show.isPresent()) {
                if(image.getGameShow().equals(null) || !image.getGameShow().getName().equals(show.get())) {
                    return;
                }
            }
            if(tags.isPresent()) {
                if(tagsAnd.isPresent() && tagsAnd.get()) {
                    if(image.getTags().isEmpty() || !image.getTags().containsAll(tagsList)) {
                        return;
                    }
                } else {
                    if(image.getTags().isEmpty() || Collections.disjoint(image.getTags(), tagsList)){
                        return;
                    }
                }
            }
            if(contestants.isPresent()) {
                if(contestantsAnd.isPresent() && contestantsAnd.get()) {
                    if(image.getContestants().isEmpty() || !image.getContestants().containsAll(contestantsList)) {
                        return;
                    }
                } else {
                    if(image.getContestants().isEmpty() || Collections.disjoint(image.getContestants(), contestantsList)){
                        return;
                    }
                }
            }
            resultImages.add(image);
        }); 
        return resultImages;
    }

}