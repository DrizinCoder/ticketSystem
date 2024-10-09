package Stores;

import Interfaces.Store;
import Resources.FilePaths;
import com.google.gson.Gson;
import models.Usuario;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;

public class UserStore implements Store<Usuario> {
    private List<Usuario> users;

    public UserStore() {
        users = new ArrayList<>();
    }

    @Override
    public void add(Usuario  user){
        users.add(user);
        serializer();
    }

    @Override
    public void remove(Usuario user) {
        users.remove(user);
        serializer();
    }

    @Override
    public Usuario getByID(UUID ID) {
        for(Usuario user : users) {
            if(user.getID().equals(ID)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> get() {
        return users;
    }

    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.USER_JSON)) {
            Type userListType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            users = gson.fromJson(reader, userListType);
            if (users == null) {
                // Caso o arquivo esteja vazio
                users = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Caso o arquivo não exista ou ocorra erro
            users = new ArrayList<>();
        }
    }

    @Override
    public void serializer() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FilePaths.USER_JSON)) {
            // Gravar a lista no arquivo JSON
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Busca usuário a partir do login
    public Usuario getByLogin(String login) {
        for(Usuario user : users) {
            if(user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    // Atualiza usuário no sistema
    public Usuario updateUser(Usuario user, String nome, String email, String senha, String login) {
        Usuario userAux = getByLogin(user.getLogin());
        if(userAux != null) {
            userAux.setNome(nome);
            userAux.setEmail(email);
            userAux.setSenha(senha);
            userAux.setLogin(login);
        }
        serializer();
        return user;
    }

    public void updateMailBox(String mailMensage, UUID ID){
        Usuario user = getByID(ID);
        if(user != null) {
            user.setMailBox(mailMensage);
        }
        serializer();
    }

}
