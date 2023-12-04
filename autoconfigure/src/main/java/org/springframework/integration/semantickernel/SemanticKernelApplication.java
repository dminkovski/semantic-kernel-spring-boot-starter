package org.springframework.integration.semantickernel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.semantickernel.semanticfunctions.SemanticFunction;

@SpringBootApplication
@ComponentScan(basePackages = {"org.springframework.integration.semantickernel","org.springframework.integration.semantickernel.semanticfunctions"})
public class SemanticKernelApplication {

    public static void main(String[] args){
        SpringApplication.run(SemanticKernelApplication.class, args);
    }
}
