package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.jpa.EmProvider;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

public class SpendRepositoryHybernate implements SpendRepository {
    private final EntityManager em = EmProvider.dataSource(DataBase.SPEND);

    @Override
    public CategoryEntity findCategory(String category) {
        String hql = "FROM CategoryEntity WHERE category = :category";

        Query query = (Query) em.createQuery(hql, CategoryEntity.class);
        query.setParameter("category", category);

        return (CategoryEntity) query.getSingleResult();
    }

    @Override
    public CategoryEntity getCategory(UUID id) {
        return null;
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        em.persist(category);
        return category;
    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {
        return em.merge(category);
    }

    @Override
    public void removeCategory(CategoryEntity category) {
        em.remove(category);
    }

    @Override
    public SpendEntity createSpend(SpendEntity spend) {
         em.persist(spend);
        return spend;
    }

    @Override
    public SpendEntity editSpend(SpendEntity spend) {
        return em.merge(spend);
    }

    @Override
    public void removeSpend(SpendEntity spend) {
        em.remove(spend);
    }

    @Override
    public List<SpendEntity> findAllByUsername(String username) {
        return null;
    }
}
