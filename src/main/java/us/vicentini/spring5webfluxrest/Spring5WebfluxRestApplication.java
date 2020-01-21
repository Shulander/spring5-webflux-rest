package us.vicentini.spring5webfluxrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class Spring5WebfluxRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring5WebfluxRestApplication.class, args);
    }

}
