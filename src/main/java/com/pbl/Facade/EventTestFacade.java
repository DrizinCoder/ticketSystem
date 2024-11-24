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

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EventTestFacade {

    private final EventStore eventStore;
    private final UserService userService;
    private final EventService eventService;
    private final mainController controller;
    private final AdminController adminController;
    private final ClientController clientController;

    /**
     * Construtor da classe EventTestFacade.
     *
     * Inicializa os serviços de usuário, evento e avaliação, além dos controladores.
     */
    public EventTestFacade() {
        UserStore userStore = new UserStore();
        ReviewStore reviewStore = new ReviewStore();
        ReviewService reviewService = new ReviewService(reviewStore);
        this.eventStore = new EventStore();
        this.userService = new UserService(userStore);
        this.eventService = new EventService(eventStore, reviewStore);
        this.controller = new mainController(null, eventService, userService, null, null, null);
        this.adminController = new AdminController(eventService, null);
        this.clientController = new ClientController(eventService, null, null, reviewService, null, null);
    }

    /**
     * Remove um assento de um evento específico.
     *
     * @param seat O assento a ser removido.
     * @param id   O ID do evento.
     */
    public void removeSeatByEventId(String seat, String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        controller.removeSeat(seat, event);
    }

    /**
     * Adiciona um assento a um evento específico.
     *
     * @param seat O assento a ser adicionado.
     * @param id   O ID do evento.
     */
    public void addSeatByEventId(String seat, String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        adminController.addSeat(seat, event);
    }

    /**
     * Cria um novo evento.
     *
     * @param loginAdmin  O login do administrador.
     * @param name        O nome do evento.
     * @param description A descrição do evento.
     * @param date        A data do evento.
     * @return O ID do evento criado.
     */
    public String create(String loginAdmin, String name, String description, Date date) {
        Usuario user = controller.getUserByLogin(loginAdmin);
        Evento event = adminController.createEvent(user, name, description, date);
        return event.getID().toString();
    }

    /**
     * Obtém um evento pelo ID.
     *
     * @param id O ID do evento.
     * @return O objeto Evento correspondente ao ID.
     */
    public Evento getById(String id) {
        return controller.getEventByID(UUID.fromString(id));
    }

    /**
     * Obtém o nome de um evento pelo ID.
     *
     * @param id O ID do evento.
     * @return O nome do evento.
     */
    public String getNameByEventId(String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        return event.getName();
    }

    /**
     * Obtém a lista de assentos de um evento pelo ID.
     *
     * @param id O ID do evento.
     * @return Uma lista de assentos disponíveis para o evento.
     */
    public List<String> getSeatsByEventId(String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        return event.getSeats();
    }

    /**
     * Obtém a descrição de um evento pelo ID.
     *
     * @param id O ID do evento.
     * @return A descrição do evento.
     */
    public String getDescriptionByEventId(String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        return event.getDescription();
    }

    /**
     * Obtém o ano da data de um evento pelo ID.
     *
     * @param id O ID do evento.
     * @return O ano da data do evento.
     */
    public int getYearByEventId(String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        return event.getDate().getYear();
    }

    /**
     * Obtém o mês da data de um evento pelo ID.
     *
     * @param id O ID do evento.
     * @return O mês da data do evento.
     */
    public int getMonthByEventId(String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        return event.getDate().getMonth();
    }

    /**
     * Obtém o dia da data de um evento pelo ID.
     *
     * @param id O ID do evento.
     * @return O dia da data do evento.
     */
    public int getDayByEventId(String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        return event.getDate().getDay();
    }

    /**
     * Verifica se um evento está ativo.
     *
     * @param id O ID do evento.
     * @return true se o evento estiver ativo, false caso contrário.
     */
    public boolean getIsActiveByEventId(String id) {
        Evento event = controller.getEventByID(UUID.fromString(id));
        return event.isAtivo();
    }

    /**
     * Exclui todos os eventos.
     */
    public void deleteAllEvents() {
        controller.deleteAllEvents();
    }

    /**
     * Adiciona um evento diretamente ao banco de dados com uma data passada.
     *
     * @param name        O nome do evento.
     * @param description A descrição do evento.
     * @param date        A data do evento (passada).
     * @return O ID do evento adicionado.
     */
    public String addEventInDataBaseWithPastDate(String name, String description, Date date) {
        Evento event = new Evento(name, description, date);
        eventStore.add(event);
        return event.getID().toString();
    }

    /**
     * Obtém a quantidade de comentários (avaliações) de um evento pelo ID.
     *
     * @param eventId O ID do evento.
     * @return O número de comentários associados ao evento.
     */
    public int getCommentQuantityByEventId(String eventId) {
        List<Review> reviews = clientController.showReviews(UUID.fromString(eventId));
        return reviews.size();
    }
}
