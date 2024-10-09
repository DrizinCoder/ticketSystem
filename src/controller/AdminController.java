package controller;

import models.Evento;
import models.Review;
import models.Usuario;
import service.*;

import java.util.Date;

public class AdminController {

    private final EventService eventService;
    private final ReviewService reviewService;

    public AdminController(EventService eventService, ReviewService reviewService) {
        this.eventService = eventService;
        this.reviewService = reviewService;
    }

    public void handleAdminActions(Usuario admin){
        System.out.println("Bem vindo " + admin.getNome() + "!\n");
    }

    public Evento createEvent(Usuario user, String name, String description, Date date){
        Date dateAux = new Date();
        if(date.after(dateAux)){
            Evento event = eventService.createEvent(user, name, description, date);
            return event;
        } else{
            throw new SecurityException("Data inválida.");
        }
    }

    public void listEvents(){
        eventService.listAvaliableEvents();
    }

    public void deleteEvent(String eventName){
        eventService.deleteEvent(eventName);
    }

    public void deleteReview(Review review){
        reviewService.removeReview(review);
    }

}
