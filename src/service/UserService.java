package service;

import Stores.UserStore;
import models.Usuario;

import java.util.*;

public class UserService {
    private final UserStore userStore;

    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    // Metodo que realiza o cadastro de um usuário no sistema
    public Usuario singUpUser(String login, String senha, String nome, String CPF, String email, boolean isAdmin) {
        Usuario user = new Usuario(login, senha, nome, CPF, email, isAdmin);
        if(userStore.getByLogin(user.getLogin()) == null) {
            userStore.add(user);
            return user;
        } else{
            throw new SecurityException("Login, email e/ou cpf já está em uso.");
        }
    }

    // Metodo que realiza o login de um usuário no sistema
    public Usuario singInUser(String login, String senha){
        Usuario user = userStore.getByLogin(login);
        if(user.getSenha().equals(senha)){
            return user;
        }
        else{
            return null;
        }
    }

    public Usuario searchUser(String login) {
        return userStore.getByLogin(login);
    }

    public Usuario editUser(Usuario user, String name, String email, String password, String login){
        String nameAux, emailAux, passwordAux, loginAux;
        if(!Objects.equals(name, user.getNome())){ nameAux = name; } else { nameAux = user.getNome(); }
        if(!Objects.equals(email, user.getEmail())){ emailAux = email; } else { emailAux = user.getEmail(); }
        if(!Objects.equals(password, user.getSenha())){ passwordAux = password; } else { passwordAux = user.getSenha(); }
        if(!Objects.equals(login, user.getLogin())){ loginAux = login; } else { loginAux = user.getLogin(); }

        return userStore.updateUser(user, nameAux, emailAux, passwordAux, loginAux);
    }
}
