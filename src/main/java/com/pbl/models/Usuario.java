/*******************************************************************************
 Autor: Guilherme Fernandes Sardinha
 Componente Curricular: MI de Programação
 Concluido em: 12/09/2024
 Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/
package com.pbl.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A classe Usuario representa um usuário no sistema, podendo ser tanto um administrador quanto um cliente.
 * Cada usuário possui um login, senha, nome, CPF, email, e um indicador se é administrador. Além disso,
 * cada usuário possui uma caixa de entrada de emails e um ID único.
 *
 * @author Guilherme Fernandes Sardinha
 */
public class Usuario {
    protected String login;
    protected String senha;
    protected String nome;
    protected String CPF;
    protected String email;
    protected boolean admin;
    protected List<String> mailBox;
    protected UUID ID;

    /**
     * Construtor padrão, utilizado para criar objetos do tipo usuário
     * @param login login de identificação do usuário
     * @param senha senha do usuário
     * @param nome nome completo do usuário
     * @param CPF Certificado de pessoa física do usuário
     * @param email Email para contato do usuário
     * @param admin Variável que verifica se o usuário é um Administrador ou Cliente
     * @author Guilherme Fernandes Sardinha
     */
    public Usuario(String login, String senha, String nome, String CPF, String email, boolean admin) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.admin = admin;
        this.mailBox = new ArrayList<>();
        this.ID = UUID.randomUUID();
    }

    /**
     * Retorna o login do usuário.
     *
     * @return login do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public String getLogin() {
        String login = this.login;  // ou qualquer outra lógica
        return login;
    }

    /**
     * Verifica se o login e senha fornecidos são iguais aos dados do usuário.
     *
     * @param login o login a ser verificado
     * @param senha a senha a ser verificada
     * @return {@code true} se o login e a senha forem válidos; {@code false} caso contrário
     * @author Guilherme Fernandes Sardinha
     */
    public boolean getLogin(String login, String senha) {
        return login.equals(this.login) && senha.equals(this.senha);
    }

    /**
     * Retorna o nome do usuário.
     *
     * @return nome do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o CPF do usuário.
     *
     * @return CPF do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public String getCpf() {
        return CPF;
    }

    /**
     * Retorna o email do usuário.
     *
     * @return email do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retorna a senha do usuário.
     *
     * @return senha do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Retorna o ID único do usuário.
     *
     * @return ID do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public UUID getID() {
        return ID;
    }

    /**
     * Retorna a caixa de emails do usuário.
     *
     * @return lista de mensagens na caixa de email do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public List<String> getMailBox() {
        return mailBox;
    }

    /**
     * Altera a senha do usuário.
     *
     * @param senha a nova senha a ser definida
     * @author Guilherme Fernandes Sardinha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Altera o login do usuário.
     *
     * @param login o novo login a ser definido
     * @author Guilherme Fernandes Sardinha
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Altera o nome do usuário.
     *
     * @param nome o novo nome a ser definido
     * @author Guilherme Fernandes Sardinha
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Altera o email do usuário.
     *
     * @param email o novo email a ser definido
     * @author Guilherme Fernandes Sardinha
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Verifica se o usuário é administrador.
     *
     * @return {@code true} se o usuário for administrador; {@code false} se for cliente
     * @author Guilherme Fernandes Sardinha
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Adiciona uma nova mensagem à caixa de emails do usuário.
     *
     * @param mailNotification a mensagem a ser adicionada
     * @author Guilherme Fernandes Sardinha
     */
    public void setMailBox(String mailNotification) {
        this.mailBox.add(mailNotification);
    }

    /**
     * Compara este objeto {@code Usuario} com o objeto especificado para verificar igualdade.
     * Dois objetos {@code Usuario} são considerados iguais se seus atributos
     * {@code login}, {@code nome}, {@code CPF} e {@code email} forem iguais.
     *
     * @param usuario o objeto a ser comparado com este {@code Usuario}
     * @return {@code true} se o objeto especificado for igual a este {@code Usuario};
     *         {@code false} caso contrário
     * @author Guilherme Fernandes Sardinha
     */
    @Override
    public boolean equals(Object usuario) {
        if(this == usuario) return true;
        if(usuario == null) return false;
        if(!(usuario instanceof Usuario)) return false;

        Usuario usuarioTeste = (Usuario) usuario;

        return login.equals(usuarioTeste.getLogin()) &&
                nome.equals(usuarioTeste.getNome()) &&
                CPF.equals(usuarioTeste.getCpf()) &&
                email.equals(usuarioTeste.getEmail());
    }

    /**
     * Retorna o valor do código hash para este objeto {@code Usuario}.
     * O valor do hash é calculado a partir dos atributos {@code login}, {@code nome},
     * {@code CPF} e {@code email}. Este método garante que dois objetos iguais (de acordo com o método
     * {@link #equals(Object)}) terão o mesmo código hash.
     *
     * @return o código hash calculado para este objeto {@code Usuario}
     * @author Guilherme Fernandes Sardinha
     */
    @Override
    public int hashCode() {
        int loginHash = login != null ? login.hashCode() : 0;
        int nomeHash = nome != null ? nome.hashCode() : 0;
        int CPFHash = CPF != null ? CPF.hashCode() : 0;
        int emailHash = email != null ? email.hashCode() : 0;
        return (loginHash * nomeHash) + (CPFHash * emailHash);
    }

    public void setCpf(String number) {
        this.CPF = number;
    }
}
