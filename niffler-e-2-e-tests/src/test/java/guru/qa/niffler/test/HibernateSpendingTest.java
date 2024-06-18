package guru.qa.niffler.test;

import com.codeborne.selenide.ElementsCollection;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.JdbcTest;
import guru.qa.niffler.jupiter.annotation.meta.Spends;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.condition.SpendsCondition.spendsInTable;

@JdbcTest
public class HibernateSpendingTest extends BaseTest {

    final String frontUrl = CFG.frontUrl();
    private static String DATE_FORMATTER = "dd MMM yy";

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
            category = "Обучение1fr223e",
            username = username
    )

    @Spends({
            @GenerateSpend(
                    description = "QA.GURU Advanced443233",
                    amount = 65000.22,
                    currency = CurrencyValues.RUB
            ),

            @GenerateSpend(
                    description = "QA.GURU Advanced написание диплома1232",
                    amount = 15000.123,
                    currency = CurrencyValues.RUB
            )
    })

    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson[] spendJson) {
        final int expectedSize = 0;

        SpendJson spendJsonFirst = spendJson[0];

        ElementsCollection spends = mainPage.getRowsSpendingTable();

        spends.shouldHave(spendsInTable(spendJson));

    }
}
