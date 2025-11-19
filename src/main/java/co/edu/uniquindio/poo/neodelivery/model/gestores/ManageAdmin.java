package co.edu.uniquindio.poo.neodelivery.model.gestores;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.User;

import java.util.List;

public class ManageAdmin {
    private DataBase db = DataBase.getInstance();

    public Admin findAdmin(String adminId) {
        Admin found = null;
        for (Admin admin : db.getListaAdmin()) {
            if (admin.getIdAdmin().equals(adminId)) {
                found = admin;
            }
        }
        return found;
    }

    public String showAdmins() {
        String admins = "";
        for (Admin admin : db.getListaAdmin()) {
            admins += admin.toString() + "\n";
        }
        return admins;
    }

    public String generateId(){
        int maxId = 0;

        for (Admin admin : db.getListaAdmin()) {
            try {
                int id = Integer.parseInt(admin.getIdAdmin());
                if (id > maxId) maxId = id;
            } catch (NumberFormatException e) {

            }
        }

        return String.valueOf(maxId + 1);
    }

    public void createAdmin(Admin admin) {
        db.getListaAdmin().add(admin);
    }

    public void deleteAdmin(Admin admin) {
        db.getListaAdmin().remove(admin);
    }

    public void updateAdmin(String adminId, Admin adminUpdated) {
        Admin adminToUpdate = findAdmin(adminId);
        if (adminToUpdate != null) {
            adminToUpdate.setName(adminUpdated.getName());
            adminToUpdate.setEmail(adminUpdated.getEmail());
            adminToUpdate.setNumber(adminUpdated.getNumber());
        }
    }

    public List<Admin> getAllAdmins() {
        return db.getListaAdmin();
    }
}
