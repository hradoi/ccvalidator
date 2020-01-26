import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CreditCardValidator {

    public static final String SPACES_4GROUPS_4DIGITS = "(([0-9]{4}){3})([0-9]{4})";
    public static final String NOSPACES_16DIGITS = "[0-9]{16}";

    private CreditCardValidator() {}

    public static boolean isValid(CreditCard card) {
        return has16Digits(card);
    }

    static boolean has16Digits(CreditCard cc) {
        String cardName = cc.getNumber();
        Matcher spacesPatternMatcher = Pattern
                                    .compile(SPACES_4GROUPS_4DIGITS)
                                    .matcher(cardName);
        Matcher noSpacesPatternMatcher = Pattern
                                    .compile(NOSPACES_16DIGITS)
                                    .matcher(cardName);
        return spacesPatternMatcher.matches() || noSpacesPatternMatcher.matches();
    }


}
