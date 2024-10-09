package service;

import Stores.EventStore;
import Stores.TicketStore;
import models.Evento;
import models.Ingresso;
import models.Usuario;

import java.util.List;
import java.util.UUID;

public class TicketService {
    private final TicketStore ticketStore;

    public TicketService(TicketStore ticketStore) {
        this.ticketStore = ticketStore;
    }

    public Ingresso createTicket(UUID eventID, String assento, UUID userID){
        Ingresso ticket = new Ingresso(eventID, assento, userID);
        ticketStore.add(ticket);

        return ticket;
    }

    public Ingresso reactivateTicket(UUID ticketID){
        Ingresso ticket = ticketStore.getByID(ticketID);

        if(ticket != null){
            ticketStore.updateTicketReactivation(ticket);
        }

        return ticket;
    }

    public List<Ingresso> getBoughTicket(Usuario user){
        return ticketStore.getTicketsByUserID(user.getID());
    }

    public List<Ingresso> getEventTicket(Evento event){
        return ticketStore.getTicketsByUserID(event.getID());
    }
}
