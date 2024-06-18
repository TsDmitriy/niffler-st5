package guru.qa.niffler.condition;

import com.codeborne.selenide.WebElementsCondition;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;

public class UsersCondition {
    public static WebElementsCondition usersInTable(UserJson... userJsons) {
        return new UsersInTableCondition(userJsons);
    }
}
