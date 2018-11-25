package com.tarasyakubiv.myshowpics.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.domain.Tag;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;
import com.tarasyakubiv.myshowpics.repository.TagRepository;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagRepository tagRepository;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Tag getTag(@PathVariable("id") Integer id) {
        return tagRepository.findById(id).
                                        orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
    }

    @PostMapping
    public Tag createTag(@Valid @RequestBody Tag tag) {
        Tag tagInUse = tagRepository.findOptionalByName(tag.getName()).orElse(tag);
        return tagRepository.save(tagInUse);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Integer id) {
        Tag tag = tagRepository.findById(id).
                            orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
        tag.getImages().forEach(image -> {
                                            image.getTags().remove(tag);
                                            imageRepository.save(image);    
        });
        tagRepository.delete(tag);
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