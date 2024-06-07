package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.HttpWebTest;

import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Selenide.open;

public class HttpWebSpendingTest extends BaseTest{

    private MainPage mainPage;
    private final String username = "DIMA";
    private final String password = "DIMA";

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
            category = "Обучение22311734",
            username = username
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        final int expectedSize = 0;

        SelenideElement rowWithSpending = mainPage
                .findRowWithSpendingByDescription(spendJson.description());

        mainPage.chooseSpending(rowWithSpending)
                .deleteSpending()
                .checkCountOfSpendings(expectedSize);

    }
}
