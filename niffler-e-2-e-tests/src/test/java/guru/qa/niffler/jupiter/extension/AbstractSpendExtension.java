package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.meta.Spends;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSpendExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(AbstractSpendExtension.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) {

        CategoryJson category = CategoryJson.fromEntity(extensionContext.getStore(AbstractCategoryExtension.NAMESPACE)
                .get(extensionContext.getUniqueId(), CategoryEntity.class));
        List<GenerateSpend> potentialSpend = new ArrayList<>();
        AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), Spends.class)
                .ifPresent(spends -> {
                    potentialSpend.addAll(Arrays.stream(spends.value()).toList());
                });

        AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), GenerateSpend.class)
                .ifPresent(spend -> {
                            potentialSpend.add(spend);
                        }
                );

        if (!potentialSpend.isEmpty()) {
            List<Object> created = new ArrayList<>();
            for (GenerateSpend spend : potentialSpend) {
                created.add(createSpend(spend, category));
            }
            extensionContext
                    .getStore(NAMESPACE)
                    .put(extensionContext.getUniqueId(), created);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        List<Object> spends = context.getStore(NAMESPACE).get(context.getUniqueId(), List.class);
        for (Object spend : spends) {
            removeSpend((SpendEntity) spend);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter()
                .getType();
        return type.isAssignableFrom(SpendJson.class) || type.isAssignableFrom(SpendJson[].class);
    }

    @Override

    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        CategoryEntity category = extensionContext.getStore(AbstractCategoryExtension.NAMESPACE)
                .get(extensionContext.getUniqueId(), CategoryEntity.class);

        Class <?> type =  parameterContext
                .getParameter()
                .getType();
        List <SpendJson> createdSpends = extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), List.class).stream()
                .map(spend -> SpendJson.fromEntity((SpendEntity) spend))
                .toList();

        return type.isAssignableFrom(SpendJson.class)
                ? createdSpends.getFirst()
                : createdSpends.toArray(SpendJson[]::new);
    }

    protected abstract Object createSpend(GenerateSpend spend, CategoryJson category);

    protected abstract void removeSpend(SpendEntity spend);
}
