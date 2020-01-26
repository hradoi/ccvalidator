import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {
    @Test
    void createCreditCard() {
        CreditCard cc = new CreditCard("0000 0000 0000 0000", "01/70");
        assertEquals(CreditCardValidator.isValid(cc), false);
    }

    @Test
    void cardThatHas16digits() {
        CreditCard cc = new CreditCard("0000 0000 0000 0000", "01/70");
        assertEquals(CreditCardValidator.has16Digits(cc), true);
    }

    @Test
    void cardThatHas16digitsNoSpaces() {
        CreditCard cc = new CreditCard("0000000000000000", "01/70");
        assertEquals(CreditCardValidator.has16Digits(cc), true);
    }


}
