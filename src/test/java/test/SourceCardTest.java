package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PayTypesPage;

import static com.codeborne.selenide.Selenide.open;

public class SourceCardTest {

    String approvedCardNumber = DataHelper.getCardApproved().getCardNumber();
    String declinedCardNumber = DataHelper.getCardDeclined().getCardNumber();
    String validMonth = DataHelper.getRandomMonth(1);
    String validYear = DataHelper.getRandomYear(1);
    String validOwnerName = DataHelper.getRandomName();
    String validCode = DataHelper.getNumberCVC(3);

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @AfterEach
    public void shouldCleanBase() {
        SQLHelper.сleanBase();
    }

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void shouldCreditPaymentApproved() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditCardPage.bankApprovedOperation();
        Assertions.assertEquals("APPROVED", SQLHelper.getCreditPayment());
    }

    @Test
    public void shouldDeclinedCardPayment() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(declinedCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditCardPage.bankDeclinedOperation();
        Assertions.assertEquals("Declined", SQLHelper.getCreditPayment());
    }
    @Test
    public void shouldHandleInvalidCardDetails() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var invalidCardNumber = DataHelper.GetAShortNumber();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(invalidCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditCardPage.errorFormat();
    }
    @Test
    public void shouldHandleEmptyCardNumber() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(emptyCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditCardPage.errorFormat();
    }
    @Test
    public void shouldHandleExpiredCardMonth() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var monthExpired = DataHelper.getRandomMonth(-2);
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, monthExpired, validYear, validOwnerName, validCode);
        creditCardPage.errorCardTermValidity();
    }
    @Test
    public void shouldHandleExpiredCardYear() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var expiredYear = DataHelper.getRandomYear(-5);
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, expiredYear, validOwnerName, validCode);
        creditCardPage.termValidityExpired();
    }

    @Test
    public void shouldHandleInvalidOwnerRussianName() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var rusLanguageName = DataHelper.getRandomNameRus();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, rusLanguageName, validCode);
        creditCardPage.errorFormat();
    }
    @Test
    public void shouldHandleInvalidOwnerDigitsInName() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var digitsName = DataHelper.getNumberName();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, digitsName, validCode);
        creditCardPage.errorFormat();
    }
    @Test
    public void shouldHandleInvalidOwnerSpecialSymbolsInName() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var specSymbolsName = DataHelper.getSpecialCharactersName();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, specSymbolsName, validCode);
        creditCardPage.errorFormat();
    }
    @Test
    public void shouldHandleInvalidOwnerEmptyName() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyName = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, emptyName, validCode);
        creditCardPage.emptyField();
    }

    @Test
    public void shouldHandleInvalidTwoDigitCVC() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var twoDigitCVC = DataHelper.getNumberCVC(2);
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, twoDigitCVC);
        creditCardPage.errorFormat();
}
    @Test
    public void shouldHandleInvalidOneDigitCVC() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var oneDigitCVC = DataHelper.getNumberCVC(1);
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, oneDigitCVC);
        creditCardPage.errorFormat();
    }
    @Test
    public void shouldHandleInvalidEmptyCVC() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyCVC = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, emptyCVC);
        creditCardPage.errorFormat();
    }
    @Test
    public void shouldHandleInvalidSpecialSymbolsInCVC() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var specSymbolsCVC = DataHelper.getSpecialCharactersName();
        creditCardPage.cleanFields();
        creditCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, specSymbolsCVC);
        creditCardPage.errorFormat();
    }

    @Test
    public void shouldHandleAllFieldsEmpty() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var creditCardPage = page.creditPayment();
        var emptyField = DataHelper.getEmptyField();
        creditCardPage.cleanFields();
        creditCardPage.errorFormat();
    }
}