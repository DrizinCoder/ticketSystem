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
package com.pbl.service;

import com.pbl.Stores.CardStore;
import com.pbl.models.Card;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code CardService} fornece serviços relacionados ao gerenciamento de cartões de crédito.
 * Ela permite a criação, remoção e desativação de cartões para os usuários.
 */
public class CardService {
    private final CardStore cardStore;

    /**
     * Constrói um novo {@code CardService} com o {@code CardStore} especificado.
     *
     * @param cardStore o armazenamento de cartões a ser utilizado
     */
    public CardService(CardStore cardStore) {
        this.cardStore = cardStore;
    }

    /**
     * Cria um novo cartão de crédito e o adiciona ao armazenamento.
     *
     * @param CardHolderName o nome do titular do cartão
     * @param cardNumber     o número do cartão
     * @param expiryDate     a data de expiração do cartão
     * @param cvv           o código de verificação do cartão
     * @param userId        o identificador único do usuário que possui o cartão
     * @return o cartão criado
     */
    public Card createCard(String CardHolderName, String cardNumber,
                           Date expiryDate, int cvv, UUID userId) {

        Card card = new Card(CardHolderName, cardNumber, expiryDate, cvv, userId);
        if(cardStore.getByCardNumber(cardNumber) == null){
            cardStore.add(card);
        } else{
            throw new IllegalArgumentException("Cartão com este número já existe.");
        }

        return card;
    }

    /**
     * Remove um cartão de crédito do armazenamento.
     *
     * @param card o cartão a ser removido
     */
    public void removeCard(Card card) {
        cardStore.remove(card);
    }

    /**
     * Desativa um cartão de crédito, atualizando seu status no armazenamento.
     *
     * @param card o cartão a ser desativado
     * @return o cartão desativado, ou {@code null} se o cartão não for encontrado
     */
    public Card disableCard(Card card) {
        if(card != null) {
            cardStore.updateCardStatus(card);
        }

        return card;
    }

    /**
     * Retorna um objeto {@code Card} com base no seu identificador único.
     *
     * @param id o UUID que identifica o cartão
     * @return o objeto {@code Card} correspondente ao UUID fornecido, ou {@code null} se o UUID for inválido
     */
    public Card getCardByID(UUID id) {
        if(id == null) {
            return null;
        }
        return cardStore.getByID(id);
    }

    public List<Card> getAllUserCards(UUID userId) {
        return cardStore.getUserCards(userId);
    }

    public Card getCardByNumber(String cardNumber) {
        return cardStore.getByCardNumber(cardNumber);
    }

    /**
     * Exclui todos os cartões cadastrados no sistema.
     * Esta operação limpa todos os registros de cartões armazenados.
     */
    public void deleteAllCards() {
        cardStore.deleteAll();
    }
}
