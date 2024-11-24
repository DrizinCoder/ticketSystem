package com.pbl.Facade;

import com.pbl.Stores.*;
import com.pbl.controller.ClientController;
import com.pbl.controller.mainController;
import com.pbl.models.Usuario;
import com.pbl.service.*;

import java.util.UUID;

/**
 * Facade para testes de funcionalidades relacionadas ao usuário no sistema.
 * Fornece métodos para criar, editar, recuperar e deletar usuários de maneira simplificada.
 */
public class UserTestFacade {

    protected UserService userService;
    private final mainController controller;
    private final ClientController clientController;

    /**
     * Constrói uma nova instância de UserTestFacade, inicializando os serviços e controladores necessários.
     */
    public UserTestFacade() {
        UserStore userStore = new UserStore();
        this.userService = new UserService(userStore);
        this.controller = new mainController(null, null, userService, null, null, null);
        this.clientController = new ClientController(null, null, null, null, null, userService);
    }

    /**
     * Cria um novo usuário.
     *
     * @param login O login do usuário.
     * @param password A senha do usuário.
     * @param name O nome do usuário.
     * @param cpf O CPF do usuário.
     * @param email O e-mail do usuário.
     * @param isAdmin Indica se o usuário é administrador.
     * @return true se o usuário foi criado com sucesso, false caso contrário.
     */
    public boolean create(String login, String password, String name, String cpf, String email, Boolean isAdmin) {
        Usuario user = controller.signUp(login, password, name, cpf, email, isAdmin);
        return user != null;
    }

    /**
     * Recupera o login de um usuário com base em seu e-mail.
     *
     * @param email O e-mail do usuário.
     * @return O login do usuário.
     */
    public String getLoginByUserEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        return user.getLogin();
    }

    /**
     * Recupera o nome de um usuário com base em seu e-mail.
     *
     * @param email O e-mail do usuário.
     * @return O nome do usuário.
     */
    public String getNameByUserEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        return user.getNome();
    }

    /**
     * Recupera o e-mail de um usuário com base em seu e-mail.
     *
     * @param email O e-mail do usuário.
     * @return O e-mail do usuário.
     */
    public String getEmailByUserEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        return user.getEmail();
    }

    /**
     * Recupera a senha de um usuário com base em seu e-mail.
     *
     * @param email O e-mail do usuário.
     * @return A senha do usuário.
     */
    public String getPasswordByUserEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        return user.getSenha();
    }

    /**
     * Recupera o CPF de um usuário com base em seu e-mail.
     *
     * @param email O e-mail do usuário.
     * @return O CPF do usuário.
     */
    public String getCpfByUserEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        return user.getCpf();
    }

    /**
     * Verifica se um usuário é administrador com base em seu e-mail.
     *
     * @param email O e-mail do usuário.
     * @return true se o usuário é administrador, false caso contrário.
     */
    public boolean getIsAdminByUserEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        return user.isAdmin();
    }

    /**
     * Verifica se existe um usuário com o e-mail especificado.
     *
     * @param email O e-mail do usuário.
     * @return true se o usuário existir, false caso contrário.
     */
    public boolean thereIsUserWithEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        return user != null;
    }

    /**
     * Retorna o número total de usuários registrados.
     *
     * @return O número de usuários.
     */
    public int getSizeUsers() {
        return controller.getUsers().size();
    }

    /**
     * Define um novo nome para o usuário com o e-mail especificado.
     *
     * @param newName O novo nome do usuário.
     * @param email O e-mail do usuário.
     */
    public void setNameByUserEmail(String newName, String email) {
        Usuario user = controller.getUserByEmail(email);
        controller.editPerfil(user, newName, email, user.getSenha(), user.getLogin());
    }

    /**
     * Define uma nova senha para o usuário com o e-mail especificado.
     *
     * @param newPassword A nova senha do usuário.
     * @param email O e-mail do usuário.
     */
    public void setPasswordByUserEmail(String newPassword, String email) {
        Usuario user = controller.getUserByEmail(email);
        controller.editPerfil(user, user.getNome(), email, newPassword, user.getLogin());
    }

    /**
     * Define um novo e-mail para o usuário com o e-mail especificado.
     *
     * @param newEmail O novo e-mail do usuário.
     * @param email O e-mail atual do usuário.
     */
    public void setEmailByUserEmail(String newEmail, String email) {
        Usuario user = controller.getUserByEmail(email);
        controller.editPerfil(user, user.getNome(), newEmail, user.getSenha(), user.getLogin());
    }

    /**
     * Deleta o usuário com o e-mail especificado.
     *
     * @param email O e-mail do usuário a ser deletado.
     */
    public void deleteByUserEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        controller.deleteUser(user);
    }

    /**
     * Realiza o login de um usuário com base no login e senha fornecidos.
     *
     * @param login O login do usuário.
     * @param password A senha do usuário.
     * @return true se o login for bem-sucedido, false caso contrário.
     */
    public boolean login(String login, String password) {
        return controller.signIn(login, password);
    }

    /**
     * Deleta todos os usuários do sistema.
     */
    public void deleteAllUsers() {
        controller.deleteAllUser();
    }

    /**
     * Recupera o ID de um usuário com base em seu e-mail.
     *
     * @param email O e-mail do usuário.
     * @return O ID do usuário.
     */
    public UUID getUserIdByEmail(String email) {
        Usuario user = controller.getUserByEmail(email);
        return user.getID();
    }
}
