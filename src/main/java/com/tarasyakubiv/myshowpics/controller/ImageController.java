package com.tarasyakubiv.myshowpics.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageController {
    
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    GameShowRepository gameShowRepository;

    @Autowired
    ContestantRepository contestantRepository;

    @GetMapping
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @GetMapping("/{id}")
    public Image getImage(@PathVariable("id") Integer id) {
        return imageRepository.
                            findById(id).
                                    orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
    }

    @PostMapping
    public Image createImage(@Valid @RequestBody Image image) {
        return imageRepository.save(image);
    }

    @PutMapping("/{id}")
    public Image updateImage(@PathVariable("id") Integer id, @Valid @RequestBody Image image) {
        imageRepository.findById(id).
                                    orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
        image.setId(id);
        return imageRepository.save(image);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable("id") Integer id) {
        Image image = imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
        imageRepository.delete(image);
    }

    @PostMapping("/{id}/tags")
    public Image createImageTag(@PathVariable("id") Integer id, @RequestBody Tag tag) {
        Image image = imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
        Tag tagInUse = tagRepository.findOptionalByName(tag.getName()).orElse(tag);
        image.getTags().add(tagInUse);
        return imageRepository.save(image);
    }

    @PutMapping("/{id}/contestant/{contestantId}")
    public Image addContestant(@PathVariable("id") Integer id, @PathVariable("contestantId") Integer contestantId) {
        Image image = imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
        Contestant contestant = contestantRepository.findById(contestantId).
                        orElseThrow(() -> new ResourceNotFoundException("Contestant", "id", contestantId));
        image.getContestants().add(contestant);
        return imageRepository.save(image);
    }

    @PutMapping("/{id}/show/{showId}")
    public Image setShow(@PathVariable("id") Integer id, @PathVariable("showId") Integer showId) {
        Image image = imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
        GameShow gameShow = gameShowRepository.findById(showId).
                        orElseThrow(() -> new ResourceNotFoundException("GameShow", "id", showId));
        image.setGameShow(gameShow);
        return imageRepository.save(image);
    }

    @DeleteMapping("/{imageId}/show")
    public Image deleteShow(@PathVariable("imageId") Integer imageId) {
        Image image = imageRepository.findById(imageId).
                        orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
        image.setGameShow(null);
        return imageRepository.save(image);
    }

    @DeleteMapping("/{imageId}/contestant/{contestantId}")
    public Image deleteContestant(@PathVariable("imageId") Integer imageId, @PathVariable("contestantId") Integer contestantId) {
        Image image = imageRepository.findById(imageId).
                        orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
        Contestant contestant = contestantRepository.findById(contestantId).
                        orElseThrow(() -> new ResourceNotFoundException("Contestant", "id", contestantId));
        image.getContestants().remove(contestant);
        return imageRepository.save(image);
    }

    @DeleteMapping("/{imageId}/tag/{tagId}")
    public Image deleteImageTag(@PathVariable("imageId") Integer imageId, @PathVariable("tagId") Integer tagId) {
        Image image = imageRepository.findById(imageId).
                        orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
        Tag tag = tagRepository.findById(tagId).
                        orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        image.getTags().remove(tag);
        return imageRepository.save(image);
    }
    
}