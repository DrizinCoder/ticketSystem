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

import java.util.UUID;

//Classe Ingresso
public class Ingresso {
    protected UUID eventID;
    protected double preco;
    protected String nomeAssento;
    protected boolean status;
    protected UUID ID;
    protected UUID userID;

    // Construtor de ingressos com valor personalizado
    public Ingresso(UUID eventID, double valor, String nomeAssento, UUID userID) {
        this.eventID = eventID;
        this.preco = valor;
        this.nomeAssento = nomeAssento;
        this.status = true;
        this.ID = UUID.randomUUID();
        this.userID = userID;
    }

    // Construtor sobrecarregado com valor de ingresso padrão
    public Ingresso (UUID eventID, String nomeAssento, UUID userID){
        this.eventID = eventID;
        this.preco = 100;
        this.nomeAssento = nomeAssento;
        this.userID = userID;
        this.ID = UUID.randomUUID();
        this.status = true;
    }

    // metodos getters
    public UUID getEventoID() {
        return eventID;
    }

    public double getPreco() {
        return preco;
    }

    public String getAssento() {
        return nomeAssento;
    }

    public UUID getID() { return ID; }

    public UUID getUserID() { return userID; }

    public boolean isAtivo() {
        return status;
    }

    // metodos Setters
    public boolean cancelar(){
        this.status = false;
        return true;
    }

    public void reativar(){
        this.status = true;
    }

    //metodo equals para realizar comparações entre objetos ingresso
    @Override
    public boolean equals(Object ingresso) {
        if(this == ingresso) return true;
        if(ingresso == null) return false;
        if(!(ingresso instanceof Ingresso)) return false;

        Ingresso ingressoTeste = (Ingresso) ingresso;

        if (Double.compare(ingressoTeste.getPreco(), this.preco) != 0) return false;
        if (!eventID.equals(ingressoTeste.eventID)) return false;
        if (!nomeAssento.equals(ingressoTeste.getAssento())) return false;
        return true;
    }

    // hashCode para verificar se 2 ingressos são iguais
    @Override
    public int hashCode(){
        int nomeHash = eventID != null ? eventID.hashCode() : 0;
        int assentoHash = nomeAssento != null ? nomeAssento.hashCode() : 0;
        return (int) preco * nomeHash + assentoHash;
    }
}
