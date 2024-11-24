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
package com.pbl.CommentTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.UUID;
import com.pbl.models.Review;

public class CommentUnitaryTest {

    private Review review;
    private UUID eventID;

    @Before
    public void setUp() {
        String userName = "Guilherme";
        String comment = "Ótimo evento!";
        int rating = 5;
        eventID = UUID.randomUUID();

        review = new Review(userName, comment, rating, eventID);
    }

    @Test
    public void testReviewCreation() {
        assertNotNull(review);
        assertEquals("Guilherme", review.getUser());
        assertEquals("Ótimo evento!", review.getComment());
        assertEquals(5, review.getRating());
        assertEquals(eventID, review.getEventID());
        assertNotNull(review.getID());
    }

    @Test
    public void testSetUserName() {
        review.setUser("Maria");
        assertEquals("Maria", review.getUser());
    }

    @Test
    public void testGetEventID() {
        assertEquals(eventID, review.getEventID());
    }

    @Test
    public void testGetComment() {
        assertEquals("Ótimo evento!", review.getComment());
    }

    @Test
    public void testGetRating() {
        assertEquals(5, review.getRating());
    }

    @Test
    public void testGetID() {
        assertNotNull(review.getID());
        assertEquals(review.getID().getClass(), UUID.class);
    }
}

