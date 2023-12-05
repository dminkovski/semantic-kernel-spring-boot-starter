package org.springframework.integration.semantickernel;

import lombok.Getter;

public enum SemanticKernelModel {
    GPT35TURBO("gpt-3.5-turbo"),
    TEXTEMBEDDINGADA002("text-embedding-ada-002");

    @Getter
    private String name;

    SemanticKernelModel(String name){
        this.name = name;
    }

}
