package service;

import Stores.EventStore;
import Stores.ReviewStore;
import models.Evento;
import models.Review;
import models.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventService {
    private final EventStore eventStore;
    private final ReviewStore reviewStore;

    public EventService(EventStore eventStore, ReviewStore reviewStore) {
        this.eventStore = eventStore;
        this.reviewStore = reviewStore;
    }

    public Evento createEvent(Usuario usuario, String nome, String descricao, Date data){
        if (usuario.isAdmin() &&  eventStore.getEventByName(nome) == null){
            Evento evento = new Evento(nome, descricao, data);
            eventStore.add(evento);

            return evento;
        } else if (eventStore.getEventByName(nome) != null){
            throw new SecurityException("Evento já criado no sistema");
        } else {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
    }

    public void adicionarAssentoEvento(String nomeEvento, String assento) {
        eventStore.updateSeats(nomeEvento, assento);
    }

    public List<Evento> listAvaliableEvents() {
        List<Evento> eventosDisponiveis = new ArrayList<>();

        System.out.println("Lista de eventos disponiveis");
        // Percorre a lista e exibe os eventos ativos
        for (Evento evento : eventStore.get()) {
            if (evento.isAtivo()) {
                eventosDisponiveis.add(evento);
                System.out.println("Nome: " + evento.getName() + " -> " + evento.getDescription());
            }
        }
        return eventosDisponiveis;
    }

    public void calculateRating(Evento event) {
        int brutalRating = 0;
        for (Review review : reviewStore.get()){
            brutalRating += review.getRating();
        }

        double rate = (double) brutalRating /reviewStore.get().size();
        event.setRate (rate);
    }

    public Evento getEvent(String nameEvent){
        return eventStore.getEventByName(nameEvent);
    }

    public void deleteEvent(String eventName){
        Evento evento = eventStore.getEventByName(eventName);
        eventStore.remove(evento);
    }

}
