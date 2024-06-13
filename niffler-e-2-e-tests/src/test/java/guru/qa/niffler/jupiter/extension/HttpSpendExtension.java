package guru.qa.niffler.jupiter.extension;
import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Date;

public class HttpSpendExtension extends AbstractSpendExtension{

    private final SpendApiClient spendApiClient = new SpendApiClient();

    @Override
    protected Object createSpend(GenerateSpend spend, CategoryJson category) {

        SpendJson spendJson = new SpendJson(
                null,
                new Date(),
                category.category(),
                spend.currency(),
                spend.amount(),
                spend.description(),
                category.username()
        );

        try {
            return SpendEntity.fromJson(spendApiClient.createSpend(spendJson), CategoryEntity.fromJson(category));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeSpend(SpendEntity spend) {
    }
}
