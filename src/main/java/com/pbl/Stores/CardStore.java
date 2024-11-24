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
package com.pbl.Stores;

import com.pbl.Interfaces.Store;
import com.pbl.Resources.FilePaths;
import com.pbl.models.Card;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code CardStore} implementa a interface {@code Store} para gerenciar um repositório de cartões.
 * Ela fornece funcionalidades para adicionar, remover, buscar e serializar cartões em um arquivo JSON.
 */
public class CardStore implements Store<Card> {
    private List<Card> cards;

    /**
     * Constrói um novo {@code CardStore} inicializando a lista de cartões.
     */
    public CardStore() {
        desserializer();
    }

    /**
     * Adiciona um cartão ao repositório e atualiza o armazenamento persistente.
     *
     * @param card o cartão a ser adicionado
     */
    @Override
    public void add(Card card) {
        cards.add(card);
        serializer();
    }

    /**
     * Remove um cartão do repositório e atualiza o armazenamento persistente.
     *
     * @param card o cartão a ser removido
     */
    @Override
    public void remove(Card card) {
        cards.remove(card);
        serializer();
    }

    /**
     * Retorna um cartão pelo seu identificador único.
     *
     * @param ID o identificador do cartão a ser buscado
     * @return o cartão correspondente ao ID, ou {@code null} se não encontrado
     */
    @Override
    public Card getByID(UUID ID) {
        desserializer();
        for (Card card : cards) {
            if (card.getID().equals(ID)) {
                return card;
            }
        }
        return null;
    }

    public List<Card> getUserCards(UUID userID) {
        desserializer();
        List<Card> userCards = new ArrayList<>();
        for (Card card : cards) {
            if(card.getUserID().equals(userID)) {
                userCards.add(card);
            }
        }
        return userCards;
    }

    /**
     * Retorna a lista de cartões armazenados.
     *
     * @return a lista de cartões
     */
    @Override
    public List<Card> get() {
        desserializer();
        return cards;
    }

    /**
     * Serializa a lista de cartões para um arquivo JSON.
     */
    @Override
    public void serializer() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FilePaths.CARD_JSON)) {
            // Gravar a lista no arquivo JSON
            gson.toJson(cards, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desserializa os cartões a partir de um arquivo JSON, carregando-os na lista.
     */
    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.CARD_JSON)) {
            Type cardListType = new TypeToken<ArrayList<Card>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            cards = gson.fromJson(reader, cardListType);
            if (cards == null) {
                // Caso o arquivo esteja vazio
                cards = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Caso o arquivo não exista ou ocorra erro
            cards = new ArrayList<>();
        }
    }

    /**
     * Atualiza o status de um cartão, desativando-o e persistindo a alteração.
     *
     * @param card o cartão a ser atualizado
     */
    public void updateCardStatus(Card card) {
        card.desactivate();
        serializer();
    }

    /**
     * Remove todos os cartões do armazenamento.
     *
     * Este método limpa a lista de cartões e atualiza o armazenamento,
     * garantindo que nenhum cartão permaneça.
     */
    public void deleteAll() {
        cards.clear();
        serializer();
    }

    /**
     * Busca um cartão pelo seu número.
     *
     * @param cardNumber o número do cartão a ser buscado
     * @return o objeto {@code Card} correspondente ao número do cartão, ou {@code null} se não for encontrado
     */
    public Card getByCardNumber(String cardNumber) {
        desserializer(); // Carrega os cartões do armazenamento
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card; // Retorna o cartão encontrado
            }
        }
        return null; // Retorna null se o cartão não for encontrado
    }
}
