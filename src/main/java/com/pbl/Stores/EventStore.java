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
import com.pbl.models.Evento;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code EventStore} implementa a interface {@code Store} para gerenciar um repositório de eventos.
 * Ela fornece funcionalidades para adicionar, remover, buscar e serializar eventos em um arquivo JSON.
 */
public class EventStore implements Store<Evento> {
    private List<Evento> events;

    /**
     * Constrói um novo {@code EventStore} inicializando a lista de eventos.
     */
    public EventStore() {
        desserializer();
    }

    /**
     * Adiciona um evento ao repositório e atualiza o armazenamento persistente.
     *
     * @param event o evento a ser adicionado
     */
    @Override
    public void add(Evento event) {
        events.add(event);
        serializer();
    }

    /**
     * Remove um evento do repositório e atualiza o armazenamento persistente.
     *
     * @param event o evento a ser removido
     */
    @Override
    public void remove(Evento event) {
        events.remove(event);
        serializer();
    }

    /**
     * Retorna um evento pelo seu identificador único.
     *
     * @param ID o identificador do evento a ser buscado
     * @return o evento correspondente ao ID, ou {@code null} se não encontrado
     */
    @Override
    public Evento getByID(UUID ID) {
        desserializer();
        for (Evento event : events) {
            if (event.getID().equals(ID)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Retorna a lista de eventos armazenados.
     *
     * @return a lista de eventos
     */
    @Override
    public List<Evento> get() {
        desserializer();
        return events;
    }

    /**
     * Serializa a lista de eventos para um arquivo JSON.
     */
    @Override
    public void serializer() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FilePaths.EVENT_JSON)) {
            // Gravar a lista no arquivo JSON
            gson.toJson(events, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desserializa os eventos a partir de um arquivo JSON, carregando-os na lista.
     */
    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.EVENT_JSON)) {
            Type eventListType = new TypeToken<ArrayList<Evento>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            events = gson.fromJson(reader, eventListType);
            if (events == null) {
                // Caso o arquivo esteja vazio
                events = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Caso o arquivo não exista ou ocorra erro
            events = new ArrayList<>();
        }
    }

    /**
     * Busca um evento pelo seu nome.
     *
     * @param name o nome do evento a ser buscado
     * @return o evento correspondente ao nome, ou {@code null} se não encontrado
     */
    public Evento getEventByName(String name) {
        desserializer();
        for (Evento event : events) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Atualiza os assentos de um evento específico.
     *
     * @param event é o evento cujos assentos serão atualizados
     * @param seat      o assento a ser adicionado ao evento
     */
    public void updateSeats(Evento event, String seat) {
        if (event != null) {
            event.addSeats(seat);
            serializer();
        }
    }

    /**
     * Remove um assento de um evento específico.
     * Este método verifica se o evento fornecido não é nulo e, em caso afirmativo,
     * remove o assento especificado do evento. Após a remoção, o armazenamento é atualizado.
     *
     * @param event o evento do qual o assento será removido
     * @param seat o assento a ser removido do evento
     */
    public void updateRemoveSeats(Evento event, String seat) {
        if (event != null) {
            Evento evento = getByID(event.getID());
            evento.removeSeats(seat);// Remove o assento do evento
            serializer(); // Atualiza o armazenamento
        }
    }

    /**
     * Remove todos os eventos do armazenamento.
     * Este método limpa a lista de eventos e atualiza o armazenamento,
     * garantindo que nenhum evento permaneça.
     */
    public void deleteAll() {
        events.clear(); // Limpa a lista de eventos
        serializer(); // Atualiza o armazenamento
    }
}
