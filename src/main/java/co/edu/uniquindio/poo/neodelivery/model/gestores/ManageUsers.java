package co.edu.uniquindio.poo.neodelivery.model.gestores;

import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.User;

import java.util.List;

public class ManageUsers {
    private DataBase db = DataBase.getInstance();
    private int userId;

    public User findUser(String findId){
        User user = null;
        for(User userAux : db.getListaUsuarios()){
            if(userAux.getIdUser().equals(findId)){
                user = userAux;
            }
        }
        return user;
    }

    public String showUsers() {
        String users = "";
        for (User user : db.getListaUsuarios()) {
            users += user.toString() + "\n";
        }
        return users;
    }

    public String generateId(){
        return String.valueOf(userId++);
    }


    public void createUser(User user) {
        db.getListaUsuarios().add(user);
    }

    public void deleteUser(User user) {
        db.getListaUsuarios().remove(user);
    }
    public void updateUser(String idUser, User userUpdated) {
        User userToUpdate = findUser(idUser);
        userToUpdate.setName(userUpdated.getName());
        userToUpdate.setNumbre(userUpdated.getNumbre());
        userToUpdate.setEmail(userUpdated.getEmail());
        userToUpdate.setAddress(userUpdated.getAddress());
    }

    public List<User> getAllUsers() {
        return db.getListaUsuarios();
    }


}
