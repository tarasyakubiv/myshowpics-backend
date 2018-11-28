package com.tarasyakubiv.myshowpics.service;

import java.util.List;


import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private ContestantService contestantService;

    @Autowired
    private GameShowService showService;

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

    public Image updateImage(Integer id, Image newImage) {
        newImage.setId(id);
        return imageRepository.save(newImage);
    }

    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }

    public Image createImageTag(Image image, Integer tagId) {
        image.getTags().add(tagService.getTag(tagId));
        return imageRepository.save(image);
    }

    public Image addContestant(Image image, Integer contestantId) {
        image.getContestants().add(contestantService.getContestant(contestantId));
        return imageRepository.save(image);
    }

    public Image setShow(Image image, Integer showId) {
        image.setGameShow(showService.getShow(showId));
        return imageRepository.save(image);
    }

    public Image deleteShow(Image image) {
        image.setGameShow(null);
        return imageRepository.save(image);
    }

    public Image deleteContestant(Image image, Integer contestantId) {
        image.getContestants().remove(contestantService.getContestant(contestantId));
        return imageRepository.save(image);
    }

    public Image deleteImageTag(Image image, Integer tagId) {
        image.getTags().remove(tagService.getTag(tagId));
        return imageRepository.save(image);
    }
}