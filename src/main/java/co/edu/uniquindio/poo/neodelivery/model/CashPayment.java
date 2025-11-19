package co.edu.uniquindio.poo.neodelivery.model;

public class CashPayment extends Payment {
    private String referenceCode; // Código de referencia Efecty

    public CashPayment(double amount) {
        super(amount);
        this.referenceCode = generateReference();
        this.status = StatusPayment.Pending; // El pago no se aprueba automáticamente
    }

    private String generateReference() {
        return "EF-" + (int)(Math.random() * 1000000); // Ej: EF-482193
    }

    @Override
    public void processPayment() {
        System.out.println("Generando recibo Efecty...");
        status = StatusPayment.Pending;
        System.out.println("Pago generado con Efecty. Código de referencia: " + referenceCode);
        System.out.println("El pago quedará en estado PENDIENTE hasta que sea confirmado en Efecty.");
    }

    // Método para confirmar el pago (por el admin o el sistema)
    public void confirmPayment() {
        this.status = StatusPayment.Approved;
        System.out.println("Pago Efecty confirmado con referencia: " + referenceCode);
    }

    public String getReferenceCode() {
        return referenceCode;
    }
}
