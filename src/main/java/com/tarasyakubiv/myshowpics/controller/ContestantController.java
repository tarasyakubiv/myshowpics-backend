package com.tarasyakubiv.myshowpics.controller;

import java.util.List;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.service.ContestantService;

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
@RequestMapping("/contestants")
public class ContestantController {

    @Autowired
    ContestantService contestantService;

    @GetMapping
    public List<Contestant> getAllContestants() {
        return contestantService.getAllContestants();
    }

    @GetMapping("/{id}")
    public Contestant getContestant(@PathVariable("id") Integer id) {
        return contestantService.getContestant(id);
    }

    @PostMapping
    public Contestant createContestant(@Valid @RequestBody Contestant contestant) {
        return contestantService.createContestant(contestant);
    }

    @PutMapping("/{id}")
    public Contestant updateContestant(@PathVariable("id") Integer id, @Valid @RequestBody Contestant contestant) {
        return contestantService.updateContestant(id, contestant);
    }

    @DeleteMapping("/{id}")
    public void deleteContestant(@PathVariable("id") Integer id) {
        contestantService.deleteContestant(contestantService.getContestant(id));
    }

    @PatchMapping("/{id}/shows/{showId}")
    public void addShow(@PathVariable("id") Integer id, @PathVariable("showId") Integer showId) {
        contestantService.addShow(id, showId);
    }

    @DeleteMapping("/{id}/shows/{showId}")
    public void deleteShow(@PathVariable("id") Integer id, @PathVariable("showId") Integer showId) {
        contestantService.deleteShow(id, showId);
    }


}