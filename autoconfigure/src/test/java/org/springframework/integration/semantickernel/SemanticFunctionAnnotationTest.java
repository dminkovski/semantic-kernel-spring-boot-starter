package org.springframework.integration.semantickernel;


import com.microsoft.semantickernel.textcompletion.CompletionSKFunction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.semantickernel.semanticfunctions.SemanticFunction;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SemanticKernelApplication.class)
@ContextConfiguration(classes={SemanticFunction.class})
public class SemanticFunctionAnnotationTest {

    @SemanticFunction(skill = "TestSkill", function = "TestFunction")
    public CompletionSKFunction summarizeFunction;

    @SemanticFunction(skill = "TestSkill2", function = "TestFunction2")
    public CompletionSKFunction summarizeFunction2;

    @SemanticFunction(skill = "TestSkill", function = "TestFunction")
    public CompletionSKFunction summarizeFunction3;

    @Test
    public void whenCompletionSKFunctuinInjected_thenItExists() {
        assertNotNull(summarizeFunction);
        assertNotEquals(summarizeFunction, summarizeFunction2);
        assertEquals(summarizeFunction, summarizeFunction3);
    }
}
