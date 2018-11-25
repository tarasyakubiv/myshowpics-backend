package com.tarasyakubiv.myshowpics.controller;

import java.util.List;

import javax.validation.Valid;

import com.tarasyakubiv.myshowpics.domain.Contestant;
import com.tarasyakubiv.myshowpics.service.ContestantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/{id}")
    public void deleteContestant(@PathVariable("id") Integer id) {
        contestantService.deleteContestant(id);
    }
}