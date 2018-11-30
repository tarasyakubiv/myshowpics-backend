package com.tarasyakubiv.myshowpics.service;

import java.util.List;
import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;
import com.tarasyakubiv.myshowpics.repository.ContestantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContestantService {

    @Autowired
    private ContestantRepository contestantRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private GameShowService showService;

    public List<Contestant> getAllContestants() {
        return contestantRepository.findAll();
    }

    public Contestant getContestant(Integer id) {
        return contestantRepository.findById(id).
                                            orElseThrow(() -> new ResourceNotFoundException("Contestant"));
    }

    public Contestant createContestant(Contestant contestant) {
        Contestant contestantInUse = contestantRepository.findOptionalByName(contestant.getName()).orElse(contestant);
        return contestantRepository.save(contestantInUse);
    }

    public Contestant updateContestant(Integer id, Contestant newContestant) {
        newContestant.setId(id);
        return contestantRepository.save(newContestant);
    }

    public void deleteContestant(Contestant contestant) {
        contestant.getImages().forEach(image -> {
            image.getContestants().remove(contestant);
            imageService.updateImage(image.getId(), image);
        });
        contestant.getGameShows().forEach(gameShow -> {
            gameShow.getContestants().remove(contestant);
            showService.updateShow(gameShow.getId(), gameShow);
        });                                 
        contestantRepository.delete(contestant);
    }

    public void addShow(Integer id, Integer showId) {
        showService.addContestant(showService.getShow(showId), id);
    }

    public void deleteShow(Integer id, Integer showId) {
        showService.deleteContestant(showService.getShow(showId), id);
    }

    public List<Contestant> findByNameIn(List<String> names) {
        return contestantRepository.findByNameIn(names);
    }

    public Set<Image> getImages(Contestant contestant) {
        return contestant.getImages();
    }
}