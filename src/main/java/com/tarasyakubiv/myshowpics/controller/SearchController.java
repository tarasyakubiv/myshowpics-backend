package com.tarasyakubiv.myshowpics.controller;



import java.util.Optional;
import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/images")
    public Set<Image> findImages(@RequestParam(value="show", required = false) Optional<String> show,
                                @RequestParam(value="tags", required = false) Optional<String> tags,
                                @RequestParam(value="contestants", required = false) Optional<String> contestants,
                                @RequestParam(value="tags_and", required = false) Optional<Boolean> tagsAnd,
                                @RequestParam(value="contestants_and", required = false) Optional<Boolean> contestantsAnd
                                ) {
        return searchService.findImages(show, tags, contestants, tagsAnd, contestantsAnd);
    }
}