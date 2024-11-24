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
package com.pbl.TicketTest;

import com.pbl.models.Evento;
import com.pbl.models.Ingresso;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TicketUnitaryTest {

    @Test
    public void testCriarIngresso() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = new Ingresso(evento.getID(), 100.0, "A1");

        assertNotNull(ingresso);
        assertEquals(evento.getID(), ingresso.getEventoID());
        assertEquals(100.0, ingresso.getPreco(), 0.0001);
        assertEquals("A1", ingresso.getAssento());
        assertTrue(ingresso.isAtivo());
    }

    @Test
    public void testCancelarIngresso() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 10);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = new Ingresso(evento.getID(), 100.0, "A1");

        assertTrue(ingresso.cancelar());
        assertFalse(ingresso.isAtivo());
    }

    @Test
    public void testCancelarIngressoEventoPassado() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 10);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = new Ingresso(evento.getID(), 100.0, "A1");

        if(evento.isAtivo()){
            assertFalse(ingresso.cancelar());
        }
        assertTrue(ingresso.isAtivo());
    }

    @Test
    public void testReativarIngresso() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = new Ingresso(evento.getID(), 100.0, "A1");

        ingresso.cancelar();
        assertFalse(ingresso.isAtivo());

        ingresso.reativar();
        assertTrue(ingresso.isAtivo());
    }

    @Test
    public void testIngressoDuplicado() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso1 = new Ingresso(evento.getID(), 100.0, "A1");
        Ingresso ingresso2 = new Ingresso(evento.getID(), 100.0, "A1");

        assertEquals(ingresso1, ingresso2);
        assertEquals(ingresso1.hashCode(), ingresso2.hashCode());
    }
}

