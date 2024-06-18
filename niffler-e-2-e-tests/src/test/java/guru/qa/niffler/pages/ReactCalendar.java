package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.SetValueOptions.withDate;

public class ReactCalendar extends BaseComponent<ReactCalendar> {
    protected ReactCalendar(SelenideElement self) {
        super(self);
    }

    protected ReactCalendar() {
        super($(".calendar-wrapper"));
    }

    public ReactCalendar setData(String date) {
        self.$("[class*='react-datepicker__input-'] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        self.$("[class*='react-datepicker__input-'] input").setValue(date);
        return this;
    }
}
