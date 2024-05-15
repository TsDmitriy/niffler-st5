package guru.qa.niffler.test;

import guru.qa.niffler.data.SpendRepositoryJdbc;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.JdbcTest;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
public class JdbcUpdateSpendTest {

    private final SpendRepositoryJdbc spendRepositoryJdbc = new SpendRepositoryJdbc();

    @GenerateCategory(
            category = "Обучение22",
            username = "tsarev"
    )
    @GenerateSpend(
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void testUpdateSpendTest(SpendJson spendJson, CategoryJson categoryJson) {

        SpendEntity spend = SpendEntity.fromJson(spendJson, CategoryEntity.fromJson(categoryJson));
        spend.setDescription("QA.GURU Advanced 2024");

        SpendEntity newSpend = spendRepositoryJdbc.editSpend(spend);

        assertEquals(
                "QA.GURU Advanced 2024",
                newSpend.getDescription(), "Проверяем поле description у траты"
        );

    }
}
