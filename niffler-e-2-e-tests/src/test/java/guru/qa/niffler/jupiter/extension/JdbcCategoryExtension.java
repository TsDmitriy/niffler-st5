package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;

public class JdbcCategoryExtension extends AbstractCategoryExtension{
     private final SpendRepository spendRepository = SpendRepository.getInstance();

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
