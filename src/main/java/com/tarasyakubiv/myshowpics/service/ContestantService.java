package com.tarasyakubiv.myshowpics.service;

import java.util.List;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.exception.ResourceNotFoundException;
import com.tarasyakubiv.myshowpics.repository.ContestantRepository;
import com.tarasyakubiv.myshowpics.repository.GameShowRepository;
import com.tarasyakubiv.myshowpics.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContestantService {

    @Autowired
    private ContestantRepository contestantRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private GameShowRepository showRepository;

    public List<Contestant> getAllContestants() {
        return contestantRepository.findAll();
    }

    public Contestant getContestant(Integer id) {
        return contestantRepository.findById(id).
                                            orElseThrow(() -> new ResourceNotFoundException("Contestant"));
    }

    public Contestant createContestant(Contestant contestant) {
        return contestantRepository.save(contestant);
    }

    public void deleteContestant(Integer id) {
        Contestant contestant = contestantRepository.findById(id).
                                            orElseThrow(() -> new ResourceNotFoundException("Contestant"));
        contestant.getImages().forEach(image -> {
                                                image.getContestants().remove(contestant);
                                                imageRepository.save(image);
        });
        contestant.getGameShows().forEach(show -> {
                                                    show.getContestants().remove(contestant);
                                                    showRepository.save(show);
        });                                             
        contestantRepository.delete(contestant);
    }
}