package page;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class PayTypesPage {

    private SelenideElement heading = $(withText("Путешествие дня"));
    private SelenideElement buyButton = $(withText("Купить"));
    private SelenideElement creditButton = $(withText("Купить в кредит"));

    public void paymentTypesPage() {
        heading.shouldBe(visible);

    }
    public SourceCardPage cardPayment() {
        buyButton.click();
        return new SourceCardPage();
    }

    public RecipientCardPage creditPayment() {
        creditButton.click();
        return new RecipientCardPage();
    }
}