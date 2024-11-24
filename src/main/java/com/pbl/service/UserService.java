/*******************************************************************************
 * Autor: Guilherme Fernandes Sardinha
 * Componente Curricular: MI de Programação
 * Concluído em: 12/09/2024
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 * trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 * apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 * de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 * do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/
package com.pbl.service;

import com.pbl.Stores.UserStore;
import com.pbl.models.Usuario;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A classe {@code UserService} fornece serviços relacionados ao gerenciamento de usuários no sistema.
 * Ela permite o cadastro, login, busca e edição de informações de usuários.
 */
public class UserService {
    private final UserStore userStore;

    /**
     * Constrói um novo {@code UserService} com o {@code UserStore} especificado.
     *
     * @param userStore o armazenamento de usuários a ser utilizado
     */
    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    /**
     * Realiza o cadastro de um usuário no sistema.
     *
     * @param login  o login do usuário
     * @param senha  a senha do usuário
     * @param nome   o nome do usuário
     * @param CPF    o CPF do usuário
     * @param email  o email do usuário
     * @param isAdmin indica se o usuário é um administrador
     * @return o usuário cadastrado
     * @throws SecurityException se o login, email e/ou CPF já estiverem em uso
     */
    public Usuario singUpUser(String login, String senha, String nome, String CPF, String email, boolean isAdmin) {
        Usuario user = new Usuario(login, senha, nome, CPF, email, isAdmin);
        if (userStore.getByEmail(user.getEmail()) == null) {
            userStore.add(user);
            return user;
        } else {
            throw new SecurityException("Login, email e/ou cpf já está em uso.");
        }
    }

    /**
     * Realiza o login de um usuário no sistema.
     *
     * @param login o login do usuário
     * @param senha a senha do usuário
     * @return o usuário autenticado, ou {@code null} se as credenciais forem inválidas
     */
    public Usuario singInUser(String login, String senha) {
        Usuario user = userStore.getByLogin(login);
        if (user != null && user.getSenha().equals(senha)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * Busca um usuário pelo seu login.
     *
     * @param login o login do usuário a ser buscado
     * @return o usuário encontrado, ou {@code null} se não houver usuário com o login fornecido
     */
    public Usuario searchUser(String login) {
        return userStore.getByLogin(login);
    }

    /**
     * Edita as informações de um usuário.
     *
     * @param user     o usuário a ser editado
     * @param name     o novo nome do usuário
     * @param email    o novo email do usuário
     * @param password a nova senha do usuário
     * @param login    o novo login do usuário
     * @return o usuário atualizado
     */
    public Usuario editUser(Usuario user, String name, String email, String password, String login) {
        String nameAux = !Objects.equals(name, user.getNome()) ? name : user.getNome();
        String emailAux = !Objects.equals(email, user.getEmail()) ? email : user.getEmail();
        String passwordAux = !Objects.equals(password, user.getSenha()) ? password : user.getSenha();
        String loginAux = !Objects.equals(login, user.getLogin()) ? login : user.getLogin();

        return userStore.updateUser(user, nameAux, emailAux, passwordAux, loginAux);
    }

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     *
     * @param email o e-mail do usuário a ser buscado
     * @return o objeto {@code Usuario} correspondente ao e-mail, ou {@code null} se não for encontrado
     */
    public Usuario searchUserByEmail(String email) {
        return userStore.getByEmail(email);
    }

    /**
     * Busca um usuário pelo seu login.
     *
     * @param login o login do usuário a ser buscado
     * @return o objeto {@code Usuario} correspondente ao login, ou {@code null} se não for encontrado
     */
    public Usuario searchUserByLogin(String login) {
        return userStore.getByLogin(login);
    }

    /**
     * Recupera uma lista de todos os usuários registrados no sistema.
     *
     * @return uma lista de objetos {@code Usuario} que representam todos os usuários
     */
    public List<Usuario> getUsers() {
        return userStore.get();
    }

    /**
     * Remove um usuário específico do sistema.
     *
     * @param user o objeto {@code Usuario} a ser removido
     */
    public void deleteUser(Usuario user) {
        userStore.remove(user);
    }

    /**
     * Remove todos os usuários do armazenamento.
     *
     * Este método exclui todos os usuários registrados no armazenamento,
     * sem deixar nenhuma referência a eles.
     */
    public void deleteAllUsers() {
        userStore.deleteAll();
    }

    /**
     * Busca um usuário pelo seu identificador único.
     *
     * @param userID o UUID do usuário a ser buscado
     * @return o objeto {@code Usuario} correspondente ao identificador, ou {@code null} se não for encontrado
     */
    public Usuario searchUserById(UUID userID) {
        return userStore.getByID(userID);
    }
}
