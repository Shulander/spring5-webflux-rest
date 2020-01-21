package us.vicentini.spring5webfluxrest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import us.vicentini.spring5webfluxrest.domain.Vendor;
import us.vicentini.spring5webfluxrest.repository.VendorRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping(VendorController.BASE_PATH)
public class VendorController {
    public static final String BASE_PATH = "/api/v1/vendors";

    private final VendorRepository vendorRepository;


    @GetMapping
    public Flux<Vendor> findAll() {
        return vendorRepository.findAll();
    }


    @GetMapping("/{id}")
    public Mono<Vendor> findVendorById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

}
