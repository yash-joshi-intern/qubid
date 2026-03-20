package com.qubid.backend;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class DevConfig {

    @Bean
    public CommandLineRunner run(){
        return args->System.out.println("Hello From Prod");
    }
}
