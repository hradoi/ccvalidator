public class CreditCard {
    private String number;
    private String expirationDate;
    private CreditCard() {}

    public CreditCard(String number, String date){
        this.number = number;
        this.expirationDate = data;
    }

    @Override
    public String toString(){
        return number + expirationDate;
    }
}
