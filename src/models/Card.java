package models;

import java.util.Date;
import java.util.UUID;

public class Card {
    protected String cardHolderName;
    protected String cardBrand;
    protected String cardNumber;
    protected String accountNumber;
    protected Date expiryDate;
    protected int cvv;
    protected boolean isActive;
    protected UUID ID;
    protected UUID userID;

    public Card(String cardHolderName, String cardBrand, String cardNumber, String accountNumber, Date expiryDate, int cvv, UUID userID) {
        this.cardHolderName = cardHolderName;
        this.cardBrand = cardBrand;
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.isActive = true;
        this.ID = UUID.randomUUID();
        this.userID = userID;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public int getCvv() {
        return cvv;
    }

    public UUID getID() {
        return ID;
    }

    public UUID getUserID() { return userID; }

    public boolean isActive() {
        return isActive;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void desactivate() {
        isActive = false;
    }
}
