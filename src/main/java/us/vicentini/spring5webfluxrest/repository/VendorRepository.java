package us.vicentini.spring5webfluxrest.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import us.vicentini.spring5webfluxrest.domain.Vendor;

@Repository
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
