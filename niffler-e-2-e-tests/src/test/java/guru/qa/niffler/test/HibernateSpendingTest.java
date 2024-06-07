package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.niffler.api.GatewayApiClient;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.HttpWebTest;
import guru.qa.niffler.jupiter.annotation.meta.JdbcTest;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.codeborne.selenide.Selenide.open;

@JdbcTest
public class HibernateSpendingTest extends BaseTest{

    final String frontUrl = CFG.frontUrl();

    private MainPage mainPage;
    private final String username = "DIMA";
    private final String password = "DIMA";


    @BeforeEach
    void doLogin() {
        mainPage = open(frontUrl, WelcomePage.class)
                .openLoginPage()
                .doLogin(username, password);
    }
    @GenerateCategory(
            category = "Обучение24444498",
            username = username
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 44443",
            amount = 65000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        final int expectedSize = 0;

        SelenideElement rowWithSpending = mainPage
                .findRowWithSpendingByDescription(spendJson.description());

    }
}
