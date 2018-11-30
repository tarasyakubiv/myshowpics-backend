package com.tarasyakubiv.myshowpics.controller;

import java.util.Optional;
import java.util.Set;

import com.tarasyakubiv.myshowpics.domain.Image;
import com.tarasyakubiv.myshowpics.service.ImageSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/images")
public class ImageSearchController {

    @Autowired
    ImageSearchService imageSearchService;

    @GetMapping
    public Set<Image> findImages(@RequestParam(value="shows", required = false) Optional<String> show,
                                    @RequestParam(value="tags", required = false) Optional<String> tags,
                                    @RequestParam(value="contestants", required = false) Optional<String> contestants,
                                    @RequestParam(value="tags_and", required = false) Optional<Boolean> tagsAnd,
                                    @RequestParam(value="contestants_and", required = false) Optional<Boolean> contestantsAnd) {
        return imageSearchService.findImages(show, tags, contestants, tagsAnd, contestantsAnd);
    }

}