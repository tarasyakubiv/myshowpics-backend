package com.tarasyakubiv.myshowpics.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.domain.Tag;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageSearchService {
    
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private ContestantService contestantService;


    public Set<Image> findImages(Optional<String> show, Optional<String> tags, Optional<String> contestants, Optional<Boolean> tagsAnd, Optional<Boolean> contestantsAnd) {
        final List<Tag> tagsList;
        final List<Contestant> contestantsList;
        if(tags.isPresent()) {
            tagsList = tagService.findByNameIn(Arrays.asList(tags.get().split(",")));
        } else {
            tagsList = new ArrayList<>();
        }
        if(contestants.isPresent()) {
            contestantsList = contestantService.findByNameIn(Arrays.asList(contestants.get().split(",")));
        } else {
            contestantsList = new ArrayList<>();
        }
        Set<Image> resultImages = new HashSet<>();
        imageRepository.findAll().parallelStream().forEach(image -> {
            System.out.println(image.getId());
            if(show.isPresent()) {
                if(image.getGameShow() == null || !image.getGameShow().getName().equals(show.get())) {
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