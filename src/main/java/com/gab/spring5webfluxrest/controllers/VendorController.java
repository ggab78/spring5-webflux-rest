package com.gab.spring5webfluxrest.controllers;

import com.gab.spring5webfluxrest.domain.Vendor;
import com.gab.spring5webfluxrest.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

@RestController
@AllArgsConstructor
public class VendorController {

    private VendorRepository vendorRepository;

    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    public Mono<Void> create(@RequestBody Publisher<Vendor> vendor){
        return vendorRepository.saveAll(vendor).then();
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

}