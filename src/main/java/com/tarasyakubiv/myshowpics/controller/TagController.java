package com.tarasyakubiv.myshowpics.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.domain.Tag;
import com.tarasyakubiv.myshowpics.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable("id") Integer id) {
        return tagService.getTag(id);
    }

    @PostMapping
    public Tag createTag(@Valid @RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Integer id) {
        tagService.deleteTag(tagService.getTag(id));
    }

    @GetMapping("/{id}/images")
    public Set<Image> getImages(@PathVariable("id") Integer id) {
        return tagService.getImages(tagService.getTag(id));
    }
}