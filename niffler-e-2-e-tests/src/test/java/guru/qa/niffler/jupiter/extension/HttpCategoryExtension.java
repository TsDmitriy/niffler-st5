package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApi;
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

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    @Override
    protected <T> Object createCategory(GenerateCategory category) {
        SpendApi spendApi = retrofit.create(SpendApi.class);


        CategoryJson categoryJson = new CategoryJson(
                null,
                category.category(),
                category.username()
        );

        try {
            return CategoryEntity.fromJson(spendApi.addCategory(categoryJson).execute().body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeCategory(CategoryEntity category) {

    }
}
