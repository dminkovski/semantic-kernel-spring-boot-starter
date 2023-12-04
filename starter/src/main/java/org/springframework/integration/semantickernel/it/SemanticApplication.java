package org.springframework.integration.semantickernel.it;


import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SemanticApplication {
    public static void main(String[] args){
        SpringApplication.run(SemanticApplication.class, args);
    }

}
