package Stores;

import Interfaces.Store;
import Resources.FilePaths;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Evento;
import models.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventStore implements Store<Evento> {
    private List<Evento> events;

    public EventStore() {
        events = new ArrayList<>();
    }

    @Override
    public void add(Evento event){
        events.add(event);
        serializer();
    }

    @Override
    public void remove(Evento event){
        events.remove(event);
        serializer();
    }

    @Override
    public Evento getByID(UUID ID) {
        for (Evento event : events) {
            if(event.getID().equals(ID)){ return event; }
        }
        return null;
    }

    @Override
    public List<Evento> get() {
        return events;
    }

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

    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.EVENT_JSON)) {
            Type userListType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            events = gson.fromJson(reader, userListType);
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

    // Busca evento pelo nome
    public Evento getEventByName(String name){
        for (Evento event : events) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    public void updateSeats(String eventName, String seat){
        Evento event = getEventByName(eventName);
        if (event != null) {
            event.addSeats(seat);
            serializer();
        }
    }
}
