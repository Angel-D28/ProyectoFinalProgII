package co.edu.uniquindio.poo.neodelivery.model;

public class ExternalAPIs {

    public static class ExternalNequiAPI{
        public boolean sendPayment (double value){
            String mensaje = "Nequi API Pago Enviado: $" +value;
            return true;
        }
    }

    public static class ExternalDaviPlataAPI{
        public boolean sendPayment (double value){
            String mensaje = "DaviPlata API Pago Enviado: $" +value;
            return true;
        }
    }
}
