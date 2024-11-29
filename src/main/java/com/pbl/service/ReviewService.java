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

import com.pbl.Stores.ReviewStore;
import com.pbl.models.Review;
import com.pbl.models.Usuario;

import java.util.List;
import java.util.UUID;

/**
 * A classe {@code ReviewService} fornece serviços relacionados ao gerenciamento de avaliações de eventos.
 * Ela permite a criação, remoção e recuperação de avaliações.
 */
public class ReviewService {
    private final ReviewStore reviewStore;

    /**
     * Constrói um novo {@code ReviewService} com o {@code ReviewStore} especificado.
     *
     * @param reviewStore o armazenamento de avaliações a ser utilizado
     */
    public ReviewService(ReviewStore reviewStore) {
        this.reviewStore = reviewStore;
    }

    /**
     * Cria uma nova avaliação para um evento.
     *
     * @param user    o usuário que está fazendo a avaliação
     * @param comment o comentário da avaliação
     * @param rating  a classificação dada pelo usuário (de 1 a 5)
     * @param eventID o ID do evento para o qual a avaliação está sendo feita
     * @return a avaliação criada
     */
    public Review makeReview(Usuario user, String comment, int rating, UUID eventID) {
        Review review = new Review(user.getID(), comment, rating, eventID);
        reviewStore.add(review);
        return review;
    }

    /**
     * Remove uma avaliação do armazenamento.
     *
     * @param review a avaliação a ser removida
     */
    public void removeReview(Review review) {
        reviewStore.remove(review); // talvez deva fazer uma verificação aqui logo
    }

    /**
     * Recupera todas as avaliações para um evento específico.
     *
     * @param eventID o ID do evento cujas avaliações serão recuperadas
     * @return uma lista de avaliações associadas ao evento
     */
    public List<Review> getReviews(UUID eventID) { return reviewStore.getReviewsByEventID(eventID); }

    /**
     * Remove todos os registros de avaliações do sistema.
     */
    public void deleteAllReviews() {
        reviewStore.deleteAll();
    }

    /**
     * Obtém uma avaliação específica pelo seu ID.
     *
     * @param commentId o UUID da avaliação que está sendo buscada
     * @return o objeto {@code Review} correspondente ao ID fornecido, ou {@code null} se não encontrado
     */
    public Review getReviewById(UUID commentId) {
        return reviewStore.getByID(commentId);
    }
}
