import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CreditCardValidator {

    private static final String SPACES_4GROUPS_4DIGITS = "([0-9]{4} ){3}[0-9]{4}";
    private static final String NOSPACES_16DIGITS = "[0-9]{16}";
    private static final String DATE_FORMAT = "[0-9]{2}/[0-9]{2}";
    private static final Integer CARD_LENGTH = 16;

    private CreditCardValidator() {}

    public static boolean isValid(CreditCard card) {
        return has16Digits(card)
                && (isVisa(card) || isMastercard(card))
                && passesLuhnTest(card);
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


    public static boolean isVisa(CreditCard cc) {
        return cc.getNumber().startsWith("4");
    }

    public static boolean isMastercard(CreditCard creditCard) {
        String firstTwoDigits = creditCard.getNumber().substring(0,2);
        int checkPrefix = Integer.parseInt(firstTwoDigits);
        return (50 < checkPrefix && checkPrefix < 56);
    }

    // used information at https://www.freeformatter.com/credit-card-number-generator-validator.html
    public static Integer computeLuhnDigit(String number) {
        String noSpacesNumber = number.replace(" ", "");
        int len = noSpacesNumber.length();

        if (!CARD_LENGTH.equals(noSpacesNumber.length())) {
            return -1;
        }

        // drop last digit
        char[] x = noSpacesNumber.substring(0, len - 1).toCharArray();

        int newLen = len - 1;
        //reverse
        for (int i = 0; i < (newLen + 1)/2; i++) {
            char tmp = x[i];
            x[i] = x[newLen - i - 1];
            x[newLen - i - 1] = tmp;
        }

        //compute sum
        int sum = 0;
        for (int i = 0; i < newLen; i++) {
            int currentDigit = (int)(x[i] - '0');
            if (i % 2 == 0) {
                currentDigit *= 2;
                // odd position in number = even in string annotation.
                sum += 9 < currentDigit  ? currentDigit - 9 : currentDigit;
            } else {
                sum += currentDigit;
            }
        }
        return (10 - (sum % 10)) % 10;
    }

    public static boolean passesLuhnTest(CreditCard cc) {
        String number = cc.getNumber();
        return computeLuhnDigit(number)
                .equals(Integer
                        .parseInt(
                                number.substring(number.length() - 1)));
    }

    public static boolean isCorrectDateFormat(String date) {
        Matcher datePatternMatcher = Pattern
                .compile(NOSPACES_16DIGITS)
                .matcher(date);
        return datePatternMatcher.matches();
    }

    public static boolean isValidExpirationDate(String date){

    }
}
