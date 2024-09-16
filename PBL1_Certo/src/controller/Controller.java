package controller;

import models.Evento;
import models.Usuario;
import models.Ingresso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Controller {

    protected List<Usuario> usuarios;
    protected List<Evento> eventos;

    public Controller() {
        this.usuarios = new ArrayList<Usuario>();
        this.eventos = new ArrayList<Evento>();
    }

    public Usuario cadastrarUsuario(String login, String senha, String nome, String CPF, String email, boolean isAdmin){
        Usuario usuario = new Usuario(login, senha, nome, CPF, email, isAdmin);
        this.usuarios.add(usuario);

        return usuario;
    }

    public Evento cadastrarEvento(Usuario usuario, String nome, String descricao, Date data){
        if(usuarios.contains(usuario) && usuario.isAdmin()){
            Evento evento = new Evento(nome, descricao, data);
            this.eventos.add(evento);

            return evento;
        } else if (!usuarios.contains(usuario)) {
            throw new SecurityException("Usuário não está registrado no sistema.");
        } else {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
    };

    public void adicionarAssentoEvento(String nomeEvento, String assento) {
        for (Evento evento : eventos) {
            if (evento.getNome().equals(nomeEvento)) { evento.adicionarAssento(assento); }
        }
    }

    public Ingresso comprarIngresso(Usuario usuario, String nomeEvento, String assento) {
        Evento evento1 = null;

        for (Evento evento : eventos) {
            if (evento.getNome().equals(nomeEvento) && evento.isAtivo()) { evento1 = evento; break; }
        }

        if (evento1 != null) {
            Ingresso ingresso = new Ingresso(evento1, assento);

            usuario.adicionarIngresso(ingresso);
            return ingresso;

        } else {
            throw new SecurityException("Acesso negado: Evento com nome " + nomeEvento + " não encontrado.");
        }
    }

    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso) {
        int hash = ingresso.hashCode();
        for (Ingresso ingresso1 : usuario.getIngressos()){
            if (hash == ingresso1.hashCode()) {
                ingresso1.cancelar();
                usuario.removerIngresso(ingresso1);
                return true;
            }
        }
        return false;
    }

    public List<Evento> listarEventosDisponiveis() {
        List<Evento> eventosDisponiveis = new ArrayList<>();

        System.out.println("Lista de eventos disponiveis");
        for (Evento evento : eventos) {
            if (evento.isAtivo()) {
                eventosDisponiveis.add(evento);
                System.out.println(evento.getNome());
                System.out.println(evento.getDescricao());
            }
        }
        return eventosDisponiveis;
    }

    public List<Ingresso> listarIngressosComprados(Usuario usuario) {
        List<Ingresso> ingressosDisponiveis = new ArrayList<>();

        System.out.println("Lista de ingressos comprados do usuário " + usuario.getNome());
        for(Ingresso ingresso : usuario.getIngressos()){
            ingressosDisponiveis.add(ingresso);
            System.out.println(ingresso.getEvento().getNome());
            System.out.println(ingresso.getAssento());
        }

        return ingressosDisponiveis;
    }
}
