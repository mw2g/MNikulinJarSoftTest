package com.nikulin.MNikulinJarSoftTest.controller;

import com.nikulin.MNikulinJarSoftTest.domain.Banner;
import com.nikulin.MNikulinJarSoftTest.service.BannerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin
@RequestMapping("/api/banner")
@AllArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<List<Banner>> getAll() {
        return ResponseEntity.status(OK)
                .body(bannerService.getAll());
    }

    @GetMapping("/{bannerId}")
    public ResponseEntity<Banner> getById(@PathVariable Integer bannerId) {
        return ResponseEntity.status(OK)
                .body(bannerService.getById(bannerId));
    }

    @PostMapping
    public ResponseEntity<Banner> create(@Valid @RequestBody Banner banner) {
        return ResponseEntity.status(OK)
                .body(bannerService.create(banner));
    }

    @PutMapping
    public ResponseEntity<Banner> update(@Valid @RequestBody Banner banner) {
        return ResponseEntity.status(OK).body(bannerService.update(banner));
    }

    @DeleteMapping("/{bannerId}")
    public ResponseEntity delete(@PathVariable Integer bannerId) {
        bannerService.delete(bannerId);
        return ResponseEntity.status(OK).build();
    }
}
