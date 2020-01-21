package us.vicentini.spring5webfluxrest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import us.vicentini.spring5webfluxrest.domain.Vendor;
import us.vicentini.spring5webfluxrest.repository.VendorRepository;

import static us.vicentini.spring5webfluxrest.util.ObjectPropertyUtils.copyIfNonNull;

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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Vendor> save(@RequestBody Mono<Vendor> newVendor) {
        return newVendor.flatMap(vendorRepository::save);
    }


    @PutMapping("/{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Mono<Vendor> newVendor) {
        return Mono.zip(vendorRepository.findById(id), newVendor)
                .flatMap(objects -> {
                    objects.getT2().setId(objects.getT1().getId());
                    return Mono.just(objects.getT2());
                })
                .flatMap(vendorRepository::save);
    }


    @PatchMapping("/{id}")
    public Mono<Vendor> patch(@PathVariable String id, @RequestBody Mono<Vendor> vendor) {
        return Mono.zip(vendorRepository.findById(id), vendor)
                .flatMap(objects -> {
                    if (copyIfNonNull(objects.getT2()::getName, objects.getT1()::setName)) {
                        return vendorRepository.save(objects.getT1());
                    } else {
                        return Mono.just(objects.getT1());
                    }
                });
    }

}
