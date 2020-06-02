package com.gab.spring5webfluxrest.controllers;

import com.gab.spring5webfluxrest.domain.Category;
import com.gab.spring5webfluxrest.repositories.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;


    @BeforeEach
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }


    @Test
    public void list() {
        Category cat1 = Category.builder().description("cat_1").build();
        Category cat2 = Category.builder().description("cat_2").build();

        given(categoryRepository.findAll())
                .willReturn(Flux.just(cat1,cat2));

        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);


    }

    @Test
    public void getById() {

        Category category = Category.builder().description("cat_1").build();

        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(category));
        webTestClient.get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Category.class)
                .equals(category);
    }

    @Test
    public void createCategory() {

        Category category = Category.builder().description("cat_1").build();

        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(category));


       Flux<Category> catToSave = Flux.just(category);

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(catToSave, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateCategory() {

        Category category = Category.builder().description("cat_1").build();

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(category));

        Mono<Category> categoryMono = Mono.just(category);

        webTestClient.put()
                .uri("/api/v1/categories/1")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isAccepted()
                .expectBody(Category.class);
    }

}