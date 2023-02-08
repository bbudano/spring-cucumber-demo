package com.example.springcucumberdemo;

import com.example.springcucumberdemo.model.Guitar;
import com.example.springcucumberdemo.repository.GuitarRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SpringCucumberDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCucumberDemoApplication.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> applicationReadyEventListener(GuitarRepository guitarRepository) {
        return event -> {
            var guitars = List.of(
                    new Guitar(null, "Fender", "Stratocaster", "White", UUID.randomUUID().toString(), new BigDecimal("2999.99")),
                    new Guitar(null, "Gibson", "Les Paul", "Black", UUID.randomUUID().toString(), new BigDecimal("4999.99")),
                    new Guitar(null, "Fender", "Telecaster", "Purple", UUID.randomUUID().toString(), new BigDecimal("3999.99"))
            );

            guitarRepository.saveAll(guitars);
        };
    }

}
