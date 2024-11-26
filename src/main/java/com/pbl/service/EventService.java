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
import com.pbl.Stores.ReviewStore;
import com.pbl.models.Evento;
import com.pbl.models.Review;
import com.pbl.models.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code EventService} fornece serviços relacionados ao gerenciamento de eventos.
 * Ela permite a criação, listagem, atualização e remoção de eventos, além de calcular a avaliação dos eventos com base em revisões.
 */
public class EventService {
    private final EventStore eventStore;
    private final ReviewStore reviewStore;

    /**
     * Constrói um novo {@code EventService} com o {@code EventStore} e {@code ReviewStore} especificados.
     *
     * @param eventStore o armazenamento de eventos a ser utilizado
     * @param reviewStore o armazenamento de avaliações a ser utilizado
     */
    public EventService(EventStore eventStore, ReviewStore reviewStore) {
        this.eventStore = eventStore;
        this.reviewStore = reviewStore;
    }

    /**
     * Cria um novo evento, se o usuário for um administrador e o evento não existir.
     *
     * @param usuario   o usuário que está criando o evento
     * @param nome      o nome do evento
     * @param descricao a descrição do evento
     * @param data      a data do evento
     * @return o evento criado
     * @throws SecurityException se o usuário não for administrador ou se o evento já existir
     */
    public Evento createEvent(Usuario usuario, String nome, String descricao, Date data) {
        if (usuario.isAdmin() && eventStore.getEventByName(nome) == null) {
            Evento evento = new Evento(nome, descricao, data);
            eventStore.add(evento);
            return evento;
        } else if (eventStore.getEventByName(nome) != null) {
            throw new SecurityException("Evento já criado no sistema");
        } else {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
    }

    /**
     * Adiciona um assento a um evento existente.
     *
     * @param event o evento ao qual o assento será adicionado
     * @param seat    o assento a ser adicionado
     */
    public void adicionarAssentoEvento(Evento event, String seat) {
        eventStore.updateSeats(event, seat);
    }

    public void removeSeatEvent(Evento event, String seat) {
        System.out.println(seat);
        eventStore.updateRemoveSeats(event, seat);
    }

    /**
     * Lista todos os eventos disponíveis (ativos).
     *
     * @return uma lista de eventos disponíveis
     */
    public List<Evento> listAvaliableEvents() {
        List<Evento> eventosDisponiveis = new ArrayList<>();

        System.out.println("Lista de eventos disponíveis");
        // Percorre a lista e exibe os eventos ativos
        for (Evento evento : eventStore.get()) {
            if (evento.isAtivo()) {
                eventosDisponiveis.add(evento);
                System.out.println("Nome: " + evento.getName() + " -> " + evento.getDescription());
            }
        }
        return eventosDisponiveis;
    }

    /**
     * Calcula a avaliação média de um evento com base nas revisões disponíveis.
     *
     * @param event o evento para o qual a avaliação será calculada
     */
    public double calculateRating(Evento event) {
        int brutalRating = 0;
        List<Review> auxList = new ArrayList<>();
        for (Review review : reviewStore.get()) {
            if(review.getEventID().equals(event.getID())){
                brutalRating += review.getRating();
                auxList.add(review);
            }
        }

        double rate = (double) brutalRating / auxList.size();
        event.setRate(rate);
        return rate;
    }

    /**
     * Retorna um objeto {@code Evento} com base no seu identificador único.
     *
     * @param id o UUID que identifica o evento
     * @return o objeto {@code Evento} correspondente ao UUID fornecido, ou {@code null} se o evento não for encontrado
     */
    public Evento getEventByID(UUID id) {
        return eventStore.getByID(id);
    }

    /**
     * Remove um evento pelo seu nome.
     *
     * @param eventName o nome do evento a ser removido
     */
    public void deleteEvent(String eventName) {
        Evento evento = eventStore.getEventByName(eventName);
        eventStore.remove(evento);
    }

    /**
     * Remove todos os eventos armazenados no sistema.
     * Este método limpa todos os registros de eventos no {@code eventStore}.
     */
    public void deleteAllEvents(){
        eventStore.deleteAll();
    }

    public List<Evento> getAllEvents() {
        return eventStore.get();
    }

    public List<String> getEventSeats(UUID eventID){
        Evento evento = eventStore.getByID(eventID);
        return evento.getSeats();
    }
}
