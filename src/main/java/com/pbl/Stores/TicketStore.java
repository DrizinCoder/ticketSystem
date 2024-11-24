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
package com.pbl.Stores;

import com.pbl.Interfaces.Store;
import com.pbl.Resources.FilePaths;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pbl.models.Ingresso;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code TicketStore} implementa a interface {@code Store} para gerenciar um repositório de ingressos.
 * Ela fornece funcionalidades para adicionar, remover, buscar e serializar ingressos em um arquivo JSON.
 */
public class TicketStore implements Store<Ingresso> {
    private List<Ingresso> tickets;

    /**
     * Constrói um novo {@code TicketStore} inicializando a lista de ingressos.
     */
    public TicketStore() {
        desserializer();
    }

    /**
     * Adiciona um ingresso ao repositório e atualiza o armazenamento persistente.
     *
     * @param ticket o ingresso a ser adicionado
     */
    @Override
    public void add(Ingresso ticket) {
        tickets.add(ticket);
        serializer();
    }

    /**
     * Remove um ingresso do repositório e atualiza o armazenamento persistente.
     *
     * @param ticket o ingresso a ser removido
     */
    @Override
    public void remove(Ingresso ticket) {
        tickets.remove(ticket);
        serializer();
    }

    /**
     * Retorna um ingresso pelo seu identificador único.
     *
     * @param ID o identificador do ingresso a ser buscado
     * @return o ingresso correspondente ao ID, ou {@code null} se não encontrado
     */
    @Override
    public Ingresso getByID(UUID ID) {
        desserializer();
        for (Ingresso ticket : tickets) {
            if (ticket.getID().equals(ID)) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Retorna a lista de ingressos armazenados.
     *
     * @return a lista de ingressos
     */
    @Override
    public List<Ingresso> get() {
        desserializer();
        return tickets;
    }

    /**
     * Serializa a lista de ingressos para um arquivo JSON.
     */
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

    /**
     * Desserializa os ingressos a partir de um arquivo JSON, carregando-os na lista.
     */
    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.TICKET_JSON)) {
            Type ticketListType = new TypeToken<ArrayList<Ingresso>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            tickets = gson.fromJson(reader, ticketListType);
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

    /**
     * Atualiza um ingresso cancelando-o e salvando as mudanças no armazenamento.
     *
     * @param ticket o ingresso a ser atualizado
     */
    public void updateTicket(Ingresso ticket) {
        ticket.cancelar();
        serializer();
    }

    /**
     * Atualiza um ingresso reativando-o e salvando as mudanças no armazenamento.
     *
     * @param ticket o ingresso a ser atualizado
     */
    public void updateTicketReactivation(Ingresso ticket) {
        ticket.reativar();
        serializer();
    }

    /**
     * Retorna uma lista de ingressos associados a um determinado evento.
     *
     * @param eventID o identificador do evento cujos ingressos devem ser buscados
     * @return uma lista de ingressos correspondentes ao evento
     */
    public List<Ingresso> getTicketsByEventID(UUID eventID) {
        desserializer();
        List<Ingresso> eventTickets = new ArrayList<>();
        for (Ingresso ticket : tickets) {
            if (ticket.getEventoID().equals(eventID)) {
                eventTickets.add(ticket);
            }
        }
        return eventTickets;
    }

    /**
     * Remove todos os ingressos do armazenamento.
     * Este método limpa a lista de ingressos e atualiza o armazenamento
     * para refletir essa remoção, garantindo que nenhum ingresso permaneça.
     */
    public void deleteAll() {
        tickets.clear();
        serializer();
    }
}
