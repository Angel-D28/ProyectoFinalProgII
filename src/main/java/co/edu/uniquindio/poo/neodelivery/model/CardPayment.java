package co.edu.uniquindio.poo.neodelivery.model;

public class CardPayment extends Payment {
    private String cardNumber;

    public CardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public void processPayment() {
        System.out.println("El payment es: " + getAmount() );
        System.out.println("Processing payment card... " );
        setStatus(StatusPayment.Approved);
    }
}
