package facade;

import controller.Controller;
import models.Evento;
import models.Ingresso;
import models.Usuario;

import java.util.Date;
import java.util.List;


public class Facade {

    private Controller controller;

    public Facade() {
        this.controller = new Controller();
    }

    // Chamadas referentes ao usuário
    public Usuario cadastrarUsuario(String login, String senha, String nome, String CPF, String email, boolean isAdmin) {
        return controller.cadastrarUsuario(login, senha, nome, CPF, email, isAdmin);
    }

    // Chamadas referentes a Evento
    public Evento cadastrarEvento(Usuario usuario, String nome, String descricao, Date data){
        return controller.cadastrarEvento(usuario, nome, descricao, data);
    }

    public void adicionarAssentoEvento(String nomeEvento, String assento) {
        controller.adicionarAssentoEvento(nomeEvento, assento);
    }

    public List<Evento> listarEventosDisponiveis() {
        return controller.listarEventosDisponiveis();
    }

    // Chamadas referentes a Ingresso
    public Ingresso comprarIngresso(Usuario usuario, String nomeEvento, String assento) {
        return controller.comprarIngresso(usuario, nomeEvento, assento);
    }

    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso) {
        return controller.cancelarCompra(usuario, ingresso);
    }

    public List<Ingresso> listarIngressosComprados(Usuario usuario) {
        return controller.listarIngressosComprados(usuario);
    }
}
