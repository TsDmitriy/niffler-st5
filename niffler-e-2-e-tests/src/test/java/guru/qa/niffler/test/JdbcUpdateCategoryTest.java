package guru.qa.niffler.test;

import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.jupiter.extension.JdbcCategoryExtension;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({BrowserExtension.class, JdbcCategoryExtension.class})
public class JdbcUpdateCategoryTest {

    private CategoryEntity category = new CategoryEntity();
    private final SpendRepositoryJdbc spendRepositoryJdbc = new SpendRepositoryJdbc();

    @GenerateCategory(
            category = "Обучение122",
            username = "tsarev"
    )
    @Test
    void testUpdateCategory(CategoryJson categoryJson) {
        category = spendRepositoryJdbc.getCategory(categoryJson.id());

        category.setCategory("Новое обучение1");

        CategoryEntity newCategory = spendRepositoryJdbc.editCategory(category);

        assertEquals(
                "Новое обучение1",
                newCategory.getCategory(), "Проверяем поле category у траты"
        );

    }
}
