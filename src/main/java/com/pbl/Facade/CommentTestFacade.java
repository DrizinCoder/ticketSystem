package com.pbl.Facade;

import com.pbl.Stores.EventStore;
import com.pbl.Stores.ReviewStore;
import com.pbl.Stores.UserStore;
import com.pbl.controller.AdminController;
import com.pbl.controller.ClientController;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Review;
import com.pbl.models.Usuario;
import com.pbl.service.EventService;
import com.pbl.service.ReviewService;
import com.pbl.service.UserService;

import java.util.UUID;

public class CommentTestFacade {

    private final UserService userService;
    private final EventService eventService;
    private final ReviewService reviewService;
    private final mainController controller;
    private final AdminController adminController;
    private final ClientController clientController;

    /**
     * Construtor da classe CommentTestFacade.
     * Inicializa os serviços de usuário, evento e avaliação, assim como os controladores.
     */
    public CommentTestFacade() {
        UserStore userStore = new UserStore();
        ReviewStore reviewStore = new ReviewStore();
        EventStore eventStore = new EventStore();
        this.eventService = new EventService(eventStore, reviewStore);
        this.userService = new UserService(userStore);
        this.reviewService = new ReviewService(reviewStore);
        this.controller = new mainController(null, eventService, userService, reviewService, null, null);
        this.adminController = new AdminController(eventService, reviewService);
        this.clientController = new ClientController(eventService, null, null, reviewService, null, userService);
    }

    /**
     * Exclui todas as avaliações (comentários).
     */
    public void deleteAllComments() {
        controller.deleteAllReview();
    }

    /**
     * Cria uma nova avaliação para um evento.
     *
     * @param userId   O ID do usuário que está avaliando.
     * @param eventId  O ID do evento a ser avaliado.
     * @param rating   A avaliação dada (de 0 a 5).
     * @param content  O conteúdo da avaliação (comentário).
     * @return O ID da avaliação criada.
     */
    public String create(UUID userId, String eventId, int rating, String content) {
        Usuario user = controller.getUserByID(userId);
        Evento event = controller.getEventByID(UUID.fromString(eventId));
        Review review = controller.reviewEvent(event, user, content, rating);
        return review.getID().toString();
    }

    /**
     * Obtém uma avaliação pelo seu ID.
     *
     * @param commentId O ID da avaliação.
     * @return O objeto Review correspondente ao ID fornecido.
     */
    public Review getById(String commentId) {
        return controller.getReviewById(UUID.fromString(commentId));
    }

    /**
     * Obtém o conteúdo de uma avaliação pelo ID da avaliação.
     *
     * @param commentId O ID da avaliação.
     * @return O conteúdo do comentário.
     */
    public String getContentById(String commentId) {
        Review review = controller.getReviewById(UUID.fromString(commentId));
        return review.getComment();
    }

    /**
     * Obtém a nota (rating) de uma avaliação pelo ID da avaliação.
     *
     * @param commentId O ID da avaliação.
     * @return A nota da avaliação.
     */
    public int getRatingCommentById(String commentId) {
        Review review = controller.getReviewById(UUID.fromString(commentId));
        return review.getRating();
    }

    /**
     * Obtém o ID do usuário que fez a avaliação pelo ID da avaliação.
     *
     * @param commentId O ID da avaliação.
     * @return O ID do usuário que fez a avaliação.
     */
    public UUID getUserIdById(String commentId) {
        Review review = controller.getReviewById(UUID.fromString(commentId));
        Usuario user = controller.getUserByID(review.getUser());
        return user.getID();
    }

    /**
     * Obtém o ID do evento associado a uma avaliação.
     *
     * @param commentId O ID da avaliação.
     * @return O ID do evento associado à avaliação.
     */
    public String getEventIdById(String commentId) {
        Review review = controller.getReviewById(UUID.fromString(commentId));
        return review.getEventID().toString();
    }

    /**
     * Calcula a nota média de um evento pelo ID do evento.
     *
     * @param eventId O ID do evento.
     * @return A nota média do evento.
     */
    public double getEventRatingByEventId(String eventId) {
        Evento event = controller.getEventByID(UUID.fromString(eventId));
        return controller.calculateEventRating(event);
    }

    /**
     * Exclui uma avaliação pelo ID.
     *
     * @param commentId O ID da avaliação a ser excluída.
     */
    public void delete(String commentId) {
        Review review = controller.getReviewById(UUID.fromString(commentId));
        adminController.deleteReview(review);
    }
}
