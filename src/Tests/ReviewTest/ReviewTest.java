package test;

import java.lang.Exception;

import Stores.EventStore;
import Stores.ReviewStore;
import Stores.UserStore;
import controller.AdminController;
import controller.ClientController;
import controller.mainController;
import models.Evento;
import models.Review;
import models.Usuario;
import org.junit.Before;
import org.junit.Test;
import service.EventService;
import service.ReviewService;
import service.UserService;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ReviewTest {
    public ClientController clientController;
    public AdminController adminController;
    public mainController mcontroller;
    public UserService userService;
    public UserStore userStore;
    public ReviewService reviewService;
    public ReviewStore reviewStore;
    public EventService eventService;
    public EventStore eventStore;

    @Before
    public void setUp() {
        userStore = new UserStore();
        userService = new UserService(userStore);
        reviewStore = new ReviewStore();
        reviewService = new ReviewService(reviewStore);
        eventStore = new EventStore();
        eventService = new EventService(eventStore, reviewStore);
        clientController = new ClientController(null, null, null, reviewService,
                null, userService );
        adminController = new AdminController(eventService, reviewService);
        mcontroller = new mainController(null, eventService, userService, reviewService,
                null, null);
    }

    @Test
    public void commentCreateTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.SEPTEMBER, 30);
        Date data = calendar.getTime();

        Usuario admin = mcontroller.signUp("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Usuario user = mcontroller.signUp("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Evento event = adminController.createEvent(admin,"Show de Rock", "Banda XYZ", data);

        Review commentCreated = clientController.reviewEvent(event.getName(), user, "Muito bom!", 5);

        Review comment = reviewStore.getByID(commentCreated.getID());

        assertEquals(commentCreated.getID(), comment.getID());
        assertEquals(commentCreated.getUser(), comment.getUser());
        assertEquals(commentCreated.getEventID(), comment.getEventID());
        assertEquals(commentCreated.getComment(), comment.getComment());
        assertEquals(commentCreated.getRating(), comment.getRating());
    }

    @Test
    public void commentDeleteTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.SEPTEMBER, 30);
        Date data = calendar.getTime();

        Usuario admin = mcontroller.signUp("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Usuario user = mcontroller.signUp("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Evento event = adminController.createEvent(admin,"Show de Rock", "Banda XYZ", data);

        Review comment = clientController.reviewEvent(event.getName(), user,"Muito bom!", 5);

        Review commentCreated = reviewStore.getByID(comment.getID());

        assertEquals(commentCreated.getID(), comment.getID());

        adminController.deleteReview(commentCreated);

        assertNull(reviewStore.getByID(commentCreated.getID()));
    }

    @Test
    public void testCreateFutureEventComment() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.SEPTEMBER, 30);
        Date data = calendar.getTime();

        Usuario admin = mcontroller.signUp("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Usuario user = mcontroller.signUp("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Evento event = adminController.createEvent(admin,"Show de Rock", "Banda XYZ", data);

        Exception exception = assertThrows(SecurityException.class, () -> {
            Review commentCreated = clientController.reviewEvent(event.getName(), user,"Muito bom!", 5);
            assertNull(commentCreated);
        });

        assertEquals("Comentário só pode ser adicionando após a realização do evento.", exception.getMessage());
    }
}