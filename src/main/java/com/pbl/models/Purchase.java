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
 * A classe {@code Purchase} representa uma compra de ingresso realizada por um usuário.
 * Cada compra é associada a um usuário, um ingresso e opcionalmente a um cartão de pagamento.
 * A classe também gera automaticamente um identificador único (UUID) para cada instância de compra.
 * <p>
 * Esta classe oferece construtores para criar uma compra com ou sem um cartão de pagamento,
 * além de métodos de acesso para recuperar informações da compra.
 * </p>
 *
 * @author Guilherme Fernandes Sardinha
 * @since 12/09/2024
 */
package com.pbl.models;

import java.util.UUID;

public class Purchase {

    /** O identificador único do usuário que realizou a compra. */
    protected UUID userID;

    /** O identificador único do ingresso associado à compra. */
    protected UUID ticketID;

    /** O identificador único gerado para a compra. */
    protected UUID ID;

    /** O identificador único do cartão de pagamento utilizado na compra (pode ser null). */
    protected UUID cardID;

    /**
     * Construtor para criar uma compra sem cartão de pagamento.
     * O campo {@code cardID} é inicializado como {@code null}.
     *
     * @param userID   O identificador único do usuário que realizou a compra.
     * @param ticketID O identificador único do ingresso associado à compra.
     */
    public Purchase(UUID userID, UUID ticketID) {
        this.userID = userID;
        this.ticketID = ticketID;
        this.ID = UUID.randomUUID();
        this.cardID = null;
    }

    /**
     * Construtor para criar uma compra com cartão de pagamento.
     *
     * @param userID   O identificador único do usuário que realizou a compra.
     * @param ticketID O identificador único do ingresso associado à compra.
     * @param cardID   O identificador único do cartão de pagamento utilizado na compra.
     */
    public Purchase(UUID userID, UUID ticketID, UUID cardID) {
        this.userID = userID;
        this.ticketID = ticketID;
        this.ID = UUID.randomUUID();
        this.cardID = cardID;
    }

    /**
     * Retorna o identificador único do usuário que realizou a compra.
     *
     * @return O identificador único do usuário.
     */
    public UUID getUser() {
        return userID;
    }

    /**
     * Retorna o identificador único do ingresso associado à compra.
     *
     * @return O identificador único do ingresso.
     */
    public UUID getTicket() {
        return ticketID;
    }

    /**
     * Retorna o identificador único gerado para a compra.
     *
     * @return O identificador único da compra.
     */
    public UUID getID() {
        return ID;
    }

    /**
     * Retorna o identificador único do cartão associado à compra.
     *
     * @return O identificador único do cartão.
     */
    public UUID getCardID() {
        return cardID;
    }
}
