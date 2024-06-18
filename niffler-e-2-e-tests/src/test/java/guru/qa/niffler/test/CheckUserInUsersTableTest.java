package guru.qa.niffler.test;

import com.codeborne.selenide.ElementsCollection;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sessionStorage;
import static com.codeborne.selenide.Selenide.sleep;
import static guru.qa.niffler.condition.UsersCondition.usersInTable;

@ExtendWith(BrowserExtension.class)
public class CheckUserInUsersTableTest extends BaseTest{

    private MainPage mainPage;
    final String frontUrl = CFG.frontUrl();
    private final String username = "DIMA";
    private final String password = "DIMA";

    @Test
    void checkUserInUsersTableTest() throws IOException {
        mainPage = open(frontUrl, WelcomePage.class)
                .openLoginPage()
                .doLogin(username, password);
        sleep(3000);

        String tokenId = sessionStorage().getItem("id_token");
        mainPage.goToPeoplePage();

        UserJson[] userJsons = gatewayApiClient.allUsers("Bearer " + tokenId).toArray(new UserJson[0]);
        ElementsCollection users =  $("[class*=abstract-table] tbody").$$("tr");
        users.shouldHave(usersInTable(userJsons));
    }
}