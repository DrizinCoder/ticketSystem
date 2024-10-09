package service;

import Stores.EventStore;
import Stores.PurchaseStore;
import Stores.TicketStore;
import Stores.UserStore;
import models.Evento;
import models.Ingresso;
import models.Purchase;
import models.Usuario;

import java.util.UUID;

public class PurchaseService {
    private final PurchaseStore PurchaseStore;
    private final EventStore eventStore;
    private final TicketStore ticketStore;
    private final UserStore userStore;

    public PurchaseService(PurchaseStore purchaseStore, EventStore eventStore, TicketStore ticketStore, UserStore userStore) {
        this.PurchaseStore = purchaseStore;
        this.eventStore = eventStore;
        this.ticketStore = ticketStore;
        this.userStore = userStore;
    }

    public Boolean validatePurchase(Purchase purchase) {
        if(PurchaseStore.get().isEmpty()){
            return false;
        } else if(PurchaseStore.get().contains(purchase)) {
            return true;
        } else{
            return false;
        }
    }

    public String generatePaySlip(Purchase purchase) {
        Ingresso ticket = ticketStore.getByID(purchase.getTicket());
        Evento evento = eventStore.getByID(ticket.getEventoID());
        Usuario user = userStore.getByID(purchase.getUser());
        return "----------------------------------------------------------------------------------------------------\n" +
                "COMPROVANTE DE COMPRA\nPurchase Nº " + purchase.getID() + "\nbought by: " + user.getNome() + "\nEvent: "
                + evento.getName() + "\nTicket chair: " + ticket.getAssento() +
                "\n----------------------------------------------------------------------------------------------------\n";
    }

    public Purchase processPayment(Usuario usuario, Evento event, Ingresso ticket) {

        if (event != null && event.isAtivo()) {
            Purchase purchase = new Purchase(usuario.getID(), ticket.getID());
            PurchaseStore.add(purchase);
            if(validatePurchase(purchase)) {
                String emailNotification = generatePaySlip(purchase);
                userStore.updateMailBox(emailNotification, usuario.getID());
            }

            return purchase;

        } else if (event == null) {
            throw new SecurityException("Acesso negado: Evento nº não encontrado.");
        } else {
            throw new SecurityException("Acesso negado: Evento nº " + event.getID() + " ja passou da data prevista.");
        }
    }

    public Purchase processPayment(Usuario usuario, Evento event, Ingresso ticket, UUID cardID) {

        if (event != null && event.isAtivo()) {
            Purchase purchase = new Purchase(usuario.getID(), ticket.getID(), cardID);
            PurchaseStore.add(purchase);
            if(validatePurchase(purchase)) {
                String emailNotification = generatePaySlip(purchase);
                userStore.updateMailBox(emailNotification, usuario.getID());
            }

            return purchase;

        } else if (event == null) {
            throw new SecurityException("Acesso negado: Evento nº não encontrado.");
        } else {
            throw new SecurityException("Acesso negado: Evento nº " + event.getID() + " ja passou da data prevista.");
        }
    }

    public boolean cancelarCompra(Evento event, Ingresso ticket) {
        if(event.isAtivo() && ticket.isAtivo()){
            ticketStore.updateTicket(ticket);
            return true;
        }

        return false;
    }
}
