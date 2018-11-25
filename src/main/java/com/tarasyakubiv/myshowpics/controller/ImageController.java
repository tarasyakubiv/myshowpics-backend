package com.tarasyakubiv.myshowpics.controller;

import java.util.List;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.service.ImageService;

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
    ImageService imageService;

    @GetMapping
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    @GetMapping("/{id}")
    public Image getImage(@PathVariable("id") Integer id) {
        return imageService.getImage(id);
    }

    @PostMapping
    public Image createImage(@Valid @RequestBody Image image) {
        return imageService.createImage(image);
    }

    @PutMapping("/{id}")
    public Image updateImage(@PathVariable("id") Integer id, @Valid @RequestBody Image image) {
        return imageService.updateImage(id, image);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable("id") Integer id) {
        imageService.deleteImage(id);
    }

    @PutMapping("/{id}/tags/{tagId}")
    public Image createImageTag(@PathVariable("id") Integer id, @PathVariable("tagId") Integer tagId) {
        return imageService.createImageTag(id, tagId);
    }

    @PutMapping("/{id}/contestants/{contestantId}")
    public Image addContestant(@PathVariable("id") Integer id, @PathVariable("contestantId") Integer contestantId) {
        return imageService.addContestant(id, contestantId);
    }

    @PutMapping("/{id}/show/{showId}")
    public Image setShow(@PathVariable("id") Integer id, @PathVariable("showId") Integer showId) {
        return imageService.setShow(id, showId);
    }

    @DeleteMapping("/{imageId}/show")
    public Image deleteShow(@PathVariable("imageId") Integer imageId) {
        return imageService.deleteShow(imageId);
    }

    @DeleteMapping("/{imageId}/contestants/{contestantId}")
    public Image deleteContestant(@PathVariable("imageId") Integer imageId, @PathVariable("contestantId") Integer contestantId) {
        return imageService.deleteContestant(imageId, contestantId);
    }

    @DeleteMapping("/{imageId}/tags/{tagId}")
    public Image deleteImageTag(@PathVariable("imageId") Integer imageId, @PathVariable("tagId") Integer tagId) {
        return imageService.deleteImageTag(imageId, tagId);
    }
    
}