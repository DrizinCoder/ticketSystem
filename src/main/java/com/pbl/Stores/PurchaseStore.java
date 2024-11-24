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
import com.pbl.models.Purchase;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A classe {@code PurchaseStore} implementa a interface {@code Store} para gerenciar um repositório de compras.
 * Ela fornece funcionalidades para adicionar, remover, buscar e serializar compras em um arquivo JSON.
 */
public class PurchaseStore implements Store<Purchase> {
    private List<Purchase> purchases;

    /**
     * Constrói um novo {@code PurchaseStore} inicializando a lista de compras.
     */
    public PurchaseStore() {
        desserializer();
    }

    /**
     * Adiciona uma compra ao repositório e atualiza o armazenamento persistente.
     *
     * @param purchase a compra a ser adicionada
     */
    @Override
    public void add(Purchase purchase) {
        purchases.add(purchase);
        serializer();
    }

    /**
     * Remove uma compra do repositório e atualiza o armazenamento persistente.
     *
     * @param purchase a compra a ser removida
     */
    @Override
    public void remove(Purchase purchase) {
        purchases.remove(purchase);
        serializer();
    }

    /**
     * Retorna uma compra pelo seu identificador único.
     *
     * @param ID o identificador da compra a ser buscada
     * @return a compra correspondente ao ID, ou {@code null} se não encontrada
     */
    @Override
    public Purchase getByID(UUID ID) {
        desserializer();
        for (Purchase purchase : purchases) {
            if (purchase.getID().equals(ID)) {
                return purchase;
            }
        }
        return null;
    }

    /**
     * Retorna a lista de compras armazenadas.
     *
     * @return a lista de compras
     */
    @Override
    public List<Purchase> get() {
        desserializer();
        return purchases;
    }

    /**
     * Serializa a lista de compras para um arquivo JSON.
     */
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

    /**
     * Desserializa as compras a partir de um arquivo JSON, carregando-as na lista.
     */
    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.PURCHASE_JSON)) {
            Type purchaseListType = new TypeToken<ArrayList<Purchase>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            purchases = gson.fromJson(reader, purchaseListType);
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

    public List<UUID> getBoughtTicket(UUID userID) {
        desserializer();
        List<UUID> ticketsID = new ArrayList<>();
        for(Purchase purchase : purchases) {
            if(purchase.getUser().equals(userID)) {
                ticketsID.add(purchase.getTicket());
            }
        }
        return ticketsID;
    }

    public void deleteAll() {
        purchases.clear();
        serializer();
    }
}
