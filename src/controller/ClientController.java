package controller;

import models.*;
import service.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ClientController {

    private final EventService eventService;
    private final CardService cardService;
    private final PurchaseService purchaseService;
    private final ReviewService reviewService;
    private final TicketService ticketService;
    private final UserService userService;

    public ClientController(EventService eventService, CardService cardService, PurchaseService purchaseService,
                            ReviewService reviewService, TicketService ticketService, UserService userService) {
        this.eventService = eventService;
        this.cardService = cardService;
        this.purchaseService = purchaseService;
        this.reviewService = reviewService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public void handleClientActions(Usuario cliente){
        System.out.println("Bem vindo " + cliente.getNome() + "!\n");
    }

    public void listEvents(){
        eventService.listAvaliableEvents();
    }

    public void buyTicket(Usuario usuario, String nameEvent, UUID cardID){
        Evento event = eventService.getEvent(nameEvent);
        List<Ingresso> tickets = ticketService.getEventTicket(event);
        Ingresso ticket = null;

        for(String seat : event.getSeats()){
            for(Ingresso ticketAux : tickets){
                if(!seat.equals(ticketAux.getAssento())){
                    ticket = new Ingresso(event.getID(), seat, usuario.getID());
                }
            }
        }

        if(ticket != null && cardID != null){
            purchaseService.processPayment(usuario, event, ticket, cardID);
        } else if(ticket != null){
            purchaseService.processPayment(usuario, event, ticket);
        } else {
            throw new SecurityException("Acesso negado: Ingresso não encontrado.");
        }
    }

    public void listBoughtTicket(String userLogin){
        Usuario user = userService.searchUser(userLogin);
        List<Ingresso> tickets = ticketService.getBoughTicket(user);
        for(Ingresso ticket : tickets){
            System.out.println(ticket.getUserID() + " " + ticket.getAssento() + " " + ticket.getPreco());
        }
    }

    public void cancelTicket(String userLogin, String nameEvent){
        Usuario user = userService.searchUser(userLogin);
        Evento event = eventService.getEvent(nameEvent);
        List<Ingresso> tickets = ticketService.getBoughTicket(user);

        for(Ingresso ticket : tickets){
            if(ticket.getEventoID() == event.getID() && ticket.getUserID() == user.getID()){
                purchaseService.cancelarCompra(event, ticket);
            }
        }
    }

    public Card addCreditCard(String CardHolderName, String cardBrand, String cardNumber, String accountNumber, Date expiryDate,
                              int cvv, UUID userId){
        Card card = cardService.createCard(CardHolderName, cardBrand, cardNumber, accountNumber, expiryDate, cvv, userId);
        return card;
    }

    public void removeCreditCard(UUID cardID){
        cardService.removeCard(cardID);
    }

    public Card disableCreditCard(UUID cardID){
        Card card = cardService.disableCard(cardID);
        return card;
    }

    public Review reviewEvent(String nomeEvento, Usuario user, String comment, int rating){
        Evento event = eventService.getEvent(nomeEvento);
        if(event != null && !event.isAtivo()){
            Review review = reviewService.makeReview(user, comment, rating, event.getID());
            return review;
        } else if (event != null && event.isAtivo()) {
            throw new SecurityException("Comentário só pode ser adicionando após a realização do evento.");
        }
        return null;
    }

    public void showReviews(String nomeEvento){
        Evento event = eventService.getEvent(nomeEvento);
        List<Review> reviews = reviewService.getReviews(event.getID());
        if(!reviews.isEmpty()){
            for (Review review : reviews) {
                System.out.println(review.getUser() + " " + review.getRating());
                System.out.println(review.getComment());
            }
        }
    }

    public Usuario editPerfil(Usuario user, String name, String email, String password, String login){
        Usuario userAux = userService.editUser(user, name, email, password, login);
        return userAux;
    }
}
