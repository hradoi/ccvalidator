import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {
    @Test
    void createCreditCard() {
        CreditCard cc = new CreditCard("0000 0000 0000 0000", "01/70");
        assertEquals(cc.isValid(), false);
    }



}
