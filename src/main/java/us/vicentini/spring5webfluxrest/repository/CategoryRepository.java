package us.vicentini.spring5webfluxrest.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import us.vicentini.spring5webfluxrest.domain.Category;

@Repository
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
