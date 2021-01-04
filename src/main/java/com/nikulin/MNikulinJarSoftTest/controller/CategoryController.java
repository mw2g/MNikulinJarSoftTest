package com.nikulin.MNikulinJarSoftTest.controller;

import com.nikulin.MNikulinJarSoftTest.domain.Category;
import com.nikulin.MNikulinJarSoftTest.service.CategoryService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.status(OK)
                .body(categoryService.getAll());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getById(@PathVariable Integer categoryId) {
        return ResponseEntity.status(OK)
                .body(categoryService.getById(categoryId));
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        return ResponseEntity.status(OK)
                .body(categoryService.create(category));
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody Category category) {
        return ResponseEntity.status(OK)
                .body(categoryService.update(category));
    }

    @DeleteMapping("/{categoryId}")
    public String delete(@PathVariable Integer categoryId) {
        return new JSONObject().put("message", categoryService.delete(categoryId)).toString();
    }
}
