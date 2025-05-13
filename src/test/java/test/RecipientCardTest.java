package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PayTypesPage;

import static com.codeborne.selenide.Selenide.open;

public class RecipientCardTest {

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
    public void shouldCardPaymentApproved() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.bankApprovedOperation();
        Assertions.assertEquals("APPROVED", SQLHelper.getCardPayment());
    }

    @Test
    public void shouldDeclinedCardPayment() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(declinedCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.bankDeclinedOperation();
        Assertions.assertEquals("DECLINED", SQLHelper.getCardPayment());
    }


    @Test
    public void invalidCardNumber() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var invalidCardNumber = DataHelper.GetAShortNumber();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(invalidCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
        }

    @Test
    public void invalidCardNumberZero() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var invalidCardNumber = DataHelper.getZeroNumber();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(invalidCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();

    }

    @Test
    public void invalidCardNumberEmpty() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var invalidCardNumber = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(invalidCardNumber, validMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void invalidCardMonth() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var invalidMonth = DataHelper.getInvalidMonth();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, invalidMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void invalidEmptyMonth() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var invalidMonth = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, invalidMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }
    @Test
    public void invalidOneDigitMonth() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var invalidMonth = DataHelper.getInvalidMonth();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, invalidMonth, validYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }
    @Test
    public void invalidExpiredCardYear() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var expiredYear = DataHelper.getRandomYear(-5);
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, expiredYear, validOwnerName, validCode);
        debitCardPage.termValidityExpired();
    }
    @Test
    public void invalidEmptyCardYear() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var emptyYear = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, emptyYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }
    @Test
    public void invalidOneDigitYear() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var oneDigitYear = DataHelper.getOneDigit();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, oneDigitYear, validOwnerName, validCode);
        debitCardPage.errorFormat();
    }
    @Test
    public void invalidOwnerEmptyName() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var emptyName = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, emptyName, validCode);
        debitCardPage.emptyField();
    }


    @Test
    public void invalidOwnerRussianName() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var rusLanguageName = DataHelper.getRandomNameRus();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, rusLanguageName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void invalidOwnerDigitsInName() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var numberName = DataHelper.getNumberName();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, numberName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void invalidOwnerSpecialSymbolsInName() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var specialCharactersName = DataHelper.getSpecialCharactersName();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, specialCharactersName, validCode);
        debitCardPage.errorFormat();
    }

    @Test
    public void invalidEmptyCVC() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var EmptyCVC = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, EmptyCVC);
        debitCardPage.errorFormat();
    }

    @Test
    public void invalidTwoDigitCVC() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var twoDigitCVC = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, twoDigitCVC);
        debitCardPage.errorFormat();
    }
    @Test
    public void invalidOneDigitCVC() {
        PayTypesPage page = new PayTypesPage();
        page.paymentTypesPage();
        var debitCardPage = page.cardPayment();
        var oneDigitCVC = DataHelper.getEmptyField();
        debitCardPage.cleanFields();
        debitCardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, oneDigitCVC);
        debitCardPage.errorFormat();
    }

}
