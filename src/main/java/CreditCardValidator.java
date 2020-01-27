import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import com.google.common.collect.ImmutableList;

public class CreditCardValidator {

    private static final String SPACES_4GROUPS_4DIGITS = "([0-9]{4} ){3}[0-9]{4}";
    private static final String NOSPACES_16DIGITS = "[0-9]{16}";
    private static final String DATE_FORMAT = "[0-9]{2}/[0-9]{2}";
    private static final Integer CARD_LENGTH = 16;
    // this can be extended with a database request allowing for future entries
    private static final ImmutableList<String> BLACKLIST =
                                    ImmutableList.of("4788384538552446",
                                                "5144385438523845");
    private CreditCardValidator() {}

    public static boolean isValid(CreditCard cc) {
        return has16Digits(cc)
                && (isVisa(cc) || isMastercard(cc))
                && passesLuhnTest(cc)
                && isValidDate(cc)
                && !isOnBlacklist(cc);
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

    public static boolean isMastercard(CreditCard cc) {
        String firstTwoDigits = cc.getNumber().substring(0,2);
        int checkPrefix = Integer.parseInt(firstTwoDigits);
        return (50 < checkPrefix && checkPrefix < 56);
    }

    // used information at https://www.freeformatter.com/credit-card-number-generator-validator.html
    static Integer computeLuhnDigit(String number) {
        String noSpacesNumber = number.replace(" ", "");
        int len = noSpacesNumber.length();

        if (!CARD_LENGTH.equals(noSpacesNumber.length())) {
            return -1;
        }

        // drop last digit
        char[] x = noSpacesNumber.substring(0, len - 1).toCharArray();

        int newLen = len - 1;
        //reverse
        for (int i = 0; i < newLen/2; i++) {
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

        int sumMod10 = sum % 10;
        return sumMod10 == 0 ? 0 : 10 - sumMod10;
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
                .compile(DATE_FORMAT)
                .matcher(date);
        return datePatternMatcher.matches();
    }


    public static boolean isValidDate(CreditCard cc) {
        String date = cc.getExpirationDate();
        if (!isCorrectDateFormat(date)) {
            return false;
        }
        String[] params = date.split("/");

        LocalDate now = LocalDate.now();
        try {
            LocalDate then = LocalDate.of(
                    Integer.parseInt("20" + params[1]),
                    Integer.parseInt(params[0]),
                    1);
            then = then.plusMonths(1); // to get last day of month
            return then.isAfter(now); // because isAfter is strict checking
        }
        catch (DateTimeException e) {
            return false; // usually when people are cheeky and put in 13 as a month :)
        }
    }

    public static boolean isOnBlacklist(CreditCard cc) {
        String number = cc.getNumber().replace(" ", "");
        return BLACKLIST.contains(number);
    }
}
