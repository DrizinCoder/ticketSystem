package Stores;

import Interfaces.Store;
import Resources.FilePaths;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Review;
import models.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewStore implements Store<Review> {
    private List<Review> reviews;

    public ReviewStore() {
        reviews = new ArrayList<>();
    }

    @Override
    public void add(Review review) {
        reviews.add(review);
        serializer();
    }

    @Override
    public void remove(Review review){
        reviews.remove(review);
        serializer();
    }

    @Override
    public Review getByID(UUID ID) {
        for (Review review : reviews) {
            if (review.getID().equals(ID)) {
                return review;
            }
        }
        return null;
    }

    @Override
    public List<Review> get() {
        return reviews;
    }

    @Override
    public void serializer() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FilePaths.REVIEW_JSON)) {
            // Gravar a lista no arquivo JSON
            gson.toJson(reviews, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void desserializer() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FilePaths.REVIEW_JSON)) {
            Type userListType = new TypeToken<ArrayList<Usuario>>(){}.getType();
            // Carregar do arquivo JSON para a lista
            reviews = gson.fromJson(reader, userListType);
            if (reviews == null) {
                // Caso o arquivo esteja vazio
                reviews = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Caso o arquivo não exista ou ocorra erro
            reviews = new ArrayList<>();
        }
    }

    public List<Review> getReviewsByEventID(UUID eventID) {
        List<Review> eventReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getEventID().equals(eventID)) {
                eventReviews.add(review);
            }
        }

        return eventReviews;
    }
}
