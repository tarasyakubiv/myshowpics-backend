package com.tarasyakubiv.myshowpics.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.domain.Tag;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;
import com.tarasyakubiv.myshowpics.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagRepository tagRepository;

    @GetMapping("/images")
    public Set<Image> findImages(@RequestParam(value="show", required = false) String show,
                                @RequestParam(value="tags", required = false) String tags,
                                @RequestParam(value="contestants", required = false) String contestants) {
        return new HashSet<>();
    }

    @GetMapping("/or/{names}")
    public Set<Image> getImagesByTagsOr(@PathVariable("names") String names) {
        return tagRepository.findByNameIn(Arrays.asList(names.split(","))).
                    stream().
                    map(tag -> tag.getImages()).
                    flatMap(Collection::stream).
                    collect(Collectors.toSet());

    }

    @GetMapping("/and/{names}")
    public Set<Image> getImagesByTagsAnd(@PathVariable("names") String names) {
        List<Tag> tags = tagRepository.findByNameIn(Arrays.asList(names.split(",")));
        return tags.stream().
                    map(tag -> tag.getImages()).
                    flatMap(Collection::stream).
                    filter(image -> image.getTags().containsAll(tags)).
                    collect(Collectors.toSet());
    }


}