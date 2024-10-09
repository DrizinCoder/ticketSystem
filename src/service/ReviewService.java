package service;

import Stores.ReviewStore;
import models.Review;
import models.Usuario;

import java.util.List;
import java.util.UUID;

public class ReviewService {
    private final ReviewStore reviewStore;

    public ReviewService(ReviewStore reviewStore) {
        this.reviewStore = reviewStore;
    }

    public Review makeReview(Usuario user, String comment, int rating, UUID eventID){
        Review review = new Review(user.getLogin(), comment, rating , eventID);
        reviewStore.add(review);
        return review;
    }

    public void removeReview(Review review){
        reviewStore.remove(review); //talvez deva fazer uma verificação aqui logo
    }

    public List<Review> getReviews(UUID eventID){
        return reviewStore.getReviewsByEventID(eventID);
    }
}
