package com.tarasyakubiv.myshowpics.service;

import java.util.List;
import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.domain.GameShow;
import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;
import com.tarasyakubiv.myshowpics.repository.ContestantRepository;
import com.tarasyakubiv.myshowpics.repository.GameShowRepository;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameShowService {

    @Autowired
    private ContestantRepository contestantRepository;

    @Autowired
    private ImageRepository imageRepository;

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

    public void deleteShow(Integer id) {
        GameShow show = showRepository.findById(id).
                                            orElseThrow(() -> new ResourceNotFoundException("Show"));
        show.getImages().forEach(image -> {
                                    image.setGameShow(null);
                                    imageRepository.save(image);
        }); 
        show.getContestants().forEach(contestant -> {
            contestant.getGameShows().remove(show);
            contestantRepository.save(contestant);
        });                                          
        showRepository.delete(show);
    }

    public Set<Contestant> getContestants(Integer id) {
        GameShow show = showRepository.findById(id).
                                        orElseThrow(() -> new ResourceNotFoundException("Show"));
        return show.getContestants();
    }

    public void addContestant(Integer id, Integer contestantId) {
        Contestant contestant = contestantRepository.findById(id).
                                orElseThrow(() -> new ResourceNotFoundException("Show"));
        GameShow gameShow = showRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("GameShow"));
        gameShow.getContestants().add(contestant);
        showRepository.save(gameShow);
    }

    public void deleteContestant(Integer id, Integer contestantId) {
        Contestant contestant = contestantRepository.findById(id).
                                orElseThrow(() -> new ResourceNotFoundException("Contestant"));
        GameShow gameShow = showRepository.findById(id).
                        orElseThrow(() -> new ResourceNotFoundException("GameShow"));
        gameShow.getContestants().remove(contestant);
        showRepository.save(gameShow);
    }

    public Set<Image> getImagesByShow( Integer id) {
        GameShow show = showRepository.findById(id).
                                        orElseThrow(() -> new ResourceNotFoundException("Show"));
        return show.getImages();
    }
}