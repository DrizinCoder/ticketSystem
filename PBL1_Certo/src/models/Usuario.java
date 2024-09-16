package models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    protected String login;
    protected String senha;
    protected String nome;
    protected String CPF;
    protected String email;
    protected boolean admin;
    protected List<Ingresso> ingressos;

    public Usuario(String login, String senha, String nome, String CPF, String email, boolean admin) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.admin = admin;
        this.ingressos = new ArrayList<Ingresso>();
    }

    public String getLogin() {
        return login;
    }

    public boolean getLogin(String login, String senha) {
        return login.equals(this.login) && senha.equals(this.senha);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String CPF) {
        this.CPF = CPF;
    }

    public void setEmail(String email) { this.email = email; }

    public boolean isAdmin() {
        return admin;
    }

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

    @Override
    public int hashCode(){
        int loginHash = login != null ? login.hashCode() : 0;
        int nomeHash = nome != null ? nome.hashCode() : 0;
        int CPFHash = CPF != null ? CPF.hashCode() : 0;
        int emailHash = email != null ? email.hashCode() : 0;
        return (loginHash * nomeHash) + (CPFHash * emailHash);
    }


    public List<Ingresso> getIngressos() { return ingressos; }

    public void adicionarIngresso(Ingresso ingresso) { this.ingressos.add(ingresso); }

    public void removerIngresso(Ingresso ingresso) { this.ingressos.remove(ingresso); }
}
