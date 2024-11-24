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
    package com.pbl.controller;

    import com.pbl.models.*;
    import com.pbl.service.*;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;

    import java.util.Date;
    import java.util.List;
    import java.util.UUID;

    /**
     * A classe {@code mainController} é responsável por gerenciar as operações
     * principais do sistema, como o registro e login de usuários, além de delegar
     * o controle de ações para {@code ClientController} ou {@code AdminController},
     * dependendo do tipo de usuário.
     *
     * Esta classe interage com diversos serviços, como compra, eventos, usuários,
     * reviews, tickets e cartões, para garantir que as funcionalidades do sistema
     * sejam executadas corretamente.
     *
     * @author Guilherme Fernandes Sardinha
     */
    public class mainController {
        protected PurchaseService purchaseService;
        protected EventService eventService;
        protected UserService userService;
        protected ReviewService reviewService;
        protected TicketService ticketService;
        protected CardService cardService;

        /**
         * Construtor da classe {@code mainController} que inicializa os serviços necessários.
         *
         * @param purchaseService o serviço responsável por gerenciar compras
         * @param eventService o serviço responsável por gerenciar eventos
         * @param userService o serviço responsável por gerenciar usuários
         * @param reviewService o serviço responsável por gerenciar reviews
         * @param ticketService o serviço responsável por gerenciar ingressos
         * @param cardService o serviço responsável por gerenciar cartões
         */
        public mainController(PurchaseService purchaseService, EventService eventService, UserService userService,
                              ReviewService reviewService, TicketService ticketService, CardService cardService) {
            this.purchaseService = purchaseService;
            this.eventService = eventService;
            this.userService = userService;
            this.reviewService = reviewService;
            this.ticketService = ticketService;
            this.cardService = cardService;
        }

        /**
         * Realiza o registro de um novo usuário no sistema.
         *
         * @param login o login do novo usuário
         * @param senha a senha do novo usuário
         * @param nome o nome completo do novo usuário
         * @param CPF o CPF do novo usuário
         * @param email o email do novo usuário
         * @param isAdmin define se o usuário é um administrador ({@code true}) ou cliente ({@code false})
         * @return o objeto {@code Usuario} que representa o novo usuário registrado
         */
        public Usuario signUp(String login, String senha, String nome, String CPF, String email, boolean isAdmin){
            return userService.singUpUser(login, senha, nome, CPF, email, isAdmin);
        }

        /**
         * Realiza o login de um usuário existente no sistema.
         * Dependendo do tipo de usuário, delega o controle de ações para o {@code ClientController}
         * ou {@code AdminController}.
         *
         * @param login o login do usuário
         * @param senha a senha do usuário
         * @return o objeto {@code Usuario} que representa o usuário logado
         */
        public boolean signIn(String login, String senha){
            Usuario user = userService.singInUser(login, senha);
            if(user == null){ return false; }
            if(!user.isAdmin()){
                ClientController clientController = new ClientController(eventService, cardService, purchaseService,
                        reviewService, ticketService, userService);
                clientController.handleClientActions(user);
            } else{
                AdminController adminController = new AdminController(eventService, reviewService);
                adminController.handleAdminActions(user);
            }
            return true;
        }

        /**
         * Calcula a média de avaliação de um evento.
         *
         * @param event o evento para o qual se deseja calcular a avaliação
         * @return a média de avaliação do evento
         */
        public double calculateEventRating(Evento event){
            return eventService.calculateRating(event);
        }

        /**
         * Busca um usuário pelo email.
         *
         * @param email o email do usuário a ser buscado
         * @return o objeto {@code Usuario} correspondente ao email fornecido
         */
        public Usuario getUserByEmail(String email){
            return userService.searchUserByEmail(email);
        }

        /**
         * Busca um usuário pelo login.
         *
         * @param login o login do usuário a ser buscado
         * @return o objeto {@code Usuario} correspondente ao login fornecido
         */
        public Usuario getUserByLogin(String login){
            return userService.searchUserByLogin(login);
        }

        /**
         * Retorna uma lista de todos os usuários do sistema.
         *
         * @return uma lista de objetos {@code Usuario}
         */
        public List<Usuario> getUsers(){
            return userService.getUsers();
        }

        /**
         * Busca um evento pelo seu ID.
         *
         * @param id o ID do evento a ser buscado
         * @return o objeto {@code Evento} correspondente ao ID fornecido
         */
        public Evento getEventByID(UUID id){
            return eventService.getEventByID(id);
        }

        /**
         * Busca um cartão pelo seu ID.
         *
         * @param id o ID do cartão a ser buscado
         * @return o objeto {@code Card} correspondente ao ID fornecido
         */
        public Card getCardById(UUID id) {
            return cardService.getCardByID(id);
        }

        /**
         * Busca um usuário pelo seu ID.
         *
         * @param userID o ID do usuário a ser buscado
         * @return o objeto {@code Usuario} correspondente ao ID fornecido
         */
        public Usuario getUserByID(UUID userID) {
            return userService.searchUserById(userID);
        }

        /**
         * Busca uma avaliação pelo seu ID.
         *
         * @param commentId o ID da avaliação a ser buscada
         * @return o objeto {@code Review} correspondente ao ID fornecido
         */
        public Review getReviewById(UUID commentId) {
            return reviewService.getReviewById(commentId);
        }


        /**
         * Busca as avaliações de um evento pelo seu ID.
         *
         * @param eventId o ID do evento a ser buscado as avaliações
         * @return o objeto {@code List<Review>} correspondente ao ID fornecido
         */
        public List<Review> getEventsReview(UUID eventId) {
            return reviewService.getReviews(eventId);
        }

        public Double calculatedRating(Evento event){
            return eventService.calculateRating(event);
        }

        /**
         * Exclui um usuário do sistema.
         *
         * @param user o objeto {@code Usuario} a ser excluído
         */
        public void deleteUser(Usuario user){
            userService.deleteUser(user);
        }

        /**
         * Exclui todos os usuários do sistema.
         */
        public void deleteAllUser(){
            userService.deleteAllUsers();
        }

        /**
         * Exclui todos os eventos do sistema.
         */
        public void deleteAllEvents(){
            eventService.deleteAllEvents();
        }

        /**
         * Exclui todos os cartões do sistema.
         */
        public void deleteAllCards() {
            cardService.deleteAllCards();
        }

        /**
         * Exclui todos os ingressos do sistema.
         */
        public void deleteAllTickets() {
            ticketService.deleteAllTickets();
        }

        /**
         * Exclui todas as avaliações do sistema.
         */
        public void deleteAllReview() {
            reviewService.deleteAllReviews();
        }

        /**
         * Busca um ingresso pelo seu ID.
         *
         * @param ticketId o ID do ingresso a ser buscado
         * @return o objeto {@code Ingresso} correspondente ao ID fornecido
         */
        public Ingresso getTicketById(UUID ticketId) {
            return ticketService.getTicketById(ticketId);
        }

        /**
         * Exclui todas as compras do sistema.
         */
        public void deleteAllPurchases() {
            purchaseService.deleteAllPurchases();
        }

        /**
         * Busca uma compra pelo seu ID.
         *
         * @param purchaseId o ID da compra a ser buscada
         * @return o objeto {@code Purchase} correspondente ao ID fornecido
         */
        public Purchase getPurchaseById(UUID purchaseId) {
            return purchaseService.getPurchaseById(purchaseId);
        }


        public List<Evento> getAllEvents(){
            return eventService.getAllEvents();
        }

        /**
         * Edita o perfil do usuário com novos valores para nome, email, senha e login.
         *
         * @param user o objeto {@code Usuario} a ser editado
         * @param name o novo nome do usuário
         * @param email o novo email do usuário
         * @param password a nova senha do usuário
         * @param login o novo login do usuário
         * @return o objeto {@code Usuario} editado
         */
        public Usuario editPerfil(Usuario user, String name, String email, String password, String login){
            return userService.editUser(user, name, email, password, login);
        }

        /**
         * Adiciona um novo cartão de crédito para o usuário.
         *
         * @param CardHolderName o nome do titular do cartão
         * @param cardBrand a bandeira do cartão
         * @param expiryDate a data de expiração do cartão
         * @param cvv o código de segurança do cartão
         * @param userId o ID do usuário associado ao cartão
         * @return o objeto {@code Card} criado
         */
        public Card addCreditCard(String CardHolderName, String cardBrand, Date expiryDate,
                                  int cvv, UUID userId){
            Card card = cardService.createCard(CardHolderName, cardBrand, expiryDate, cvv, userId);
            return card;
        }

        public List<Card>getUserCards(UUID userID){
            return cardService.getAllUserCards(userID);
        }

        /**
         * Remove um cartão de crédito associado a um ID específico.
         *
         * @param card o ID do cartão a ser removido
         */
        public void removeCreditCard(Card card){
            cardService.removeCard(card);
        }


        public List<String> getEventSeats(UUID eventID){
            return eventService.getEventSeats(eventID);
        }

        /**
         * Cria um ingresso para um evento com um valor específico e assento.
         *
         * @param eventId o ID do evento
         * @param v o valor do ingresso
         * @param a1 o assento
         * @return o objeto {@code Ingresso} criado
         */
        public Ingresso createTicket(String eventId, double v, String a1){
            return ticketService.createTicket(UUID.fromString(eventId), v, a1);
        }

        /**
         * Cria um ingresso para um evento com um assento, sem valor especificado.
         *
         * @param eventId o ID do evento
         * @param a1 o assento
         * @return o objeto {@code Ingresso} criado
         */
        public Ingresso createTicket(String eventId, String a1){
            return ticketService.createTicket(UUID.fromString(eventId), a1);
        }

        /**
         * Realiza a compra de um ingresso para um evento, associando-o ao usuário e processando o pagamento.
         *
         * @param usuario o objeto {@code Usuario} que está comprando o ingresso
         * @param event o evento
         * @param card o cartão de crédito utilizado para o pagamento (pode ser nulo)
         * @throws SecurityException se o ingresso não for encontrado
         */
        public Purchase buyTicket(Usuario usuario, Evento event, Card card, String seat){
            if(event.getDate().before(new Date())){
                throw new SecurityException("Acesso negado: Evento já realizado.");
            }

            Ingresso ticket = createTicket(event.getID().toString(), seat);

            if(ticket != null && card != null){
                return purchaseService.processPayment(usuario, event, ticket, card.getID());
            } else if(ticket != null){
                return purchaseService.processPayment(usuario, event, ticket);
            } else {
                throw new SecurityException("Acesso negado: Ingresso não encontrado.");
            }
        }

        public Card getCardByNumber(String cardNumber){
            return cardService.getCardByNumber(cardNumber);
        }

        /**
         * Remove um assento do evento especificado.
         *
         * @param seat o assento a ser removido
         * @param event o evento do qual o assento será removido
         */
        public void removeSeat(String seat, Evento event){
            System.out.println(seat);
            eventService.removeSeatEvent(event, seat);
        }

        public List<Ingresso> getUserIngressos(Usuario user){
            return purchaseService.getBoughTicket(user.getID());
        }
    }
