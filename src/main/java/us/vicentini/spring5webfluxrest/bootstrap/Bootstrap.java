package us.vicentini.spring5webfluxrest.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import us.vicentini.spring5webfluxrest.domain.Category;
import us.vicentini.spring5webfluxrest.domain.Customer;
import us.vicentini.spring5webfluxrest.domain.Vendor;
import us.vicentini.spring5webfluxrest.repository.CategoryRepository;
import us.vicentini.spring5webfluxrest.repository.CustomerRepository;
import us.vicentini.spring5webfluxrest.repository.VendorRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;


    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
        loadVendors();
    }


    private void loadVendors() {
        Long vendorCount = vendorRepository.count().block();
        if (vendorCount > 0) {
            log.warn("Vendor already has data loaded: {}", vendorCount);
            return;
        }
        Vendor vendor1 = Vendor.builder().name("Franks Fresh Fruits from France Ltd.").build();
        Vendor vendor2 = Vendor.builder().name("Traina Dried Fruit").build();
        vendorRepository.save(vendor1).block();
        vendorRepository.save(vendor2).block();
        log.info("Vendor Data Loaded: {}", vendorRepository.count().block());
    }


    private void loadCustomers() {
        Long customerCount = customerRepository.count().block();
        if (customerCount > 0) {
            log.warn("Customer already has data loaded: {}", customerCount);
            return;
        }
        Customer customer1 = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        Customer customer2 = Customer.builder()
                .firstName("Lemuel")
                .lastName("Gulliver")
                .build();

        customerRepository.save(customer1).block();
        customerRepository.save(customer2).block();
        log.info("Customer Data Loaded: {}", customerRepository.count().block());
    }


    private void loadCategories() {
        Long categoryCount = categoryRepository.count().block();
        if (categoryCount > 0) {
            log.warn("Customer already has data loaded: {}", categoryCount);
            return;
        }
        Category fruits = Category.builder().name("Fruits").build();
        fruits.setName("Fruits");

        Category dried = Category.builder().name("Dried").build();
        dried.setName("Dried");

        Category fresh = Category.builder().name("Fresh").build();
        fresh.setName("Fresh");

        Category exotic = Category.builder().name("Exotic").build();
        exotic.setName("Exotic");

        Category nuts = Category.builder().name("Nuts").build();
        nuts.setName("Nuts");

        categoryRepository.save(fruits).block();
        categoryRepository.save(dried).block();
        categoryRepository.save(fresh).block();
        categoryRepository.save(exotic).block();
        categoryRepository.save(nuts).block();

        log.info("Category Data Loaded: {}", categoryRepository.count().block());
    }
}
