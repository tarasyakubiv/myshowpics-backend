package com.tarasyakubiv.myshowpics.service;

import java.util.List;
import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.domain.Tag;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;
import com.tarasyakubiv.myshowpics.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private TagRepository tagRepository;
    
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTag( Integer id) {
        return tagRepository.findById(id).
                                        orElseThrow(() -> new ResourceNotFoundException("Tag"));
    }

    public Tag createTag(Tag tag) {
        Tag tagInUse = tagRepository.findOptionalByName(tag.getName()).orElse(tag);
        return tagRepository.save(tagInUse);
    }

    public void deleteTag(Tag tag) {
        tag.getImages().forEach(image -> {
            image.getTags().remove(tag);
            imageRepository.save(image);
        });
        tagRepository.delete(tag);
    }

    public Set<Image> getImages(Tag tag) {
        return tag.getImages();
    }

    public List<Tag> findByNameIn(List<String> names) {
        return tagRepository.findByNameIn(names);
    } 
}