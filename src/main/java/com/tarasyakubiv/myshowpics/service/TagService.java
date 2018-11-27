package com.tarasyakubiv.myshowpics.service;

import java.util.List;

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

    public void deleteTag(Integer id) {
        Tag tag = tagRepository.findById(id).
                            orElseThrow(() -> new ResourceNotFoundException("Tag"));
        tag.getImages().clear();
        tagRepository.delete(tag);
    }
}