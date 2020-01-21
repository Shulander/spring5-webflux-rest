package us.vicentini.spring5webfluxrest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import us.vicentini.spring5webfluxrest.domain.Category;
import us.vicentini.spring5webfluxrest.repository.CategoryRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping(CategoryController.BASE_PATH)
public class CategoryController {
    public static final String BASE_PATH = "/api/v1/categories";

    private final CategoryRepository categoryRepository;


    @GetMapping
    public Flux<Category> findAll() {
        return categoryRepository.findAll();
    }


    @GetMapping("/{name}")
    public Mono<Category> findCategoryByName(@PathVariable String name) {
        return categoryRepository.findCategoryByName(name);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Category> save(@RequestBody Mono<Category> newCategory) {
        return newCategory.flatMap(categoryRepository::save);
    }


    @PutMapping("/{id}")
    public Mono<Category> update(@PathVariable String id, @RequestBody Mono<Category> newCategory) {
        return Mono.zip(categoryRepository.findById(id), newCategory)
                .flatMap(objects -> {
                    objects.getT2().setId(objects.getT1().getId());
                    return Mono.just(objects.getT2());
                })
                .flatMap(categoryRepository::save);
    }

}
