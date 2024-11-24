package com.pbl.Facade;

import com.pbl.Stores.*;
import com.pbl.controller.ClientController;
import com.pbl.controller.mainController;
import com.pbl.models.*;
import com.pbl.service.*;

import java.util.UUID;

/**
 * Um facade para testar a funcionalidade de compras no sistema. Ele fornece
 * métodos para criar, recuperar e manipular dados de compra de maneira simplificada.
 */
public class PurchaseTestFacade {

    private final EventService eventService;
    private final TicketService ticketService;
    private final PurchaseService purchaseService;
    private final UserService userService;
    private final CardService cardService;
    private final mainController controller;
    private final ClientController clientController;

    /**
     * Constrói uma nova instância de PurchaseTestFacade, inicializando serviços, stores e controladores.
     */
    public PurchaseTestFacade() {
        EventStore eventStore = new EventStore();
        UserStore userStore = new UserStore();
        ReviewStore reviewStore = new ReviewStore();
        TicketStore ticketStore = new TicketStore();
        PurchaseStore purchaseStore = new PurchaseStore();
        CardStore cardStore = new CardStore();
        this.cardService = new CardService(cardStore);
        this.userService = new UserService(userStore);
        this.purchaseService = new PurchaseService(purchaseStore, eventStore, ticketStore, userStore);
        this.eventService = new EventService(eventStore, reviewStore);
        this.ticketService = new TicketService(ticketStore);
        this.controller = new mainController(purchaseService, eventService, userService, null, ticketService,
                cardService);
        this.clientController = new ClientController(eventService, cardService, purchaseService,
                null, ticketService, userService);
    }

    /**
     * Exclui todas as compras do sistema.
     */
    public void deleteAllPurchases() {
        controller.deleteAllPurchases();
    }

    /**
     * Cria uma compra para um usuário comprando um ingresso para um evento.
     *
     * @param email O e-mail do usuário que está realizando a compra.
     * @param eventId O ID do evento para o qual o ingresso está sendo comprado.
     * @param cardId O ID do cartão utilizado para pagamento (opcional).
     * @param seat O assento selecionado para o evento.
     * @return O ID da compra criada.
     */
    public String create(String email, String eventId, String cardId, String seat) {
        Usuario user = controller.getUserByEmail(email);
        Evento event = controller.getEventByID(UUID.fromString(eventId));
        Card card = controller.getCardById(UUID.fromString(cardId));
        Purchase purchase;
        if (cardId != null) {
            purchase = controller.buyTicket(user, event, card, seat);
        } else {
            purchase = controller.buyTicket(user, event, null, seat);
        }
        return purchase.getID().toString();
    }

    /**
     * Recupera uma compra pelo seu ID.
     *
     * @param purchaseId O ID da compra.
     * @return O objeto Purchase.
     */
    public Purchase getById(String purchaseId) {
        return controller.getPurchaseById(UUID.fromString(purchaseId));
    }

    /**
     * Recupera o evento associado a uma compra.
     *
     * @param purchaseId O ID da compra.
     * @return O ID do evento associado à compra.
     */
    public String getEventByPurchaseId(String purchaseId) {
        Purchase purchase = controller.getPurchaseById(UUID.fromString(purchaseId));
        Ingresso ticket = controller.getTicketById(purchase.getTicket());
        Evento event = controller.getEventByID(ticket.getEventoID());
        return event.getID().toString();
    }

    /**
     * Recupera o login do usuário associado a uma compra.
     *
     * @param purchaseId O ID da compra.
     * @return O login do usuário que realizou a compra.
     */
    public String getUserLoginByPurchaseId(String purchaseId) {
        Purchase purchase = controller.getPurchaseById(UUID.fromString(purchaseId));
        Usuario user = controller.getUserByID(purchase.getUser());
        return user.getLogin();
    }

    /**
     * Recupera o ingresso associado a uma compra.
     *
     * @param purchaseId O ID da compra.
     * @return O objeto Ingresso associado à compra.
     */
    public Ingresso getTicketByPurchaseId(String purchaseId) {
        Purchase purchase = controller.getPurchaseById(UUID.fromString(purchaseId));
        return controller.getTicketById(purchase.getTicket());
    }

    /**
     * Recupera o cartão utilizado em uma compra.
     *
     * @param purchaseId O ID da compra.
     * @return O objeto Card utilizado na compra.
     */
    public Card getCardByPurchaseID(String purchaseId) {
        Purchase purchase = controller.getPurchaseById(UUID.fromString(purchaseId));
        return controller.getCardById(purchase.getCardID());
    }

    /**
     * Recupera o tamanho da caixa de mensagens do usuário associado a uma compra.
     *
     * @param purchaseId O ID da compra.
     * @return O número de itens na caixa de mensagens do usuário.
     */
    public int getUserMailBoxByPurchaseId(String purchaseId) {
        Purchase purchase = controller.getPurchaseById(UUID.fromString(purchaseId));
        Usuario user = controller.getUserByID(purchase.getUser());
        return user.getMailBox().size();
    }
}
