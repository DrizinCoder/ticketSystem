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
/**
 * A classe {@code Ingresso} representa um ingresso associado a um evento.
 * <p>
 * Cada ingresso possui um identificador único (UUID), um preço, um nome de assento,
 * um status indicando se está ativo ou cancelado, e informações relacionadas ao
 * evento e ao usuário que comprou o ingresso.
 * </p>
 * <p>
 * A classe oferece métodos para acessar os atributos do ingresso, bem como para
 * cancelar ou reativar o ingresso. Também inclui a capacidade de comparar ingressos
 * e calcular seus códigos de hash.
 * </p>
 *
 * @author Guilherme Fernandes Sardinha
 * @since 12/09/2024
 */
package com.pbl.models;

import java.util.UUID;

public class Ingresso {

    /** O identificador único do evento associado ao ingresso. */
    protected UUID eventID;

    /** O preço do ingresso. */
    protected double preco;

    /** O nome do assento associado ao ingresso. */
    protected String nomeAssento;

    /** O status do ingresso, indicando se está ativo ou cancelado. */
    protected boolean status;

    /** O identificador único do ingresso. */
    protected UUID ID;

    /**
     * Construtor padrão que inicializa um objeto {@code Ingresso} com um valor personalizado.
     * O ingresso é marcado como ativo ao ser criado.
     *
     * @param eventID     o identificador do evento associado
     * @param valor       o valor do ingresso
     * @param nomeAssento o nome do assento
     */
    public Ingresso(UUID eventID, double valor, String nomeAssento) {
        this.eventID = eventID;
        this.preco = valor;
        this.nomeAssento = nomeAssento;
        this.status = true;
        this.ID = UUID.randomUUID();
    }

    /**
     * Construtor sobrecarregado que inicializa um objeto {@code Ingresso} com
     * um valor de ingresso padrão de 100.
     * O ingresso é marcado como ativo ao ser criado.
     *
     * @param eventID     o identificador do evento associado
     * @param nomeAssento o nome do assento
     */
    public Ingresso(UUID eventID, String nomeAssento) {
        this.eventID = eventID;
        this.preco = 100;
        this.nomeAssento = nomeAssento;
        this.ID = UUID.randomUUID();
        this.status = true;
    }

    /**
     * Retorna o identificador do evento associado ao ingresso.
     *
     * @return o UUID do evento
     */
    public UUID getEventoID() {
        return eventID;
    }

    /**
     * Retorna o preço do ingresso.
     *
     * @return o preço do ingresso
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Retorna o nome do assento associado ao ingresso.
     *
     * @return o nome do assento
     */
    public String getAssento() {
        return nomeAssento;
    }

    /**
     * Retorna o identificador único do ingresso.
     *
     * @return o UUID do ingresso
     */
    public UUID getID() {
        return ID;
    }

    /**
     * Verifica se o ingresso está ativo.
     *
     * @return {@code true} se o ingresso estiver ativo, {@code false} se estiver cancelado
     */
    public boolean isAtivo() {
        return status;
    }

    /**
     * Cancela o ingresso, alterando seu status para inativo.
     *
     * @return
     */
    public boolean cancelar() {
        this.status = false;
        return true;
    }

    /**
     * Reativa o ingresso, alterando seu status para ativo.
     */
    public void reativar() {
        this.status = true;
    }

    /**
     * Compara este ingresso com outro objeto para verificar se são iguais.
     * A comparação é feita com base no preço, evento e assento.
     *
     * @param ingresso o objeto a ser comparado
     * @return {@code true} se os ingressos forem iguais, {@code false} caso contrário
     */
    @Override
    public boolean equals(Object ingresso) {
        if (this == ingresso) return true;
        if (ingresso == null) return false;
        if (!(ingresso instanceof Ingresso ingressoTeste)) return false;

        if (Double.compare(ingressoTeste.getPreco(), this.preco) != 0) return false;
        if (!eventID.equals(ingressoTeste.eventID)) return false;
        return nomeAssento.equals(ingressoTeste.getAssento());
    }

    /**
     * Calcula o código hash do ingresso com base no evento e no nome do assento.
     *
     * @return o código hash do ingresso
     */
    @Override
    public int hashCode() {
        int nomeHash = eventID != null ? eventID.hashCode() : 0;
        int assentoHash = nomeAssento != null ? nomeAssento.hashCode() : 0;
        return (int) preco * nomeHash + assentoHash;
    }
}
