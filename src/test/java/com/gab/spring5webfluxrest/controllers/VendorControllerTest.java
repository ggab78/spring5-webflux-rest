package com.gab.spring5webfluxrest.controllers;

import com.gab.spring5webfluxrest.domain.Vendor;
import com.gab.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorController vendorController;
    VendorRepository vendorRepository;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {

        BDDMockito.given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder().firstName("first").lastName("last").build()));
        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(1);
    }

    @Test
    void getById() {
        Vendor vendor = Vendor.builder().firstName("first").lastName("last").build();
        BDDMockito.given(vendorRepository.findById(anyString())).willReturn(Mono.just(vendor));
        webTestClient.get()
                .uri("/api/v1/vendors/1")
                .exchange()
                .expectBody(Vendor.class)
                .equals(vendor);
    }


    @Test
    void create() {
        Vendor vendor = Vendor.builder().firstName("first").lastName("last").build();

        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(vendor));

        Mono<Vendor> vendorToCreate = Mono.just(vendor);

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorToCreate, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

}