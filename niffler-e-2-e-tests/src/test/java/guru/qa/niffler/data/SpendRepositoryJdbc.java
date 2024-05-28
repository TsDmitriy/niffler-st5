package guru.qa.niffler.data;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.model.CurrencyValues;
import io.qameta.allure.Step;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class SpendRepositoryJdbc implements SpendRepository {

    private final static DataSource spendDataSource = DataSourceProvider.dataSource(DataBase.SPEND);

    @Step("Создать категорию")
    public CategoryEntity createCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO category (category, username) VAlUES (?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, category.getCategory());
            statement.setString(2, category.getUsername());
            statement.executeUpdate();

            UUID uuid = null;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    uuid = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalArgumentException("Не удалось получить uuid категории");
                }
            }
            category.setId(uuid);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CategoryEntity findCategory(String category) {
        return null;
    }

    @Step("Получить категорию с id {0}")
    public CategoryEntity getCategory(UUID id) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT * FROM category where id = ?;",
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {
            statement.setObject(1, id);
            statement.execute();

            CategoryEntity category = new CategoryEntity();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category.setId(UUID.fromString(resultSet.getString("id")));
                    category.setUsername(resultSet.getString("username"));
                    category.setCategory(resultSet.getString("category"));
                } else {
                    throw new IllegalArgumentException("Не удалось получить данные по категории");
                }
            }
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Изменить категорию")
    public CategoryEntity editCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("UPDATE category SET category=?, username=? WHERE id = ?",
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, category.getCategory());
            statement.setString(2, category.getUsername());
            statement.setObject(3, category.getId());
            statement.executeUpdate();


            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    category.setId(UUID.fromString(resultSet.getString("id")));
                    category.setUsername(resultSet.getString("username"));
                    category.setCategory(resultSet.getString("category"));
                } else {
                    throw new IllegalArgumentException("Не удалось получить данные по категории");
                }
            }
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Удалить категорию")
    public void removeCategory(CategoryEntity category) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("DELETE FROM category where id =?"
                     )) {

            statement.setObject(1, category.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Создать трату")
    public SpendEntity createSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO public.spend(username, spend_date, currency, amount, description, category_id)" +
                                     " VALUES (?, ?, ?, ?, ?, ?);",
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, spend.getUsername());
            statement.setDate(2, (Date) spend.getSpendDate());
            statement.setString(3, spend.getCurrency().toString());
            statement.setDouble(4, spend.getAmount());
            statement.setString(5, spend.getDescription());
            statement.setObject(6, spend.getCategory());
            statement.executeUpdate();

            UUID uuid = null;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    uuid = UUID.fromString(resultSet.getString("id"));
                } else {
                    throw new IllegalArgumentException("Не удалось получить данные по трате");
                }
            }
            spend.setId(uuid);
            return spend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Изменить трату")
    public SpendEntity editSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("UPDATE spend SET username=?, spend_date=?, currency=?, amount=?, description=?, category_id=? " +
                                     "WHERE id = ?;",
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, spend.getUsername());
            statement.setDate(2, (Date) spend.getSpendDate());
            statement.setString(3, spend.getCurrency().toString());
            statement.setDouble(4, spend.getAmount());
            statement.setString(5, spend.getDescription());
            statement.setObject(6, spend.getCategory());
            statement.setObject(7, spend.getId());
            statement.executeUpdate();


            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    spend.setId(UUID.fromString(resultSet.getString("id")));
                    spend.setUsername(resultSet.getString("username"));
                    spend.setSpendDate(resultSet.getDate("spend_date"));
                    spend.setCurrency(CurrencyValues.valueOf(resultSet.getString("currency")));
                    spend.setAmount(resultSet.getDouble("amount"));
                    spend.setDescription(resultSet.getString("description"));
                    spend.setCategory((CategoryEntity) resultSet.getObject("category_id"));
                } else {
                    throw new IllegalArgumentException("Неудалось получить данные о трате");
                }
            }
            return spend;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Удалить трату")
    public void removeSpend(SpendEntity spend) {
        try (Connection connection = spendDataSource.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("DELETE FROM public.spend where id =?"
                     )) {

            statement.setObject(1, spend.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SpendEntity> findAllByUsername(String username) {
        return null;
    }
}
