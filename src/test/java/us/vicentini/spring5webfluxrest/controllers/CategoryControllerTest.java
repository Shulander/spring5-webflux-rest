package us.vicentini.spring5webfluxrest.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import us.vicentini.spring5webfluxrest.domain.Category;
import us.vicentini.spring5webfluxrest.repository.CategoryRepository;
import us.vicentini.spring5webfluxrest.util.ObjectUtil;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static us.vicentini.spring5webfluxrest.controllers.CategoryController.BASE_PATH;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    private static final String NAME = "Jim";
    private static final String ID = "1";
    private static final String ID2 = "2";

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryController categoryController;
    private WebTestClient webTestClient;


    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }


    @Test
    public void testListCategories() {
        Category category1 = new Category();
        category1.setId(ID);
        category1.setName(NAME);

        Category category2 = new Category();
        category2.setId(ID2);
        category2.setName("Bob");

        List<Category> categories = Arrays.asList(category1, category2);

        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.fromIterable(categories));

        webTestClient.get()
                .uri(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(2);
    }


    @Test
    public void testGetByNameCategories() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        BDDMockito.given(categoryRepository.findCategoryByName(NAME))
                .willReturn(Mono.just(category));

        webTestClient.get()
                .uri(BASE_PATH + "/" + NAME)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class)
                .isEqualTo(category);
    }


    @Test
    public void testGetByNameCategoriesEmtpy() {
        BDDMockito.given(categoryRepository.findCategoryByName(NAME))
                .willReturn(Mono.empty());

        webTestClient.get()
                .uri(BASE_PATH + "/Jim")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

    }


    @Test
    void shouldCreateNewCategory() {
        Category newCategory = Category.builder()
                .name("New Category")
                .build();
        Category persistedCategory = newCategory.toBuilder().id(ID).build();
        BDDMockito.given(categoryRepository.save(newCategory))
                .willReturn(Mono.just(persistedCategory));

        webTestClient.post()
                .uri(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ObjectUtil.asJsonString(newCategory))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Category.class)
                .isEqualTo(persistedCategory);
    }


    @Test
    void shouldUpdateCategory() {
        Category category = Category.builder()
                .name("New Category")
                .build();
        Category categoryWithId = category.toBuilder().id(ID).build();
        BDDMockito.given(categoryRepository.save(categoryWithId))
                .willReturn(Mono.just(categoryWithId));

        webTestClient.put()
                .uri(BASE_PATH + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ObjectUtil.asJsonString(category))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Category.class)
                .isEqualTo(categoryWithId);
    }


    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(categoryRepository);
    }
}
