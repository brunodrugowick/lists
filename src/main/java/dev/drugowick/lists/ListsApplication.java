package dev.drugowick.lists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ListsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListsApplication.class, args);
    }

}
