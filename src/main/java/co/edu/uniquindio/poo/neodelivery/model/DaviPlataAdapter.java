package co.edu.uniquindio.poo.neodelivery.model;

public class DaviPlataAdapter implements IPaymentProcessor{

    private ExternalAPIs.ExternalDaviPlataAPI daviPlataAPI = new ExternalAPIs.ExternalDaviPlataAPI();

    @Override
    public boolean authenticate(String number, String password) {
        System.out.println("Autenticando con Daviplata...");
        return number.matches("\\d{10}") && password.matches("\\d{4}");
    }

    @Override
    public boolean process(double amount) {
        System.out.println("Adaptando pago a daviplata API....");
        return daviPlataAPI.sendPayment(amount);
    }
}
