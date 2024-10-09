package models;

import java.util.Date;
import java.util.UUID;

public class Purchase {
    protected UUID userID;
    protected UUID ticketID;
    protected UUID ID;
    protected UUID cardID;

    public Purchase(UUID userID, UUID ticketID) {
        this.userID = userID;
        this.ticketID = ticketID;
        this.ID = UUID.randomUUID();
        this.cardID = null;
    }

    public Purchase(UUID userID, UUID ticketID, UUID cardID) {
        this.userID = userID;
        this.ticketID = ticketID;
        this.ID = UUID.randomUUID();
        this.cardID = cardID;
    }

    public UUID getUser() {
        return userID;
    }

    public UUID getTicket() {
        return ticketID;
    }

    public UUID getID() {return ID;}
}
