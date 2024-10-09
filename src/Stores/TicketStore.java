package Stores;

import Interfaces.Store;
import Resources.FilePaths;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Evento;
import models.Ingresso;
import models.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketStore implements Store<Ingresso> {
    private List<Ingresso> tickets;

    public TicketStore() {
        tickets = new ArrayList<>();
    }

    @Override
    public void add(Ingresso ticket) {
        tickets.add(ticket);
        serializer();
    }

    @Override
    public void remove(Ingresso ticket){
        tickets.remove(ticket);
        serializer();
    }

    @Override
    public Ingresso getByID(UUID ID) {
        for (Ingresso ticket : tickets) {
            if (ticket.getID().equals(ID)) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public List<Ingresso> get() {
        return tickets;
    }

    @Override
    public void serializer() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FilePaths.TICKET_JSON)) {
            // Gravar a lista no arquivo JSON
            gson.toJson(tickets, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.TICKET_JSON)) {
            Type userListType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            tickets = gson.fromJson(reader, userListType);
            if (tickets == null) {
                // Caso o arquivo esteja vazio
                tickets = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Caso o arquivo não exista ou ocorra erro
            tickets = new ArrayList<>();
        }
    }

    // Atualiza ticket
    public void updateTicket(Ingresso ticket) {
        ticket.cancelar();
        serializer();
    }

    public void updateTicketReactivation(Ingresso ticket) {
        ticket.reativar();
        serializer();
    }

    // Outra maneira de capturar os tickets
    public List<Ingresso> getTicketsByUserID(UUID userID) {
        List<Ingresso> userTickets = new ArrayList<>();
        for(Ingresso ticket : tickets) {
            if (ticket.getUserID().equals(userID)) {
                userTickets.add(ticket);
            }
        }
        return userTickets;
    }

    public List<Ingresso> getTicketsByEventID(UUID eventID) {
        List<Ingresso> userTickets = new ArrayList<>();
        for(Ingresso ticket : tickets) {
            if (ticket.getEventoID().equals(eventID)) {
                userTickets.add(ticket);
            }
        }
        return userTickets;
    }
}
