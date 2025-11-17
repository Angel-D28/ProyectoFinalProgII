package co.edu.uniquindio.poo.neodelivery.model;

public class User implements Observer {
    private String name;
    private String password;
    private String email;
    private Address address;
    private String number;
    private String idUser;

    public User(String name, String password, String email, Address address, String numbre, String idUser) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.number = numbre;
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(String message) {
        System.out.println("Enviando gmail en tiempo real");
        EmailService.sendEmail(email,"Actualizacion de Envio", message);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNumbre() {
        return number;
    }

    public void setNumbre(String numbre) {
        this.number = numbre;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String toString() {
        return "Nombre:" + name + "Contraseña:" + password + "Email:" + email + "Direccion:" + address + "Número:" + number + "IdUsuario:" + idUser;
    }
}
