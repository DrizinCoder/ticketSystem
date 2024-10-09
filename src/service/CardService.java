package service;

import Stores.CardStore;
import models.Card;

import java.util.Date;
import java.util.UUID;

public class CardService {
    private final CardStore cardStore;

    public CardService(CardStore cardStore) {
        this.cardStore = cardStore;
    }

    public Card createCard(String CardHolderName, String cardBrand, String cardNumber, String accountNumber, Date expiryDate,
                           int cvv, UUID userId) {

        Card card = new Card(CardHolderName, cardBrand, cardNumber, accountNumber, expiryDate, cvv, userId);
        cardStore.add(card);

        return card;
    }

    public void removeCard(UUID cardId) {
        Card card = cardStore.getByID(cardId);
        cardStore.remove(card);
    }

    public Card disableCard(UUID cardId) {
        Card card = cardStore.getByID(cardId);

        if(card != null) {
            cardStore.updateCardStatus(card);
        }

        return card;
    }
}
