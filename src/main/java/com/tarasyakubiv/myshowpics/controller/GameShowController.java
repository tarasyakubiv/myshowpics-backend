package com.tarasyakubiv.myshowpics.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.domain.GameShow;
import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;
import com.tarasyakubiv.myshowpics.repository.ContestantRepository;
import com.tarasyakubiv.myshowpics.repository.GameShowRepository;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;

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
@RequestMapping("/shows")
public class GameShowController {

    @Autowired
    GameShowRepository gameShowRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ContestantRepository contestantRepository;

    @GetMapping
    public List<GameShow> getAllShows() {
        return gameShowRepository.findAll();
    }

    @GetMapping("/{id}")
    public GameShow getShow(@PathVariable("id") Integer id) {
        return gameShowRepository.findById(id).
                                        orElseThrow(() -> new ResourceNotFoundException("Show", "id", id));
    }

    @PostMapping
    public GameShow createShow(@Valid @RequestBody GameShow show) {
        return gameShowRepository.save(show);
    }

    @DeleteMapping("/{id}")
    public void deleteShow(@PathVariable("id") Integer id) {
        GameShow show = gameShowRepository.findById(id).
                                        orElseThrow(() -> new ResourceNotFoundException("Show", "id", id));
        show.getContestants().forEach(contestant -> {
                                                contestant.getGameShows().remove(show);
                                                contestantRepository.save(contestant);
        }); 
        show.getImages().forEach(image -> {
                                            image.setGameShow(null);
                                            imageRepository.save(image);
        });         
        gameShowRepository.delete(show);
    }

    @PutMapping("/{id}/contestant/{contestantId}")
    public void addContestant(@PathVariable("id") Integer id, @PathVariable("contestantId") Integer contestantId) {
        Contestant contestant = contestantRepository.findById(id).
                                orElseThrow(() -> new ResourceNotFoundException("Show", "id", id));
        GameShow gameShow = gameShowRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("GameShow", "id", id));
        gameShow.getContestants().add(contestant);
        gameShowRepository.save(gameShow);
    }

    @DeleteMapping("/{id}/contestant/{contestantId}")
    public void deleteContestant(@PathVariable("id") Integer id, @PathVariable("contestantId") Integer contestantId) {
        Contestant contestant = contestantRepository.findById(id).
                                orElseThrow(() -> new ResourceNotFoundException("Contestant", "id", id));
        GameShow gameShow = gameShowRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("GameShow", "id", id));
        gameShow.getContestants().remove(contestant);
        gameShowRepository.save(gameShow);
    }

    @GetMapping("/{id}/images")
    public Set<Image> getImagesByShow(@PathVariable("id") Integer id) {
        GameShow show = gameShowRepository.findById(id).
                                        orElseThrow(() -> new ResourceNotFoundException("Show", "id", id));
        return imageRepository.findAllByGameShow(show);
    }





}