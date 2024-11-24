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
package com.pbl.service;

import com.pbl.Stores.EventStore;
import com.pbl.Stores.PurchaseStore;
import com.pbl.Stores.TicketStore;
import com.pbl.Stores.UserStore;
import com.pbl.models.Evento;
import com.pbl.models.Ingresso;
import com.pbl.models.Purchase;
import com.pbl.models.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code PurchaseService} fornece serviços relacionados ao gerenciamento de compras de ingressos.
 * Ela permite a validação de compras, o processamento de pagamentos e o cancelamento de compras.
 */
public class PurchaseService {
    private final PurchaseStore purchaseStore;
    private final EventStore eventStore;
    private final TicketStore ticketStore;
    private final UserStore userStore;

    /**
     * Constrói um novo {@code PurchaseService} com o {@code PurchaseStore}, {@code EventStore},
     * {@code TicketStore} e {@code UserStore} especificados.
     *
     * @param purchaseStore o armazenamento de compras a ser utilizado
     * @param eventStore    o armazenamento de eventos a ser utilizado
     * @param ticketStore   o armazenamento de ingressos a ser utilizado
     * @param userStore     o armazenamento de usuários a ser utilizado
     */
    public PurchaseService(PurchaseStore purchaseStore, EventStore eventStore, TicketStore ticketStore, UserStore userStore) {
        this.purchaseStore = purchaseStore;
        this.eventStore = eventStore;
        this.ticketStore = ticketStore;
        this.userStore = userStore;
    }

    /**
     * Valida uma compra verificando se ela já existe no armazenamento.
     *
     * @param purchase a compra a ser validada
     * @return {@code true} se a compra for válida, {@code false} caso contrário
     */
    public Boolean validatePurchase(Purchase purchase) {
        List<Purchase> purchases = purchaseStore.get();
        if (!purchaseStore.get().isEmpty()) {
            for (Purchase purchaseAux : purchases) {
                if (purchaseAux.getID().equals(purchase.getID())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gera um recibo de pagamento para uma compra.
     *
     * @param purchase a compra para a qual o recibo será gerado
     * @return uma string contendo o recibo da compra
     */
    public String generatePaySlip(Purchase purchase) {
        Ingresso ticket = ticketStore.getByID(purchase.getTicket());
        Evento evento = eventStore.getByID(ticket.getEventoID());
        Usuario user = userStore.getByID(purchase.getUser());
        return "----------------------------------------------------------------------------------------------------\n" +
                "COMPROVANTE DE COMPRA\nPurchase Nº " + purchase.getID() + "\nbought by: " + user.getNome() + "\nEvent: "
                + evento.getName() + "\nTicket chair: " + ticket.getAssento() +
                "\n----------------------------------------------------------------------------------------------------\n";
    }

    /**
     * Processa o pagamento de uma compra de ingresso.
     *
     * @param usuario o usuário que está realizando a compra
     * @param event   o evento para o qual o ingresso está sendo comprado
     * @param ticket  o ingresso a ser comprado
     * @return a compra processada
     * @throws SecurityException se o evento não for encontrado ou se já passou da data prevista
     */
    public Purchase processPayment(Usuario usuario, Evento event, Ingresso ticket) {
        if (event != null && event.isAtivo()) {
            Purchase purchase = new Purchase(usuario.getID(), ticket.getID());
            purchaseStore.add(purchase);
            if (validatePurchase(purchase)) {
                String emailNotification = generatePaySlip(purchase);
                userStore.updateMailBox(emailNotification, usuario.getID());
            }

            return purchase;
        } else if (event == null) {
            throw new SecurityException("Acesso negado: Evento nº não encontrado.");
        } else {
            throw new SecurityException("Acesso negado: Evento nº " + event.getID() + " já passou da data prevista.");
        }
    }

    /**
     * Processa o pagamento de uma compra de ingresso utilizando um cartão.
     *
     * @param usuario o usuário que está realizando a compra
     * @param event   o evento para o qual o ingresso está sendo comprado
     * @param ticket  o ingresso a ser comprado
     * @param cardID  o ID do cartão utilizado para a compra
     * @return a compra processada
     * @throws SecurityException se o evento não for encontrado ou se já passou da data prevista
     */
    public Purchase processPayment(Usuario usuario, Evento event, Ingresso ticket, UUID cardID) {
        if (event != null && event.isAtivo()) {
            Purchase purchase = new Purchase(usuario.getID(), ticket.getID(), cardID);
            purchaseStore.add(purchase);
            if (validatePurchase(purchase)) {
                String emailNotification = generatePaySlip(purchase);
                userStore.updateMailBox(emailNotification, usuario.getID());
            }

            return purchase;
        } else if (event == null) {
            throw new SecurityException("Acesso negado: Evento nº não encontrado.");
        } else {
            throw new SecurityException("Acesso negado: Evento nº " + event.getID() + " já passou da data prevista.");
        }
    }

    /**
     * Obtém uma lista de ingressos comprados por um usuário específico.
     *
     * @param userID o UUID do usuário cujos ingressos comprados estão sendo buscados
     * @return uma lista de ingressos comprados pelo usuário, ou uma lista vazia se nenhum ingresso for encontrado
     */
    public List<Ingresso> getBoughTicket(UUID userID) {
        List<Ingresso> tickets = new ArrayList<>();
        List<UUID> ticketsID = purchaseStore.getBoughtTicket(userID);
        for(UUID ticketID : ticketsID) {
            tickets.add(ticketStore.getByID(ticketID));
        }
        return tickets;
    }

    /**
     * Remove todos os registros de compras do sistema.
     */
    public void deleteAllPurchases() {
        purchaseStore.deleteAll();
    }

    /**
     * Obtém uma compra específica pelo seu ID.
     *
     * @param purchaseId o UUID da compra que está sendo buscada
     * @return o objeto {@code Purchase} correspondente ao ID fornecido, ou {@code null} se não encontrado
     */
    public Purchase getPurchaseById(UUID purchaseId) {
        return purchaseStore.getByID(purchaseId);
    }
}
