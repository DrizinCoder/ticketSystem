package models;

import java.util.UUID;

public class Review {
    protected String userName;
    protected String comment;
    protected int rating;
    protected UUID eventID;
    protected UUID ID;

    public Review(String userName, String comment, int rating, UUID eventID) {
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;
        this.eventID = eventID;
        this.ID = UUID.randomUUID();
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String userName) {
        this.userName = userName;
    }

    public UUID getID() { return ID; }

    public UUID getEventID() { return eventID; }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
