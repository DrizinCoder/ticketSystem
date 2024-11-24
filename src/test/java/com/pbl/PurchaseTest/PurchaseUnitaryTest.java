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
package com.pbl.PurchaseTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.UUID;
import com.pbl.models.Purchase;

public class PurchaseUnitaryTest {

    private UUID userID;
    private UUID ticketID;
    private UUID cardID;
    private Purchase purchaseWithoutCard;
    private Purchase purchaseWithCard;

    @Before
    public void setUp() {
        userID = UUID.randomUUID();
        ticketID = UUID.randomUUID();
        cardID = UUID.randomUUID();

        purchaseWithoutCard = new Purchase(userID, ticketID);
        purchaseWithCard = new Purchase(userID, ticketID, cardID);
    }

    @Test
    public void testPurchaseWithoutCardCreation() {
        // Testa se a compra sem cartão foi criada corretamente
        assertNotNull(purchaseWithoutCard);
        assertEquals(userID, purchaseWithoutCard.getUser());
        assertEquals(ticketID, purchaseWithoutCard.getTicket());
        assertNotNull(purchaseWithoutCard.getID());
        assertNull(purchaseWithoutCard.getCardID());
    }

    @Test
    public void testPurchaseWithCardCreation() {
        assertNotNull(purchaseWithCard);
        assertEquals(userID, purchaseWithCard.getUser());
        assertEquals(ticketID, purchaseWithCard.getTicket());
        assertNotNull(purchaseWithCard.getID());
        assertEquals(cardID, purchaseWithCard.getCardID());
    }

    @Test
    public void testGetUser() {
        assertEquals(userID, purchaseWithoutCard.getUser());
        assertEquals(userID, purchaseWithCard.getUser());
    }

    @Test
    public void testGetTicket() {
        assertEquals(ticketID, purchaseWithoutCard.getTicket());
        assertEquals(ticketID, purchaseWithCard.getTicket());
    }

    @Test
    public void testGetID() {
        assertNotNull(purchaseWithoutCard.getID());
        assertNotNull(purchaseWithCard.getID());
        assertNotEquals(purchaseWithoutCard.getID(), purchaseWithCard.getID());
    }

    @Test
    public void testGetCardID() {
        assertNull(purchaseWithoutCard.getCardID());
        assertEquals(cardID, purchaseWithCard.getCardID());
    }
}