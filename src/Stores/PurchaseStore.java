package Stores;

import Interfaces.Store;
import Resources.FilePaths;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.jshell.spi.ExecutionControl;
import models.Purchase;
import models.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PurchaseStore implements Store<Purchase> {
    private List<Purchase> purchases;

    public PurchaseStore() {
        purchases = new ArrayList<>();
    }

    @Override
    public void add(Purchase purchase){
        purchases.add(purchase);
        serializer();
    }

    @Override
    public void remove(Purchase purchase){
        purchases.remove(purchase);
        serializer();
    }

    @Override
    public Purchase getByID(UUID ID) {
        for(Purchase purchase : purchases){
            if(purchase.getID() == ID){
                return purchase;
            }
        }
        return null;
    }

    @Override
    public List<Purchase> get() {
        return purchases;
    }

    @Override
    public void serializer() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FilePaths.PURCHASE_JSON)) {
            // Gravar a lista no arquivo JSON
            gson.toJson(purchases, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.PURCHASE_JSON)) {
            Type userListType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            purchases = gson.fromJson(reader, userListType);
            if (purchases == null) {
                // Caso o arquivo esteja vazio
                purchases = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Caso o arquivo não exista ou ocorra erro
            purchases = new ArrayList<>();
        }
    }
}
