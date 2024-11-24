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
package com.pbl.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code Evento} representa um evento que pode conter informações como
 * nome, descrição, data, status de ativação, taxa de avaliação e uma lista de
 * assentos disponíveis.
 * Cada evento possui um identificador único (UUID) e pode ser gerenciado
 * em termos de suas reservas de assentos e status de atividade (ativo ou inativo).
 *
 * @author Guilherme Fernandes Sardinha
 */
public class Evento {
    protected String name;
    protected String description;
    protected Date date;
    protected boolean status;
    protected List<String> seats;
    protected UUID ID;
    protected double rate;

    /**
     * Construtor padrão que inicializa um objeto {@code Evento} com as informações
     * fornecidas.
     *
     * @param name        o nome do evento
     * @param description a descrição do evento
     * @param date        a data de realização do evento
     */
    public Evento(String name, String description, Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.status = setStatus();
        this.seats = new ArrayList<>();
        this.ID = UUID.randomUUID();
        this.rate = 0;
    }

    /**
     * Retorna o nome do evento.
     *
     * @return o nome do evento
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna a descrição do evento.
     *
     * @return a descrição do evento
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retorna o identificador único (UUID) do evento.
     *
     * @return o UUID do evento
     */
    public UUID getID() {
        return ID;
    }

    /**
     * Retorna a lista de assentos disponíveis no evento.
     *
     * @return a lista de assentos
     */
    public List<String> getSeats() {
        return seats;
    }

    /**
     * Retorna a data de realização do evento.
     *
     * @return a data do evento
     */
    public Date getDate() {
        return date;
    }

    public Double getRate() { return rate; }

    /**
     * Verifica se o evento está ativo com base na data atual.
     *
     * @return {@code true} se o evento estiver ativo (não ocorrido), {@code false} caso contrário
     */
    public boolean isAtivo() {
        return status;
    }

    /**
     * Define o status do evento com base na data atual.
     * O evento é ativo se a data atual for anterior à data do evento.
     *
     * @return o novo status do evento
     */
    public boolean setStatus() {
        Date dateAux = new Date();
        this.status = dateAux.before(date);
        return this.status;
    }

    /**
     * Define a taxa de avaliação do evento.
     *
     * @param rate a nova taxa de avaliação do evento
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Adiciona um assento à lista de assentos do evento.
     *
     * @param seats o assento a ser adicionado
     */
    public void addSeats(String seats) {
        this.seats.add(seats);
    }

    /**
     * Remove um assento da lista de assentos do evento.
     *
     * @param seat o assento a ser removido
     */
    public void removeSeats(String seat) {
        this.seats.remove(seat);
        if(seats.isEmpty()) {
            System.out.println("FUNCIONOU");
        } else{
            System.out.println("TSOU O JAVA E ESTOU TE TROLANDO");
        }
    }

    public void setSeats(ArrayList<String> strings) {
        seats = strings;
    }
}
