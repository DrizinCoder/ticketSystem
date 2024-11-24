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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pbl.models.Review;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code ReviewStore} implementa a interface {@code Store} para gerenciar um repositório de avaliações.
 * Ela fornece funcionalidades para adicionar, remover, buscar e serializar avaliações em um arquivo JSON.
 */
public class ReviewStore implements Store<Review> {
    private List<Review> reviews;

    /**
     * Constrói um novo {@code ReviewStore} inicializando a lista de avaliações.
     */
    public ReviewStore() {
        desserializer();
    }

    /**
     * Adiciona uma avaliação ao repositório e atualiza o armazenamento persistente.
     *
     * @param review a avaliação a ser adicionada
     */
    @Override
    public void add(Review review) {
        reviews.add(review);
        serializer();
    }

    /**
     * Remove uma avaliação do repositório e atualiza o armazenamento persistente.
     *
     * @param review a avaliação a ser removida
     */
    @Override
    public void remove(Review review) {
        reviews.remove(review);
        serializer();
    }

    /**
     * Retorna uma avaliação pelo seu identificador único.
     *
     * @param ID o identificador da avaliação a ser buscada
     * @return a avaliação correspondente ao ID, ou {@code null} se não encontrada
     */
    @Override
    public Review getByID(UUID ID) {
        desserializer();
        for (Review review : reviews) {
            if (review.getID().equals(ID)) {
                return review;
            }
        }
        return null;
    }

    /**
     * Retorna a lista de avaliações armazenadas.
     *
     * @return a lista de avaliações
     */
    @Override
    public List<Review> get() {
        desserializer();
        return reviews;
    }

    /**
     * Serializa a lista de avaliações para um arquivo JSON.
     */
    @Override
    public void serializer() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FilePaths.REVIEW_JSON)) {
            // Gravar a lista no arquivo JSON
            gson.toJson(reviews, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desserializa as avaliações a partir de um arquivo JSON, carregando-as na lista.
     */
    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.REVIEW_JSON)) {
            Type reviewListType = new TypeToken<ArrayList<Review>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            reviews = gson.fromJson(reader, reviewListType);
            if (reviews == null) {
                // Caso o arquivo esteja vazio
                reviews = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Caso o arquivo não exista ou ocorra erro
            reviews = new ArrayList<>();
        }
    }

    /**
     * Retorna uma lista de avaliações associadas a um determinado evento.
     *
     * @param eventID o identificador do evento cujas avaliações devem ser buscadas
     * @return uma lista de avaliações correspondentes ao evento
     */
    public List<Review> getReviewsByEventID(UUID eventID) {
        desserializer();
        List<Review> eventReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getEventID().equals(eventID)) {
                eventReviews.add(review);
            }
        }
        return eventReviews;
    }

    /**
     * Remove todas as avaliações do armazenamento.
     *
     * Este método limpa a lista de avaliações e atualiza o armazenamento
     * para refletir essa remoção, garantindo que nenhuma avaliação permaneça.
     */
    public void deleteAll() {
        reviews.clear();
        serializer();
    }
}
