package guru.qa.niffler.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Data;

import java.util.Date;
import java.util.UUID;
@Data
public class SpendEntity {
    private UUID id;
    private String username;
    private CurrencyValues currency;
    private Date spendDate;
    private Double amount;
    private String description;
    private UUID category;

    public static SpendEntity fromJson(SpendJson spendJson, CategoryEntity category) {
        SpendEntity spendEntity = new SpendEntity();
        spendEntity.setId(spendJson.id());
        spendEntity.setUsername(spendJson.username());
        spendEntity.setCurrency(spendJson.currency());
        spendEntity.setSpendDate(spendJson.spendDate());
        spendEntity.setAmount(spendJson.amount());
        spendEntity.setDescription(spendJson.description());
        spendEntity.setCategory(category.getId());
        return spendEntity;
    }

}
