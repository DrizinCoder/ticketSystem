package com.pbl.Facade;

import com.pbl.Stores.*;
import com.pbl.controller.ClientController;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Ingresso;
import com.pbl.service.EventService;
import com.pbl.service.TicketService;

import java.util.UUID;

/**
 * Um facade para testar a funcionalidade de gerenciamento de ingressos no sistema.
 * Ele fornece métodos simplificados para criar, recuperar e manipular dados de ingressos.
 */
public class TicketTestFacade {

    private final EventService eventService;
    private final TicketService ticketService;
    private final mainController controller;
    private final ClientController clientController;

    /**
     * Constrói uma nova instância de TicketTestFacade, inicializando serviços e controladores necessários.
     */
    public TicketTestFacade() {
        EventStore eventStore = new EventStore();
        ReviewStore reviewStore = new ReviewStore();
        TicketStore ticketStore = new TicketStore();
        this.eventService = new EventService(eventStore, reviewStore);
        this.ticketService = new TicketService(ticketStore);
        this.controller = new mainController(null, eventService, null, null, ticketService, null);
        this.clientController = new ClientController(eventService, null, null, null, ticketService, null);
    }

    /**
     * Exclui todos os ingressos do sistema.
     */
    public void deleteAllTickets() {
        controller.deleteAllTickets();
    }

    /**
     * Cria um novo ingresso para um evento.
     *
     * @param eventId O ID do evento para o qual o ingresso está sendo criado.
     * @param price O preço do ingresso.
     * @param seat O assento designado para o ingresso.
     * @return O ID do ingresso criado.
     */
    public String create(String eventId, double price, String seat) {
        Ingresso ticket = controller.createTicket(eventId, price, seat);
        return ticket.getID().toString();
    }

    /**
     * Recupera um ingresso pelo seu ID.
     *
     * @param ticketId O ID do ingresso.
     * @return O objeto Ingresso correspondente ao ID fornecido.
     */
    public Ingresso getById(String ticketId) {
        return controller.getTicketById(UUID.fromString(ticketId));
    }

    /**
     * Recupera o preço de um ingresso com base no seu ID.
     *
     * @param ticketId O ID do ingresso.
     * @return O preço do ingresso.
     */
    public double getPriceByTicketId(String ticketId) {
        Ingresso ticket = controller.getTicketById(UUID.fromString(ticketId));
        return ticket.getPreco();
    }

    /**
     * Recupera o assento associado a um ingresso com base no seu ID.
     *
     * @param ticketId O ID do ingresso.
     * @return O assento associado ao ingresso.
     */
    public String getSeatByTicketId(String ticketId) {
        Ingresso ticket = controller.getTicketById(UUID.fromString(ticketId));
        return ticket.getAssento();
    }

    /**
     * Recupera o ID do evento associado a um ingresso.
     *
     * @param ticketId O ID do ingresso.
     * @return O ID do evento associado ao ingresso.
     */
    public String getEventByTicketId(String ticketId) {
        Ingresso ticket = controller.getTicketById(UUID.fromString(ticketId));
        Evento event = controller.getEventByID(ticket.getEventoID());
        return event.getID().toString();
    }

    /**
     * Cancela um ingresso, desativando-o se o evento associado ainda estiver ativo.
     *
     * @param ticketId O ID do ingresso a ser cancelado.
     */
    public void cancelByTicketId(String ticketId) {
        Ingresso ticket = controller.getTicketById(UUID.fromString(ticketId));
        Evento event = controller.getEventByID(ticket.getEventoID());
        if(event.isAtivo()){
            clientController.disableTicket(ticket);
        }
    }

    /**
     * Verifica se um ingresso está ativo.
     *
     * @param ticketId O ID do ingresso.
     * @return true se o ingresso estiver ativo, false caso contrário.
     */
    public boolean getIsActive(String ticketId) {
        Ingresso ticket = controller.getTicketById(UUID.fromString(ticketId));
        return ticket.isAtivo();
    }

    /**
     * Reativa um ingresso que foi previamente cancelado.
     *
     * @param ticketId O ID do ingresso a ser reativado.
     */
    public void reactiveById(String ticketId) {
        Ingresso ticket = controller.getTicketById(UUID.fromString(ticketId));
        clientController.reactivateTicket(ticket);
    }
}
