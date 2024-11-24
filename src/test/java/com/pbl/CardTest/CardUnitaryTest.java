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
package com.pbl.CardTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.UUID;
import com.pbl.models.Card;

public class CardUnitaryTest {

    private Card card;
    private UUID userId;

    @Before
    public void setUp() {
        // Preparar dados para teste
        String cardHolderName = "John Doe";
        String cardNumber = "1234 5678 9012 3456";
        Date expiryDate = new Date();
        int cvv = 123;
        userId = UUID.randomUUID();

        card = new Card(cardHolderName, cardNumber, expiryDate, cvv, userId);
    }

    @Test
    public void testCardCreation() {
        assertNotNull(card);
        assertEquals("John Doe", card.getCardHolderName());
        assertEquals("1234 5678 9012 3456", card.getCardNumber());
        assertEquals(userId, card.getUserID());
        assertTrue(card.isActive());
    }

    @Test
    public void testDeactivateCard() {
        card.desactivate();
        assertFalse(card.isActive());
    }

    @Test
    public void testGetCardDetails() {
        assertEquals("1234 5678 9012 3456", card.getCardNumber());
        assertEquals(userId, card.getUserID());
        assertNotNull(card.getExpiryDate());
        assertEquals(card.getID().getClass(), UUID.class);
    }

}
