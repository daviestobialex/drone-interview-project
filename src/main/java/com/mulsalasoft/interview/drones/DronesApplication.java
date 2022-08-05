package com.mulsalasoft.interview.drones;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DronesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DronesApplication.class, args);
    }

    @Bean
    public OpenAPI musalasoftDrones() {
        return new OpenAPI()
                .info(new Info().title("Musalasoft Drone Code Interview")
                        .description("Musalasoft Drone Code Interview Service")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
