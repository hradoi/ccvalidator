import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {
    @Test
    void checkCard() {
        CreditCard cc = new CreditCard("4510 8166 0257 3952", "06/20");
        assertTrue(CreditCardValidator.isValid(cc));
    }

    @Test
    void createEmptyCard() {
        CreditCard cc = new CreditCard("", "");
        assertFalse(CreditCardValidator.isValid(cc));
    }

    @Test
    void cardThatHas16digits() {
        CreditCard cc = new CreditCard("0000 0000 0000 0000", "01/70");
        assertTrue(CreditCardValidator.has16Digits(cc));
    }

    @Test
    void cardThatHas16digitsNoSpaces() {
        CreditCard cc = new CreditCard("0000000000000000", "01/70");
        assertTrue(CreditCardValidator.has16Digits(cc));
    }

    @Test
    void cardThatHas16DigitsButSpacesAroundIt() {
        CreditCard cc = new CreditCard("    0000000000000000    ", "01/70");
        assertTrue(CreditCardValidator.has16Digits(cc));
    }

    @Test
    void cardThatHasLessDigits() {
        CreditCard cc = new CreditCard("123456789", "01/70");
        assertFalse(CreditCardValidator.has16Digits(cc));
    }

    @Test
    void cardThatHasTooManyDigits() {
        CreditCard cc = new CreditCard("01234567890123456789", "01/70");
        assertFalse(CreditCardValidator.has16Digits(cc));
    }

    @Test
    void cardThatHasIllegalCharacters() {
        CreditCard cc = new CreditCard("0123456789ABCDEF", "01/70");
        assertFalse(CreditCardValidator.has16Digits(cc));
    }

    @Test
    void cardThatHas16CharactersButWeirdSpaces() {
        CreditCard cc = new CreditCard("000 000 000 000 000 0", "01/70");
        assertFalse(CreditCardValidator.has16Digits(cc));
    }

    @Test
    void cardFromVisa() {
        CreditCard cc = new CreditCard("4000 0000 0000 0000", "01/70");
        assertTrue(CreditCardValidator.isVisa(cc));
    }

    @Test
    void assertWrongFirstDigitVisa() {
        assertFalse(CreditCardValidator.isVisa(new CreditCard("0000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isVisa(new CreditCard("1000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isVisa(new CreditCard("2000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isVisa(new CreditCard("3000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isVisa(new CreditCard("5000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isVisa(new CreditCard("6000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isVisa(new CreditCard("7000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isVisa(new CreditCard("8000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isVisa(new CreditCard("9000 0000 0000 0000", "01/70")));
    }

    @Test
    void forEverythingElseThereIsMastercard() {
        assertTrue(CreditCardValidator.isMastercard(new CreditCard("5100 0000 0000 0000", "01/70")));
        assertTrue(CreditCardValidator.isMastercard(new CreditCard("5200 0000 0000 0000", "01/70")));
        assertTrue(CreditCardValidator.isMastercard(new CreditCard("5300 0000 0000 0000", "01/70")));
        assertTrue(CreditCardValidator.isMastercard(new CreditCard("5400 0000 0000 0000", "01/70")));
        assertTrue(CreditCardValidator.isMastercard(new CreditCard("5500 0000 0000 0000", "01/70")));
    }

    @Test
    void assertWrongInputMastercard() {
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("1000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("2000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("3000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("4000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("5000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("5600 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("5700 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("5800 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("5900 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("6000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("7000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("8000 0000 0000 0000", "01/70")));
        assertFalse(CreditCardValidator.isMastercard(new CreditCard("9000 0000 0000 0000", "01/70")));
    }

    @Test
    void assertLuhnFormula() {
        assertEquals(CreditCardValidator.computeLuhnDigit("0000 0000 0000 0000"), 0);
        assertEquals(CreditCardValidator.computeLuhnDigit("4556 7375 8689 9855"), 5);
        assertEquals(CreditCardValidator.computeLuhnDigit("1234 5678 9012 3450"), 2);
        assertEquals(CreditCardValidator.computeLuhnDigit("1234 1234 1234 1230"), 8);
        assertEquals(CreditCardValidator.computeLuhnDigit("9999 9999 9999 9990"), 5);
        assertEquals(CreditCardValidator.computeLuhnDigit("5513 7427 5112 3840"), 1);

        assertEquals(CreditCardValidator.computeLuhnDigit("1234"), -1);
    }

    @Test
    void checkLuhnFormula() {
        assertTrue(CreditCardValidator.passesLuhnTest(new CreditCard("0000 0000 0000 0000", "01/70")));
        assertTrue(CreditCardValidator.passesLuhnTest(new CreditCard("4556 7375 8689 9855", "01/70")));
        assertTrue(CreditCardValidator.passesLuhnTest(new CreditCard("4916 5792 5874 4950", "01/70")));
        assertTrue(CreditCardValidator.passesLuhnTest(new CreditCard("4539 1782 8678 5007", "01/70")));
        assertTrue(CreditCardValidator.passesLuhnTest(new CreditCard("5513 7427 5112 3841", "01/70")));
        assertTrue(CreditCardValidator.passesLuhnTest(new CreditCard("5362 6692 4263 7752", "01/70")));
    }

    @Test
    void checkCorrectDateFormat() {
        assertTrue(CreditCardValidator.isCorrectDateFormat("01/70"));
        // OK to be true here
        assertTrue(CreditCardValidator.isCorrectDateFormat("70/01"));
        assertTrue(CreditCardValidator.isCorrectDateFormat("00/00"));
        assertTrue(CreditCardValidator.isCorrectDateFormat("20/20"));

        assertFalse(CreditCardValidator.isCorrectDateFormat("01/01/70"));
        assertFalse(CreditCardValidator.isCorrectDateFormat("01-70"));
        assertFalse(CreditCardValidator.isCorrectDateFormat("JAN/70"));
        assertFalse(CreditCardValidator.isCorrectDateFormat("01/SEVENTY"));
        assertFalse(CreditCardValidator.isCorrectDateFormat("01 70"));
    }

    @Test
    void checkDateInFutureAndCorrectAssumingRightFormat() {
        assertTrue(CreditCardValidator.isValidDate(new CreditCard("", "01/99")));
        assertTrue(CreditCardValidator.isValidDate(new CreditCard("","06/20")));
        assertTrue(CreditCardValidator.isValidDate(new CreditCard("","01/20")));
        assertTrue(CreditCardValidator.isValidDate(new CreditCard("","02/20")));

        assertFalse(CreditCardValidator.isValidDate(new CreditCard("","13/99")));
        assertFalse(CreditCardValidator.isValidDate(new CreditCard("","05/12")));
    }

    @Test
    void checkBlacklist() {
        assertTrue(CreditCardValidator.isOnBlacklist(new CreditCard("4788384538552446", "01/70")));
        assertTrue(CreditCardValidator.isOnBlacklist(new CreditCard("5144 3854 3852 3845", "01/70")));

        assertFalse(CreditCardValidator.isOnBlacklist(new CreditCard("4510 8166 0257 3952", "01/21")));
    }
}