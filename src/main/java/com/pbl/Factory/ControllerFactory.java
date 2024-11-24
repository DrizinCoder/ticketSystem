package com.pbl.Factory;

import com.pbl.Stores.*;
import com.pbl.controller.mainController;
import com.pbl.service.*;

public class ControllerFactory {

    public mainController createController(){
        // Inicializa as Stores necessárias
        CardStore cardStore = new CardStore();
        EventStore eventStore = new EventStore();
        PurchaseStore purchaseStore = new PurchaseStore();
        ReviewStore reviewStore = new ReviewStore();
        TicketStore ticketStore = new TicketStore();
        UserStore userStore = new UserStore();

        // Inicializando os serviços necessários
        PurchaseService purchaseService = new PurchaseService(purchaseStore, eventStore, ticketStore, userStore);
        EventService eventService = new EventService(eventStore, reviewStore);
        UserService userService = new UserService(userStore);
        ReviewService reviewService = new ReviewService(reviewStore);
        TicketService ticketService = new TicketService(ticketStore);
        CardService cardService = new CardService(cardStore);

        // Cria uma instância do mainController com os serviços
        return new mainController(purchaseService, eventService, userService,
                reviewService, ticketService, cardService);
    }

}
