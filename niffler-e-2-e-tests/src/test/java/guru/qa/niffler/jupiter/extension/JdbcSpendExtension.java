package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.SpendRepositoryJdbc;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;

import java.sql.Date;

public class JdbcSpendExtension extends AbstractSpendExtension {
    private final SpendRepository spendRepository = new SpendRepositoryJdbc();

    @Override
    protected Object createSpend(GenerateSpend spend, CategoryJson category) {
        SpendEntity spendEntity = new SpendEntity();
        spendEntity.setUsername(category.username());
        spendEntity.setCurrency(spend.currency());
        spendEntity.setSpendDate(new Date(System.currentTimeMillis()));
        spendEntity.setAmount(spend.amount());
        spendEntity.setDescription(spend.description());
        spendEntity.setCategory(category.id());

        return spendRepository.createSpend(spendEntity);
    }

    @Override
    protected void removeSpend(SpendEntity spend) {
        spendRepository.removeSpend(spend);
    }
}
