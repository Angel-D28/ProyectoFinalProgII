package co.edu.uniquindio.poo.neodelivery.model;

public class CardPayment extends Payment {
    private String cardNumber;

    public CardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public void processPayment() {
        if (cardNumber == null || cardNumber.length() < 8) {
            status = StatusPayment.Rejected;
            System.out.println("Tarjeta inválida");
            return;
        }

        status = StatusPayment.Approved;
        System.out.println("Pago con tarjeta aprobado");
    }
}
