package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.JdbcTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

@JdbcTest
public class JdbcWebSpendingTest extends BaseTest{

    private MainPage mainPage;
    private final String username = "dotsarev";
    private final String password = "dotsarev";

    static {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void doLogin() {
        mainPage = open(CFG.frontUrl(), WelcomePage.class)
                .openLoginPage()
                .doLogin(username, password);
    }

    @GenerateCategory(
            category = "Обучение",
            username = username
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void testFindSpendAfterCreateCategoryAndSpendFromDataBase(SpendJson spendJson) {
       mainPage
               .findRowWithSpendingByDescription(spendJson.description())
               .shouldBe(visible);
    }
}
