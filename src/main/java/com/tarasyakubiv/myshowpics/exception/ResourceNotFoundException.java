package com.tarasyakubiv.myshowpics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1233420819048087922L;
    private String resourceName;

    public ResourceNotFoundException (String resourceName) {
        super(String.format("%s not found", resourceName));
        this.resourceName = resourceName;
    }
}