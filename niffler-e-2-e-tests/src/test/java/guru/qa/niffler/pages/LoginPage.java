package guru.qa.niffler.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverConditions.url;

public class LoginPage extends BasePage<LoginPage>{

    public final String url = "http://127.0.0.1:9000/login";
    private final SelenideElement username = $("input[name='username']").as("Логин");
    private final SelenideElement password = $("input[name='password']").as("Пароль");
    private final SelenideElement submitBtn = $("button[type='submit']").as("Кнопка 'Войти'");

    @Override
    public LoginPage waitForPageLoaded() {
        username.shouldBe(visible);
        return null;
    }

    @Override
    public LoginPage checkPage() {
        Selenide.webdriver()
                .shouldHave(url(url));
        return this;
    }
    public MainPage doLogin() {
        username.setValue("dotsarev");
        password.setValue("dotsarev");
        submitBtn.click();
        return page(MainPage.class);
    }
}