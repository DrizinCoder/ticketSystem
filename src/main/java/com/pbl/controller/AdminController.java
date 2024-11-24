/*******************************************************************************
 * Autor: Guilherme Fernandes Sardinha
 * Componente Curricular: MI de Programação
 * Concluído em: 12/10/2024
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 * trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 * apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 * de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 * do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/
package com.pbl.controller;

import com.pbl.models.Evento;
import com.pbl.models.Review;
import com.pbl.models.Usuario;
import com.pbl.service.*;

import java.util.Date;

/**
 * A classe {@code AdminController} é responsável por gerenciar as ações realizadas por um
 * administrador, como a criação, listagem e exclusão de eventos, além da remoção de reviews.
 * Ela utiliza serviços especializados para realizar essas operações, garantindo que o administrador
 * tenha controle completo sobre as funcionalidades relacionadas a eventos e reviews.
 *
 * @author Guilherme Fernandes Sardinha
 */
public class AdminController {

    private final EventService eventService;
    private final ReviewService reviewService;

    /**
     * Construtor da classe {@code AdminController} que inicializa os serviços
     * necessários para manipular eventos e reviews.
     *
     * @param eventService o serviço responsável por gerenciar eventos
     * @param reviewService o serviço responsável por gerenciar reviews
     */
    public AdminController(EventService eventService, ReviewService reviewService) {
        this.eventService = eventService;
        this.reviewService = reviewService;
    }

    /**
     * Exibe uma mensagem de boas-vindas ao administrador que fez login.
     *
     * @param admin o objeto {@code Usuario} que representa o administrador logado
     */
    public void handleAdminActions(Usuario admin){
        System.out.println("Bem vindo " + admin.getNome() + "!\n");
    }

    /**
     * Cria um novo evento, se a data fornecida for válida (posterior à data atual).
     *
     * @param user o usuário que está criando o evento
     * @param name o nome do evento
     * @param description a descrição do evento
     * @param date a data do evento
     * @return o objeto {@code Evento} recém-criado
     * @throws SecurityException se a data do evento for anterior à data atual
     */
    public Evento createEvent(Usuario user, String name, String description, Date date){
        Date dateAux = new Date();
        if(date.after(dateAux)){
            return eventService.createEvent(user, name, description, date);
        } else{
            throw new SecurityException("Data inválida.");
        }
    }

    /**
     * Adiciona um assento ao evento especificado.
     *
     * @param seat o assento a ser adicionado
     * @param event o evento ao qual o assento será adicionado
     */
    public void addSeat(String seat, Evento event){
        eventService.adicionarAssentoEvento(event, seat);
    }

    /**
     * Lista todos os eventos disponíveis no sistema.
     */
    public void listEvents(){
        eventService.listAvaliableEvents();
    }

    /**
     * Exclui um evento específico baseado no nome fornecido.
     *
     * @param eventName o nome do evento a ser excluído
     */
    public void deleteEvent(String eventName){
        eventService.deleteEvent(eventName);
    }

    /**
     * Remove um review específico.
     *
     * @param review o objeto {@code Review} a ser removido
     */
    public void deleteReview(Review review){
        reviewService.removeReview(review);
    }
}
