package Stores;

import Interfaces.Store;
import Resources.FilePaths;
import jdk.jshell.spi.ExecutionControl;
import models.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class CardStore implements Store<Card> {
    private List<Card> cards;

    public CardStore() {
        cards = new ArrayList<>();
    }

    @Override
    public void add(Card card){
        cards.add(card);
        serializer();
    }

    @Override
    public void remove(Card card){
        cards.remove(card);
        serializer();
    }

    @Override
    public Card getByID(UUID ID) {
        for (Card card : cards) {
            if (card.getID().equals(ID)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public List<Card> get() {
        return cards;
    }

    @Override
    public void serializer() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FilePaths.CARD_JSON)) {
            // Gravar a lista no arquivo JSON
            gson.toJson(cards, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.CARD_JSON)) {
            Type userListType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            cards = gson.fromJson(reader, userListType);
            if (cards == null) {
                // Caso o arquivo esteja vazio
                cards = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Caso o arquivo não exista ou ocorra erro
            cards = new ArrayList<>();
        }
    }

    public void updateCardStatus(Card card){
        card.desactivate();
        serializer();
    }
}
