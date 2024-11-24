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

import java.util.Date;
import java.util.UUID;

/**
 * A classe {@code Card} representa um cartão de crédito ou débito associado a um usuário.
 * Cada cartão contém informações como o nome do titular, a bandeira do cartão, o número do cartão,
 * o número da conta, a data de validade, o código CVV, o status de ativação e o ID único do cartão
 * e do usuário proprietário.
 *
 * @author Guilherme Fernandes Sardinha
 */
public class Card {
    protected String cardHolderName;
    protected String cardNumber;
    protected Date expiryDate;
    protected int cvv;
    protected boolean isActive;
    protected UUID ID;
    protected UUID userID;

    /**
     * Construtor que inicializa um objeto {@code Card} com as informações fornecidas.
     *
     * @param cardHolderName o nome do titular do cartão
     * @param cardNumber o número do cartão
     * @param expiryDate a data de validade do cartão
     * @param cvv o código de segurança CVV do cartão
     * @param userID o identificador único do usuário associado a este cartão
     * @author Guilherme Fernandes Sardinha
     */
    public Card(String cardHolderName, String cardNumber, Date expiryDate, int cvv, UUID userID) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.isActive = true;
        this.ID = UUID.randomUUID();
        this.userID = userID;
    }

    /**
     * Retorna o número do cartão.
     *
     * @return o número do cartão
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Retorna o nome do proprietário do cartão.
     *
     * @return o nome do proprietário do cartão
     */
    public String getCardHolderName() { return cardHolderName; }

    /**
     * Retorna a data de validade do cartão.
     *
     * @return a data de validade do cartão
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Retorna o identificador único (UUID) do cartão.
     *
     * @return o identificador único do cartão
     */
    public UUID getID() {
        return ID;
    }

    /**
     * Retorna o identificador único do usuário associado a este cartão.
     *
     * @return o identificador único do usuário
     */
    public UUID getUserID() {
        return userID;
    }

    /**
     * Verifica se o cartão está ativo.
     *
     * @return {@code true} se o cartão estiver ativo, {@code false} caso contrário
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Desativa o cartão, alterando seu status para inativo.
     *
     * @author Guilherme Fernandes Sardinha
     */
    public void desactivate() {
        isActive = false;
    }
}
