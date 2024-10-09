/*******************************************************************************
 Autor: Guilherme Fernandes Sardinha
 Componente Curricular: MI de Programação
 Concluido em: 12/09/2024
 Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/
package controller;

import models.Usuario;
import service.*;

// Classe Controller
public class mainController {

    protected PurchaseService purchaseService;
    protected EventService eventService;
    protected UserService userService;
    protected ReviewService reviewService;
    protected TicketService ticketService;
    protected CardService cardService;

    // Construtor da classe controller
    public mainController(PurchaseService purchaseService, EventService eventService, UserService userService,
                          ReviewService reviewService, TicketService ticketService, CardService cardService) {

        this.purchaseService = purchaseService;
        this.eventService = eventService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.ticketService = ticketService;
        this.cardService = cardService;

    }

    public Usuario signUp(String login, String senha, String nome, String CPF, String email, boolean isAdmin){
        Usuario user = userService.singUpUser(login, senha, nome, CPF, email, isAdmin);
        return user;
    }

    public Usuario signIn(String login, String senha){
        Usuario user = userService.singInUser(login, senha);
        if(!user.isAdmin()){
            ClientController clientController = new ClientController( eventService,  cardService, purchaseService,
                    reviewService,  ticketService,  userService);
            clientController.handleClientActions(user);
        } else{
            AdminController adminController = new AdminController(eventService, reviewService);
            adminController.handleAdminActions(user);
        }
        return user;
    }
}
