public class CreditCard {
    private String number;
    private String expirationDate;
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

    public boolean isValid(){
        return CreditCardValidator.isValid(this);
    }


}
