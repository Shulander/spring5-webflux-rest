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
import us.vicentini.spring5webfluxrest.domain.Vendor;
import us.vicentini.spring5webfluxrest.repository.VendorRepository;
import us.vicentini.spring5webfluxrest.util.ObjectUtil;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static us.vicentini.spring5webfluxrest.controllers.VendorController.BASE_PATH;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {
    public static final String ID_1 = "1";
    public static final String ID_2 = "2";

    @Mock
    VendorRepository vendorRepository;
    @InjectMocks
    VendorController vendorController;
    private WebTestClient webTestClient;


    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }


    @Test
    void findAll() {
        Vendor vendor1 = Vendor.builder()
                .id(ID_1)
                .name("Franks Fresh Fruits from France Ltd.")
                .build();
        Vendor vendor2 = Vendor.builder()
                .id(ID_2)
                .name("Lemuel")
                .build();
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(vendor1, vendor2));

        webTestClient.get()
                .uri(BASE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }


    @Test
    void findVendorById() {
        Vendor vendor1 = Vendor.builder()
                .id(ID_1)
                .name("Franks Fresh Fruits from France Ltd.")
                .build();
        BDDMockito.given(vendorRepository.findById(ID_1))
                .willReturn(Mono.just(vendor1));

        webTestClient.get()
                .uri(BASE_PATH + "/" + ID_1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class)
                .isEqualTo(vendor1);
    }


    @Test
    void shouldCreateNewVendor() {
        Vendor newVendor = Vendor.builder()
                .name("New Vendor")
                .build();
        Vendor persistedVendor = newVendor.toBuilder().id(ID_1).build();
        BDDMockito.given(vendorRepository.save(newVendor))
                .willReturn(Mono.just(persistedVendor));

        webTestClient.post()
                .uri(VendorController.BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ObjectUtil.asJsonString(newVendor))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Vendor.class)
                .isEqualTo(persistedVendor);
    }


    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(vendorRepository);
    }
}
