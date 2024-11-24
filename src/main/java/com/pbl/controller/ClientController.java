/*******************************************************************************
 * Autor: Guilherme Fernandes Sardinha
 * Componente Curricular: MI de Programação
 * Concluído em: 12/10/2024
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 * trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 * apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 * de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 * do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/
package com.pbl.controller;

import com.pbl.models.*;
import com.pbl.service.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code ClientController} gerencia as ações disponíveis para o cliente, incluindo a compra
 * e cancelamento de ingressos, gerenciamento de cartão de crédito, envio de reviews e edição de perfil.
 * Ela utiliza diferentes serviços para executar essas operações e interage com o sistema através dos
 * objetos de modelo.
 *
 * @author Guilherme Fernandes Sardinha
 */
public class ClientController {

    private final EventService eventService;
    private final CardService cardService;
    private final PurchaseService purchaseService;
    private final ReviewService reviewService;
    private final TicketService ticketService;
    private final UserService userService;

    /**
     * Construtor da classe {@code ClientController} que inicializa os serviços necessários para
     * o cliente interagir com o sistema.
     *
     * @param eventService serviço responsável pelos eventos
     * @param cardService serviço responsável pelos cartões de crédito
     * @param purchaseService serviço responsável pelas compras
     * @param reviewService serviço responsável pelas reviews
     * @param ticketService serviço responsável pelos ingressos
     * @param userService serviço responsável pelos usuários
     */
    public ClientController(EventService eventService, CardService cardService, PurchaseService purchaseService,
                            ReviewService reviewService, TicketService ticketService, UserService userService) {
        this.eventService = eventService;
        this.cardService = cardService;
        this.purchaseService = purchaseService;
        this.reviewService = reviewService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    /**
     * Exibe uma mensagem de boas-vindas ao cliente que fez login.
     *
     * @param cliente o objeto {@code Usuario} que representa o cliente logado
     */
    public void handleClientActions(Usuario cliente){
        System.out.println("Bem vindo " + cliente.getNome() + "!\n");
    }

    /**
     * Lista todos os eventos disponíveis.
     */
    public void listEvents(){
        eventService.listAvaliableEvents();
    }



    /**
     * Lista todos os ingressos comprados por um usuário.
     *
     * @param userLogin o login do usuário
     */
    public void listBoughtTicket(String userLogin){
        Usuario user = userService.searchUser(userLogin);
        List<Ingresso> tickets = purchaseService.getBoughTicket(user.getID());
        for(Ingresso ticket : tickets){
            System.out.println(user.getNome() + " " + ticket.getAssento() + " " + ticket.getPreco());
        }
    }

    /**
     * Desativa um cartão de crédito associado a um ID específico.
     *
     * @param card o cartão a ser desativado
     * @return o objeto {@code Card} desativado
     */
    public void disableCard(Card card){
        cardService.disableCard(card);
    }

    /**
     * Desativa um ingresso.
     *
     * @param ticket o ingresso a ser desativado
     */
    public void disableTicket(Ingresso ticket){
        ticketService.disableTicket(ticket);
    }

    /**
     * Reativa um ingresso.
     *
     * @param ticket o ingresso a ser reativado
     */
    public void reactivateTicket(Ingresso ticket){
        ticketService.reactivateTicket(ticket);
    }

    /**
     * Adiciona uma review para um evento, após a sua conclusão.
     *
     * @param event o evento
     * @param user o usuário que está adicionando a review
     * @param comment o comentário da review
     * @param rating a nota atribuída ao evento
     * @return o objeto {@code Review} criado
     * @throws SecurityException se o evento ainda estiver ativo
     */
    public Review reviewEvent(Evento event, Usuario user, String comment, int rating){
        if(event != null && !event.isAtivo()){
            return reviewService.makeReview(user, comment, rating, event.getID());
        } else if (event != null && event.isAtivo()) {
            throw new SecurityException("Comentário só pode ser adicionando após a realização do evento.");
        }
        return null;
    }

    /**
     * Exibe todas as reviews associadas a um evento específico.
     *
     * @param eventID o identificador de evento
     * @return uma lista de {@code Review} associadas ao evento
     */
    public List<Review> showReviews(UUID eventID){
        List<Review> reviews = reviewService.getReviews(eventID);
        if(!reviews.isEmpty()){
            for (Review review : reviews) {
                System.out.println(review.getUser() + " " + review.getRating());
                System.out.println(review.getComment());
            }
        }
        return reviews;
    }


}
