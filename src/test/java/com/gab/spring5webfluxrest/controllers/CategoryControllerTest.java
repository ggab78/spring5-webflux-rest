package com.gab.spring5webfluxrest.controllers;

import com.gab.spring5webfluxrest.domain.Category;
import com.gab.spring5webfluxrest.repositories.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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
                .uri("api/v1/categories/1")
                .exchange()
                .expectBody(Category.class)
                .equals(category);
    }
}