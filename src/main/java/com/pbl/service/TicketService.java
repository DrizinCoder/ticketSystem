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
package com.pbl.service;

import com.pbl.Stores.TicketStore;
import com.pbl.models.Evento;
import com.pbl.models.Ingresso;

import java.util.List;
import java.util.UUID;

/**
 * A classe {@code TicketService} fornece serviços relacionados ao gerenciamento de ingressos de eventos.
 * Ela permite a criação, reativação e recuperação de ingressos.
 */
public class TicketService {
    private final TicketStore ticketStore;

    /**
     * Constrói um novo {@code TicketService} com o {@code TicketStore} especificado.
     *
     * @param ticketStore o armazenamento de ingressos a ser utilizado
     */
    public TicketService(TicketStore ticketStore) {
        this.ticketStore = ticketStore;
    }

    /**
     * Cria um novo ingresso para um evento.
     *
     * @param eventID o ID do evento ao qual o ingresso está associado
     * @param assento o assento do ingresso
     * @return o ingresso criado
     */
    public Ingresso createTicket(UUID eventID, String assento) {
        List<Ingresso> tickets = ticketStore.getTicketsByEventID(eventID);

        if (tickets == null || tickets.isEmpty()) {
            Ingresso ticket = new Ingresso(eventID, assento);
            ticketStore.add(ticket);
            return ticket;
        } else {
            for (Ingresso ticketAux : tickets) {
                if (ticketAux.getAssento().equals(assento)) {
                    throw new IllegalArgumentException("Não é possível cadastrar o mesmo assento duas vezes para um único evento.");
                }
            }

            Ingresso ticket = new Ingresso(eventID, assento);
            ticketStore.add(ticket);
            return ticket;
        }
    }

    /**
     * Cria um novo ingresso para um evento específico.
     * Este método verifica se o assento já está ocupado para o evento.
     * Se o assento não estiver ocupado, o ingresso é criado e adicionado ao armazenamento.
     * Caso contrário, é lançada uma exceção indicando que o assento já foi cadastrado.
     *
     * @param eventID o UUID do evento ao qual o ingresso pertence
     * @param v o valor do ingresso
     * @param assento a designação do assento do ingresso
     * @return o objeto {@code Ingresso} criado
     * @throws IllegalArgumentException se o assento já estiver ocupado para o evento
     */
    public Ingresso createTicket(UUID eventID, double v, String assento) {
        List<Ingresso> tickets = ticketStore.getTicketsByEventID(eventID);

        if (tickets == null || tickets.isEmpty()) {
            Ingresso ticket = new Ingresso(eventID, v, assento);
            ticketStore.add(ticket);
            return ticket;
        } else {
            for (Ingresso ticketAux : tickets) {
                if (ticketAux.getAssento().equals(assento)) {
                    throw new IllegalArgumentException("Não é possível cadastrar o mesmo assento duas vezes para um único evento.");
                }
            }

            Ingresso ticket = new Ingresso(eventID, v, assento);
            ticketStore.add(ticket);
            return ticket;
        }
    }

    /**
     * Reativa um ingresso que foi previamente desativado.
     *
     * @param ticket o ingresso a ser reativado
     * @return o ingresso reativado, ou {@code null} se não for encontrado
     */
    public Ingresso reactivateTicket(Ingresso ticket) {

        if (ticket != null) {
            ticketStore.updateTicketReactivation(ticket);
        }

        return ticket;
    }

    /**
     * Recupera todos os ingressos associados a um evento específico.
     *
     * @param event o evento cujos ingressos serão recuperados
     * @return uma lista de ingressos associados ao evento
     */
    public List<Ingresso> getEventTicket(Evento event) {
        return ticketStore.getTicketsByEventID(event.getID());
    }

    /**
     * Remove todos os ingressos do armazenamento.
     * Este método exclui todos os ingressos registrados no armazenamento,
     * sem deixar nenhuma referência a eles.
     */
    public void deleteAllTickets() {
        ticketStore.deleteAll();
    }

    /**
     * Recupera um ingresso com base no seu identificador único.
     *
     * @param ticketId o UUID do ingresso a ser recuperado
     * @return o objeto {@code Ingresso} correspondente ao identificador, ou {@code null} se não for encontrado
     */
    public Ingresso getTicketById(UUID ticketId) {
        return ticketStore.getByID(ticketId);
    }

    /**
     * Desabilita um ingresso no sistema.
     * Este método atualiza o ingresso no armazenamento,
     * marcando-o como desabilitado ou inativo, conforme a implementação da classe {@code Ingresso}.
     *
     * @param ticket o objeto {@code Ingresso} a ser desabilitado
     */
    public void disableTicket(Ingresso ticket) {
        ticketStore.updateTicket(ticket);
    }
}
