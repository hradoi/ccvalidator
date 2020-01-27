public class CreditCard {
    private String number;
    private String expirationDate;

    static enum VALIDATION {
        VALID,
        NOT_VALID,
        BLACKLISTED
    }

    private CreditCard() {}

    public CreditCard(String number, String date){
        this.number = number.trim();
        this.expirationDate = date.trim();
    }

    public String getNumber() {
        return this.number;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public VALIDATION isValid(){
        return CreditCardValidator.isValid(this) ? VALIDATION.VALID :
                CreditCardValidator.isOnBlacklist(this) ? VALIDATION.BLACKLISTED :
                        VALIDATION.NOT_VALID;
    }
}
