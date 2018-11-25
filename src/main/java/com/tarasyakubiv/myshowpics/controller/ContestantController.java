package com.tarasyakubiv.myshowpics.controller;

import java.util.List;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.domain.GameShow;
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
@RequestMapping("/contestants")
public class ContestantController {

    @Autowired
    ContestantRepository contestantRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    GameShowRepository gameShowRepository;

    @GetMapping
    public List<Contestant> getAllContestants() {
        return contestantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contestant getContestant(@PathVariable("id") Integer id) {
        return contestantRepository.findById(id).
                                            orElseThrow(() -> new ResourceNotFoundException("Show", "id", id));
    }

    @PostMapping
    public Contestant createContestant(@Valid @RequestBody Contestant contestant) {
        return contestantRepository.save(contestant);
    }

    @DeleteMapping("/{id}")
    public void deleteContestant(@PathVariable("id") Integer id) {
        Contestant contestant = contestantRepository.findById(id).
                                            orElseThrow(() -> new ResourceNotFoundException("Show", "id", id));
        contestant.getImages().forEach(image -> {
                                                image.getContestants().remove(contestant);
                                                imageRepository.save(image);
        });
        contestant.getGameShows().forEach(show -> {
                                                    show.getContestants().remove(contestant);
                                                    gameShowRepository.save(show);
        });                                             
        contestantRepository.delete(contestant);
    }

    @PutMapping("/{id}/show/{showId}")
    public void addShow(@PathVariable("id") Integer id, @PathVariable("showId") Integer showId) {
        Contestant contestant = contestantRepository.findById(id).
                                orElseThrow(() -> new ResourceNotFoundException("Contestant", "id", id));
        GameShow gameShow = gameShowRepository.findById(showId).
                        orElseThrow(() -> new ResourceNotFoundException("GameShow", "id", showId));
        contestant.getGameShows().add(gameShow);
        contestantRepository.save(contestant);
    }

    @DeleteMapping("/{id}/show/{showId}")
    public void deleteShow(@PathVariable("id") Integer id, @PathVariable("showId") Integer showId) {
        Contestant contestant = contestantRepository.findById(id).
                                orElseThrow(() -> new ResourceNotFoundException("Contestant", "id", id));
        GameShow gameShow = gameShowRepository.findById(showId).
                        orElseThrow(() -> new ResourceNotFoundException("GameShow", "id", showId));
        contestant.getGameShows().remove(gameShow);
        contestantRepository.save(contestant);
    }
}