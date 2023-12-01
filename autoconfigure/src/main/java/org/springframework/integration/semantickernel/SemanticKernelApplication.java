package org.springframework.integration.semantickernel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootApplication
public class SemanticKernelApplication {

    public static void main(String[] args){
        SpringApplication.run(SemanticKernelApplication.class, args);
        System.out.println("Instantiating new Test");
        Test test = new Test();
        System.out.println(test.summarizeFunction);

    }
}
