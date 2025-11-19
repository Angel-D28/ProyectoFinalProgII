package co.edu.uniquindio.poo.neodelivery.model;

public class DigitalWalletPayment extends Payment {
    private String walletProvider;
    private String numberWallet;
    private IPaymentProcessor adapter;

    public DigitalWalletPayment() {
        this.paymentMethodName = "Digital Wallet";
    }

    public DigitalWalletPayment(double amount, String walletProvider, String numberWallet) {
        super(amount);
        this.walletProvider = walletProvider;
        this.numberWallet = numberWallet;
        this.status = StatusPayment.Pending;
        this.paymentMethodName = walletProvider;

        if (walletProvider.equalsIgnoreCase("Nequi")) {
            this.adapter = new NequiAdapter();
        } else if (walletProvider.equalsIgnoreCase("Daviplata")) {
            this.adapter = new DaviPlataAdapter();
        } else {
            throw new IllegalArgumentException("Unsupported wallet provider: " + walletProvider);
        }
    }

    @Override
    public void processPayment() {
        System.out.println("Procesando pago con billetera: " + walletProvider + "...");

        boolean success = adapter.process(amount);

        if (success) {
            this.status = StatusPayment.Approved;
            System.out.println("Pago aprobado con " + walletProvider);
        } else {
            this.status = StatusPayment.Rejected;
            System.out.println("Pago rechazado con " + walletProvider);
        }
    }

    @Override
    public String toString() {
        return "DigitalWalletPayment{" +
                "walletProvider='" + walletProvider + '\'' +
                ", numberWallet='" + numberWallet + '\'' +
                ", status=" + status +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                '}';
    }

    public String getWalletProvider() {
        return walletProvider;
    }

    public String getNumberWallet() {
        return numberWallet;
    }
}
