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
package com.pbl.Stores;

import com.pbl.Interfaces.Store;
import com.pbl.Resources.FilePaths;
import com.google.gson.Gson;
import com.pbl.models.Usuario;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * A classe {@code UserStore} implementa a interface {@code Store} para gerenciar um repositório de usuários.
 * Ela fornece funcionalidades para adicionar, remover, buscar e serializar usuários em um arquivo JSON.
 */
public class UserStore implements Store<Usuario> {
    private List<Usuario> users;

    /**
     * Constrói um novo {@code UserStore} inicializando a lista de usuários.
     */
    public UserStore() {
        desserializer();
    }

    /**
     * Adiciona um usuário ao repositório e atualiza o armazenamento persistente.
     *
     * @param user o usuário a ser adicionado
     */
    @Override
    public void add(Usuario user) {
        users.add(user);
        serializer();
    }

    /**
     * Remove um usuário do repositório e atualiza o armazenamento persistente.
     *
     * @param user o usuário a ser removido
     */
    @Override
    public void remove(Usuario user) {
        users.remove(user);
        serializer();
    }

    /**
     * Retorna um usuário pelo seu identificador único.
     *
     * @param ID o identificador do usuário a ser buscado
     * @return o usuário correspondente ao ID, ou {@code null} se não encontrado
     */
    @Override
    public Usuario getByID(UUID ID) {
        desserializer();
        for (Usuario user : users) {
            if (user.getID().equals(ID)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retorna a lista de usuários armazenados.
     *
     * @return a lista de usuários
     */
    @Override
    public List<Usuario> get() {
        desserializer();
        return users;
    }

    /**
     * Desserializa os usuários a partir de um arquivo JSON, carregando-os na lista.
     */
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

    /**
     * Serializa a lista de usuários para um arquivo JSON.
     */
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

    /**
     * Busca um usuário a partir do seu login.
     *
     * @param login o login do usuário a ser buscado
     * @return o usuário correspondente ao login, ou {@code null} se não encontrado
     */
    public Usuario getByLogin(String login) {
        desserializer();
        for (Usuario user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Busca um usuário a partir do seu Email.
     *
     * @param email o login do usuário a ser buscado
     * @return o usuário correspondente ao email, ou {@code null} se não encontrado
     */
    public Usuario getByEmail(String email) {
        desserializer();
        for (Usuario user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um usuário no sistema.
     *
     * @param user  o usuário a ser atualizado
     * @param nome  o novo nome do usuário
     * @param email o novo e-mail do usuário
     * @param senha a nova senha do usuário
     * @param login o novo login do usuário
     * @return o usuário atualizado
     */
    public Usuario updateUser(Usuario user, String nome, String email, String senha, String login) {
        Usuario userAux = getByLogin(user.getLogin());
        if (userAux != null) {
            userAux.setNome(nome);
            userAux.setEmail(email);
            userAux.setSenha(senha);
            userAux.setLogin(login);
        }
        serializer();
        return user;
    }

    /**
     * Atualiza a caixa de entrada de um usuário com uma nova mensagem.
     *
     * @param mailMensage a nova mensagem a ser adicionada à caixa de entrada
     * @param ID         o identificador do usuário cuja caixa de entrada deve ser atualizada
     */
    public void updateMailBox(String mailMensage, UUID ID) {
        Usuario user = getByID(ID);
        if (user != null) {
            user.setMailBox(mailMensage);
        }
        serializer();
    }

    /**
     * Remove todos os usuários do armazenamento.
     * Este método limpa a lista de usuários e atualiza o armazenamento
     * para garantir que nenhum usuário permaneça. Após a execução deste
     * método, a lista de usuários estará vazia.
     */
    public void deleteAll(){
        users.clear();
        serializer();
    }
}
