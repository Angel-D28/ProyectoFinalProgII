package co.edu.uniquindio.poo.neodelivery.model;

public class User {
    private String name;
    private String password;
    private String email;
    private Address address;
    private String numbre;
    private String idUser;

    public User(String name, String password, String email, Address address, String numbre, String idUser) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.numbre = numbre;
        this.idUser = idUser;
    }

    public String getName() {
        return name;
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
        return numbre;
    }

    public void setNumbre(String numbre) {
        this.numbre = numbre;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
