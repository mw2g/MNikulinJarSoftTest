package com.nikulin.MNikulinJarSoftTest.controller;

import com.nikulin.MNikulinJarSoftTest.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin
@RequestMapping
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/bid")
    public ResponseEntity<String> getBanner(@Param("category") String category) {
        HttpStatus status = OK;
        String bannerContent = requestService.getBanner(category);
        if (bannerContent == null) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(bannerContent);
    }
}
