package com.pbl.Facade;

import com.pbl.Stores.CardStore;
import com.pbl.Stores.UserStore;
import com.pbl.controller.ClientController;
import com.pbl.controller.mainController;
import com.pbl.models.Card;
import com.pbl.models.Usuario;
import com.pbl.service.CardService;
import com.pbl.service.UserService;

import java.util.Date;
import java.util.UUID;

public class CardTestFacade {

    private final UserService userService;
    private final CardService cardService;
    private final mainController controller;
    private final ClientController clientController;

    /**
     * Construtor da classe CardTestFacade.
     *
     * Inicializa os serviços de usuário e cartão, assim como os controladores.
     */
    public CardTestFacade() {
        UserStore userStore = new UserStore();
        CardStore cardStore = new CardStore();
        this.userService = new UserService(userStore);
        this.cardService = new CardService(cardStore);
        this.controller = new mainController(null, null, userService, null, null, cardService);
        this.clientController = new ClientController(null, cardService, null, null, null, userService);
    }

    /**
     * Cria um novo cartão de crédito associado a um usuário.
     *
     * @param email       O email do usuário.
     * @param cardNumber  O número do cartão.
     * @param expiryDate  A data de validade do cartão.
     * @param cvv        O código CVV do cartão.
     * @return O ID do cartão criado.
     */
    public String create(String email, String cardNumber, Date expiryDate, int cvv) {
        Usuario user = controller.getUserByEmail(email);
        Card card = controller.addCreditCard(user.getNome(), cardNumber, expiryDate, cvv, user.getID());
        return card.getID().toString();
    }

    /**
     * Obtém um cartão pelo seu ID.
     *
     * @param id O ID do cartão.
     * @return O objeto Card correspondente ao ID fornecido.
     */
    public Card getById(String id) {
        return controller.getCardById(UUID.fromString(id));
    }

    /**
     * Obtém o nome do usuário associado a um cartão pelo ID do cartão.
     *
     * @param id O ID do cartão.
     * @return O nome do usuário associado ao cartão.
     */
    public String getUserNameByCardId(String id) {
        Card card = controller.getCardById(UUID.fromString(id));
        Usuario user = controller.getUserByID(card.getUserID());
        return user.getNome();
    }

    /**
     * Obtém o número do cartão pelo ID do cartão.
     *
     * @param id O ID do cartão.
     * @return O número do cartão associado ao ID fornecido.
     */
    public String getCardNumberByCardId(String id) {
        Card card = controller.getCardById(UUID.fromString(id));
        return card.getCardNumber();
    }

    /**
     * Obtém o ano de validade do cartão pelo ID do cartão.
     *
     * @param id O ID do cartão.
     * @return O ano de validade do cartão.
     */
    public int getYearByCardId(String id) {
        Card card = controller.getCardById(UUID.fromString(id));
        return card.getExpiryDate().getYear();
    }

    /**
     * Obtém o mês de validade do cartão pelo ID do cartão.
     *
     * @param id O ID do cartão.
     * @return O mês de validade do cartão.
     */
    public int getMonthByCardId(String id) {
        Card card = controller.getCardById(UUID.fromString(id));
        return card.getExpiryDate().getMonth();
    }

    /**
     * Obtém o dia de validade do cartão pelo ID do cartão.
     *
     * @param id O ID do cartão.
     * @return O dia de validade do cartão.
     */
    public int getDayByCardId(String id) {
        Card card = controller.getCardById(UUID.fromString(id));
        return card.getExpiryDate().getDay();
    }

    /**
     * Remove todos os cartões do armazenamento.
     */
    public void deleteAllCards() {
        controller.deleteAllCards();
    }

    /**
     * Remove um cartão pelo ID.
     *
     * @param id O ID do cartão a ser removido.
     */
    public void delete(String id) {
        Card card = controller.getCardById(UUID.fromString(id));
        controller.removeCreditCard(card);
    }

    /**
     * Desabilita um cartão pelo ID.
     *
     * @param id O ID do cartão a ser desabilitado.
     */
    public void disable(String id) {
        Card card = controller.getCardById(UUID.fromString(id));
        clientController.disableCard(card);
    }

    /**
     * Obtém o status de um cartão (ativo ou inativo) pelo ID do cartão.
     *
     * @param id O ID do cartão.
     * @return true se o cartão estiver ativo, false caso contrário.
     */
    public boolean getStatusByCardId(String id) {
        Card card = controller.getCardById(UUID.fromString(id));
        return card.isActive();
    }
}