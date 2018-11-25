package com.tarasyakubiv.myshowpics.service;

import java.util.List;

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
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ContestantRepository contestantRepository;

    @Autowired
    private GameShowRepository showRepository;

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

    public Image updateImage(Integer id, Image image) {
        imageRepository.findById(id).
                            orElseThrow(() -> new ResourceNotFoundException("Image"));
        image.setId(id);
        return imageRepository.save(image);
    }

    public void deleteImage(Integer id) {
        Image image = imageRepository.findById(id).
                                orElseThrow(() -> new ResourceNotFoundException("Image"));
        imageRepository.delete(image);
    }

    public Image createImageTag(Integer id, Integer tagId) {
        Image image = imageRepository.findById(tagId).
                        orElseThrow(() -> new ResourceNotFoundException("Image"));
        Tag tag = tagRepository.findById(tagId).
                        orElseThrow(() -> new ResourceNotFoundException("Tag"));
        image.getTags().add(tag);
        return imageRepository.save(image);
    }

    public Image addContestant(Integer id, Integer contestantId) {
        Image image = imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image"));
        Contestant contestant = contestantRepository.findById(contestantId).
                        orElseThrow(() -> new ResourceNotFoundException("Contestant"));
        image.getContestants().add(contestant);
        return imageRepository.save(image);
    }

    public Image setShow(Integer id, Integer showId) {
        Image image = imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image"));
        GameShow gameShow = showRepository.findById(showId).
                                orElseThrow(() -> new ResourceNotFoundException("GameShow"));
        image.setGameShow(gameShow);
        return imageRepository.save(image);
    }

    public Image deleteShow(Integer id) {
        Image image = imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image"));
        image.setGameShow(null);
        return imageRepository.save(image);
    }

    public Image deleteContestant(Integer id, Integer contestantId) {
        Image image =imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image"));
        Contestant contestant = contestantRepository.findById(id).
                                                orElseThrow(() -> new ResourceNotFoundException("Contestant"));
        image.getContestants().remove(contestant);
        return imageRepository.save(image);
    }

    public Image deleteImageTag(Integer id, Integer tagId) {
        Image image = imageRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Image"));
        Tag tag = tagRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("Tag"));
        image.getTags().remove(tag);
        return imageRepository.save(image);
    }

}