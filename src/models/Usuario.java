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
package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
     * @param admin Variavel que verifica se o usuário é um Administrador ou Cliente
     * @author Guilherme Fernandes Sardinha
     */
    public Usuario(String login, String senha, String nome, String CPF, String email, boolean admin) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.admin = admin;
        this.mailBox = new ArrayList<String>();
        this.ID = UUID.randomUUID();
    }

    /**
     * Este método serve para pegar o Login do usuário e retornar este valor como uma string.
     * Este valor poderá ser usado em outros lugares no código.
     * @return login
     * @author Guilherme Fernandes Sardinha
     */
    public String getLogin() {
        return login;
    }

    /**
     * Esse método verifica se o login e senha passados são iguais ao do usuário
     * @param login o login passado para análise
     * @param senha a senha passada para análise
     * @return booleano true ou false
     * @author Guilherme Fernandes Sardinha
     */
    public boolean getLogin(String login, String senha) {
        return login.equals(this.login) && senha.equals(this.senha);
    }

    /**
     * Este método serve para pegar o Nome do usuário e retornar este valor como uma string.
     * Este valor poderá ser usado em outros lugares no código.
     * @return nome
     * @author Guilherme Fernandes Sardinha
     */
    public String getNome() {
        return nome;
    }

    /**
     * Este método serve para pegar o CPF do usuário e retornar este valor como uma string.
     * Este valor poderá ser usado em outros lugares no código.
     * @return CPF
     * @author Guilherme Fernandes Sardinha
     */
    public String getCpf() {
        return CPF;
    }

    /**
     * Este método serve para pegar o Email do usuário e retornar este valor como uma string.
     * Este valor poderá ser usado em outros lugares no código.
     * @return email
     * @author Guilherme Fernandes Sardinha
     */
    public String getEmail() {
        return email;
    }

    /**
     * Este método serve para pegar a senha do usuário e retornar este valor como uma string.
     * Este valor poderá ser usado em outros lugares no código.
     * @return senha
     * @author Guilherme Fernandes Sardinha
     */
    public String getSenha(){return senha;}

    /**
     * Este método serve para pegar o ID do usuário e retornar este valor como uma string.
     * Este valor poderá ser usado em outros lugares no código.
     * @return UUID ID
     * @author Guilherme Fernandes Sardinha
     */
    public UUID getID(){return ID;}

    /**
     * Este método serve para pegar a caixa de Email do usuário e retornar este valor como uma string.
     * Este valor poderá ser usado em outros lugares no código.
     * @return mailBox Lista com todos os e-mails recebidos pelo usuário
     * @author Guilherme Fernandes Sardinha
     */
    public List<String> getMailBox() { return mailBox; }

    /**
     * Este método serve para alterar a senha de um usuário a partir do parametro passado por ela.
     * @param senha a senha que será substituida
     * @author Guilherme Fernandes Sardinha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Este método serve para alterar o login de um usuário a partir do parametro passado por ela.
     * @param login o novo login do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Este método serve para alterar o nome de um usuário a partir do parametro passado por ela.
     * @param nome o novo nome do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Este método serve para alterar o email de um usuário a partir do parametro passado por ela.
     * @param email o novo email do usuário
     * @author Guilherme Fernandes Sardinha
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Este método serve para pegar o atributo isAdmin do usuário e retornar este valor como um booleano.
     * Este valor poderá ser usado em outros lugares no código.
     * @return admin True - caso seja administrador, False - caso seja Cliente
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Este método adiciona na caixa de emails do usuáro uma mensaem de confirmação de compra
     * @param mailNotification Mensagem de confirmação de compra enviada para o usuário
     * @author Guilherme Fernandes Sardinha
     */
    public void setMailBox(String mailNotification) { this.mailBox.add(mailNotification); }

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

        if(!login.equals(usuarioTeste.getLogin())) return false;
        if(!nome.equals(usuarioTeste.getNome())) return false;
        if(!CPF.equals((usuarioTeste.getCpf()))) return false;
        if(!email.equals((usuarioTeste.getEmail()))) return false;
        return true;
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
    public int hashCode(){
        int loginHash = login != null ? login.hashCode() : 0;
        int nomeHash = nome != null ? nome.hashCode() : 0;
        int CPFHash = CPF != null ? CPF.hashCode() : 0;
        int emailHash = email != null ? email.hashCode() : 0;
        return (loginHash * nomeHash) + (CPFHash * emailHash);
    }
}
