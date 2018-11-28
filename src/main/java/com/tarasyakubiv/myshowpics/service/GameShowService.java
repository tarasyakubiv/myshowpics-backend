package com.tarasyakubiv.myshowpics.service;

import java.util.List;
import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.domain.GameShow;
import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;
import com.tarasyakubiv.myshowpics.repository.GameShowRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameShowService {

    @Autowired
    private ContestantService contestantService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private GameShowRepository showRepository;

    public List<GameShow> getAllShows() {
        return showRepository.findAll();
    }

    public GameShow getShow(Integer id) {
        return showRepository.findById(id).
                                            orElseThrow(() -> new ResourceNotFoundException("Show"));
    }

    public GameShow createShow(GameShow show) {
        return showRepository.save(show);
    }

    public GameShow updateShow(Integer id, GameShow show) {
        show.setId(id);
        return showRepository.save(show);
    }

    public void deleteShow(GameShow show) {
        show.getImages().forEach(image -> {
            image.setGameShow(null);
            imageService.updateImage(image.getId(), image);
        });                                     
        showRepository.delete(show);
    }

    public Set<Contestant> getContestants(GameShow show) {
        return show.getContestants();
    }

    public void addContestant(GameShow show, Integer id) {
        Contestant contestant = contestantService.getContestant(id);
        show.getContestants().add(contestant);
        showRepository.save(show);
    }

    public void deleteContestant(GameShow show, Integer id) {
        Contestant contestant = contestantService.getContestant(id);
        show.getContestants().remove(contestant);
        showRepository.save(show);
    }

    public Set<Image> getImagesByShow(GameShow show) {
        return show.getImages();
    }
}