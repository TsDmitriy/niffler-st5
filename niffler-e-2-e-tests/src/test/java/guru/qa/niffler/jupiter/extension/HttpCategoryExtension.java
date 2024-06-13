package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class HttpCategoryExtension extends AbstractCategoryExtension {

    private final SpendApiClient spendApiClient = new SpendApiClient();

    @Override
    protected Object createCategory(GenerateCategory category) {

        CategoryJson categoryJson = new CategoryJson(
                null,
                category.category(),
                category.username()
        );

        try {
            return CategoryEntity.fromJson(spendApiClient.addCategory(categoryJson));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeCategory(CategoryEntity category) {

    }
}
