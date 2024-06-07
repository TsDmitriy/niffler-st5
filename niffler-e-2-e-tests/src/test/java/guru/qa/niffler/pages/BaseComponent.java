package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;

public  abstract class BaseComponent<T extends BaseComponent<?>> {
    protected final SelenideElement self;

    protected BaseComponent(SelenideElement self) {
        this.self = self;
    }
}