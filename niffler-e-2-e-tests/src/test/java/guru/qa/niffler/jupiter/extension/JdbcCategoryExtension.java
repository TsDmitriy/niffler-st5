package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.SpendRepositoryJdbc;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;

public class JdbcCategoryExtension extends AbstractCategoryExtension{
     private final SpendRepository spendRepository = new SpendRepositoryJdbc();

    @Override
    protected Object createCategory(GenerateCategory category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(category.category());
        categoryEntity.setUsername(category.username());

        return spendRepository.createCategory(categoryEntity);
    }

    @Override
    protected void removeCategory(CategoryEntity category) {
         spendRepository.removeCategory(category);
    }

}
