package com.gab.spring5webfluxrest.bootstrap;

import com.gab.spring5webfluxrest.domain.Category;
import com.gab.spring5webfluxrest.domain.Vendor;
import com.gab.spring5webfluxrest.repositories.CategoryRepository;
import com.gab.spring5webfluxrest.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {

        if(categoryRepository.count().block()==0){
            System.out.println("LOADING DATA ON STARTUP");

            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();
            System.out.println("Loaded categories : "+categoryRepository.count().block());

            vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Buck").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Gab").lastName("Gam").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Mat").lastName("Pat").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Bill").lastName("Nershi").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Jimi").lastName("Buffet").build()).block();
            System.out.println("Loaded vendor : "+vendorRepository.count().block());

        }

    }
}
