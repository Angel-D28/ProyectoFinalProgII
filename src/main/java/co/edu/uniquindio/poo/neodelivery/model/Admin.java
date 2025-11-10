package co.edu.uniquindio.poo.neodelivery.model;

public class Admin implements Observer{
    private String idAdmin;
    private String name;
    private String email;
    private String number;

    public Admin(String idAdmin, String name, String email, String number) {
        this.idAdmin = idAdmin;
        this.name = name;
        this.email = email;
        this.number = number;
    }
    @Override
    public void update(String message) {
        System.out.println("[Admin " + name + "] Notificación: " + message);

        //SE UTILIZARIA EL METODO DE LA ALERTA DE JAVA FX
    }

    // Se delega a los "Gestores" para cumplir SOLID

    public void gestionarUsuarios() {
        System.out.println("Accediendo a gestión de usuarios...");
    }

    public void gestionarRepartidores() {
        System.out.println("Accediendo a gestión de repartidores...");
    }

    public void gestionarEnvios() {
        System.out.println("Accediendo a gestión de envíos...");
    }

    public void generarReportes() {
        System.out.println("Generando reportes del sistema...");
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "idAdmin: " + idAdmin + "Nombre: " + name + "Email: " + email +
                "Numero: " + number;
    }
}
