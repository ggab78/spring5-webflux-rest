package com.gab.spring5webfluxrest.controllers;

import com.gab.spring5webfluxrest.domain.Category;
import com.gab.spring5webfluxrest.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/api/v1/categories")
    public Flux<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    public Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/categories")
    public Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/api/v1/categories/{id}")
    public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/api/v1/categories/{id}")
    public Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {

        Category existing = categoryRepository.findById(id).block();
        if(!category.getDescription().equals(existing.getDescription())){
            existing.setDescription(category.getDescription());
            return categoryRepository.save(existing);
        }

        return Mono.just(existing);
    }

}
