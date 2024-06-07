package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.jupiter.extension.DbCreateUserExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;
@ExtendWith(DbCreateUserExtension.class)
public class LoginTest extends BaseTest{

    @Test
    @TestUser
    void login (UserJson userJson) throws InterruptedException {
        open(CFG.frontUrl(), WelcomePage.class)
                .openLoginPage()
                .doLogin( userJson.username(), userJson.testData().password());
        System.out.println("Логин юзера " + userJson.username() + "Пароль " + userJson.testData().password());
    }
}
