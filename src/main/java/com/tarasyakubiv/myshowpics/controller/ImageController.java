package com.tarasyakubiv.myshowpics.controller;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

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
        imageService.deleteImage(imageService.getImage(id));
    }

    @PatchMapping("/{id}/tags/{tagId}")
    public Image createImageTag(@PathVariable("id") Integer id, @PathVariable("tagId") Integer tagId) {
        return imageService.createImageTag(imageService.getImage(id), tagId);
    }

    @PatchMapping("/{id}/contestants/{contestantId}")
    public Image addContestant(@PathVariable("id") Integer id, @PathVariable("contestantId") Integer contestantId) {
        return imageService.addContestant(imageService.getImage(id), contestantId);
    }

    @PatchMapping("/{id}/shows/{showId}")
    public Image setShow(@PathVariable("id") Integer id, @PathVariable("showId") Integer showId) {
        return imageService.setShow(imageService.getImage(id), showId);
    }

    @DeleteMapping("/{imageId}/shows")
    public Image deleteShow(@PathVariable("imageId") Integer id) {
        return imageService.deleteShow(imageService.getImage(id));
    }

    @DeleteMapping("/{imageId}/contestants/{contestantId}")
    public Image deleteContestant(@PathVariable("imageId") Integer id, @PathVariable("contestantId") Integer contestantId) {
        return imageService.deleteContestant(imageService.getImage(id), contestantId);
    }

    @DeleteMapping("/{imageId}/tags/{tagId}")
    public Image deleteImageTag(@PathVariable("imageId") Integer id, @PathVariable("tagId") Integer tagId) {
        return imageService.deleteImageTag(imageService.getImage(id), tagId);
    }
    
}