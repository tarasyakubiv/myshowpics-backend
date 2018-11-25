package com.tarasyakubiv.myshowpics.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.domain.GameShow;
import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.service.GameShowService;

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
    GameShowService gameShowService;

    @GetMapping
    public List<GameShow> getAllShows() {
        return gameShowService.getAllShows();
    }

    @GetMapping("/{id}")
    public GameShow getShow(@PathVariable("id") Integer id) {
        return gameShowService.getShow(id);
    }

    @PostMapping
    public GameShow createShow(@Valid @RequestBody GameShow show) {
        return gameShowService.createShow(show);
    }

    @DeleteMapping("/{id}")
    public void deleteShow(@PathVariable("id") Integer id) {
        gameShowService.deleteShow(id);
    }

    @GetMapping("/{id}/contestants")
    public Set<Contestant> getContestants(@PathVariable("id") Integer id) {
        return gameShowService.getContestants(id);
    }

    @PutMapping("/{id}/contestants/{contestantId}")
    public void addContestant(@PathVariable("id") Integer id, @PathVariable("contestantId") Integer contestantId) {
        gameShowService.addContestant(id, contestantId);
    }

    @DeleteMapping("/{id}/contestants/{contestantId}")
    public void deleteContestant(@PathVariable("id") Integer id, @PathVariable("contestantId") Integer contestantId) {
        gameShowService.deleteContestant(id, contestantId);
    }

    @GetMapping("/{id}/images")
    public Set<Image> getImagesByShow(@PathVariable("id") Integer id) {
        return gameShowService.getImagesByShow(id);
    }
}