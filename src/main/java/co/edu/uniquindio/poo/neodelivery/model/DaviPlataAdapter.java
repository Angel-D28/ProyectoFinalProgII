package co.edu.uniquindio.poo.neodelivery.model;

public class DaviPlataAdapter implements IPaymentProcessor{

    private ExternalAPIs.ExternalDaviPlataAPI daviPlataAPI = new ExternalAPIs.ExternalDaviPlataAPI();

    @Override
    public boolean process(double amount) {
        System.out.println("Adaptando pago a daviplata API....");
        return daviPlataAPI.sendPayment(amount);
    }
}
