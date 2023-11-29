package org.springframework.integration.semantickernel;

import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.semantickernel.semanticfunctions.SemanticFunction;
import org.springframework.stereotype.Component;

@Component
public class Test {

    @SemanticFunction(skill = "TestSkill", function = "TestFunction")
    public CompletionSKFunction summarizeFunction;

    public Test(){

    }
}
