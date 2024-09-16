package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {
    protected String nome;
    protected String descricao;
    protected Date data;
    protected boolean status;
    protected List<String> Assento;

    public Evento(String nome, String descricao, Date data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.status = setStatus();
        this.Assento = new ArrayList<>();
    }

    public String getNome() { return nome; }

    public String getDescricao() { return descricao; }

    public Date getData() { return data; }


    public boolean isAtivo() { return status; }
    public boolean setStatus() {
        Date date = new Date();
        this.status = date.before(data);
        return this.status;
    }

    public List<String> getAssentosDisponiveis() { return Assento; }

    public void adicionarAssento(String assento) { this.Assento.add(assento); }

    public void removerAssento(String assento) { this.Assento.remove(assento); }

}
