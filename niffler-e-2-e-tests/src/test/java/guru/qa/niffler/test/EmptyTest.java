package guru.qa.niffler.test;

import io.qameta.allure.Allure;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AllureJunit5.class)
public class EmptyTest {
    @Test
    void emptyTest() {
        Allure.addAttachment("example", "example");
    }

    @Test
    void emptyTest1() {
        Allure.addAttachment("example", "example");
        Assumptions.assumeTrue(false);
    }
}