package models;

public class Ingresso {
    protected Evento evento;
    protected double preco;
    protected String nomeAssento;
    protected boolean status;

    public Ingresso(Evento evento, double valor, String nomeAssento) {
        this.evento = evento;
        this.preco = valor;
        this.nomeAssento = nomeAssento;
        this.status = true;
    }

    public Ingresso (Evento evento, String nomeAssento){
        this.evento = evento;
        this.preco = 100;
        this.nomeAssento = nomeAssento;
        this.status = true;
    }

    public Evento getEvento() {
        return evento;
    }

    public double getPreco() {
        return preco;
    }

    public String getAssento() {
        return nomeAssento;
    }

    public boolean isAtivo() {
        return status;
    }

    public boolean cancelar(){
        if(evento.isAtivo()){
            this.status = false;
            return true;
        } else {
            return false;
        }
    }

    public void reativar(){
        this.status = evento.isAtivo();
    }

    @Override
    public boolean equals(Object ingresso) {
        if(this == ingresso) return true;
        if(ingresso == null) return false;
        if(!(ingresso instanceof Ingresso)) return false;

        Ingresso ingressoTeste = (Ingresso) ingresso;

        if (Double.compare(ingressoTeste.getPreco(), this.preco) != 0) return false;
        if (!evento.equals(ingressoTeste.evento)) return false;
        if (!nomeAssento.equals(ingressoTeste.getAssento())) return false;
        return true;
    }

    @Override
    public int hashCode(){
        int nomeHash = evento.getNome() != null ? evento.nome.hashCode() : 0;
        int assentoHash = nomeAssento != null ? nomeAssento.hashCode() : 0;
        return (int) preco * nomeHash + assentoHash;
    }
}
