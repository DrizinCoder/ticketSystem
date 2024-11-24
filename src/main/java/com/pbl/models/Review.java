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

import java.util.UUID;

/**
 * A classe {@code Review} representa uma avaliação de um evento realizada por um usuário.
 * Cada avaliação contém o nome do usuário, um comentário, uma classificação numérica,
 * o identificador do evento avaliado, e um identificador único para a avaliação.
 *
 * @author Guilherme Fernandes Sardinha
 */
public class Review {
    protected String userName;
    protected String comment;
    protected int rating;
    protected UUID eventID;
    protected UUID ID;

    /**
     * Construtor que inicializa uma nova avaliação.
     *
     * @param userName o nome do usuário que realizou a avaliação
     * @param comment  o comentário feito sobre o evento
     * @param rating   a classificação do evento, sendo um valor inteiro
     * @param eventID  o identificador único do evento avaliado
     */
    public Review(String userName, String comment, int rating, UUID eventID) {
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;
        this.eventID = eventID;
        this.ID = UUID.randomUUID();
    }

    /**
     * Retorna o nome do usuário que fez a avaliação.
     *
     * @return o nome do usuário
     */
    public String getUser() {
        return userName;
    }

    /**
     * Define o nome do usuário que fez a avaliação.
     *
     * @param userName o novo nome do usuário
     */
    public void setUser(String userName) {
        this.userName = userName;
    }

    /**
     * Retorna o identificador único da avaliação.
     *
     * @return o identificador único da avaliação
     */
    public UUID getID() {
        return ID;
    }

    /**
     * Retorna o identificador único do evento avaliado.
     *
     * @return o identificador único do evento
     */
    public UUID getEventID() {
        return eventID;
    }

    /**
     * Retorna o comentário feito na avaliação.
     *
     * @return o comentário da avaliação
     */
    public String getComment() {
        return comment;
    }

    /**
     * Retorna a classificação do evento dada pelo usuário.
     *
     * @return a classificação do evento
     */
    public int getRating() {
        return rating;
    }
}
