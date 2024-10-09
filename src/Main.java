import Stores.*;
import models.Evento;
import models.Ingresso;
import models.Usuario;
import service.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*******************************************************************************
 Autor: Guilherme Fernandes Sardinha
 Componente Curricular: MI de Programação
 Concluido em: 12/09/2024
 Declaro que este código foi elaborado por mim de forma individual e não contém nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ******************************************************************************************/

public class Main {
    public static void main(String[] args) {
        EventStore eventStore = new EventStore();
        UserStore userStore = new UserStore();
        PurchaseStore purchaseStore = new PurchaseStore();
        CardStore cardStore = new CardStore();
        TicketStore ticketStore = new TicketStore();
        ReviewStore reviewStore = new ReviewStore();

        EventService eventService = new EventService(eventStore, reviewStore);
        UserService userService = new UserService(userStore);
        PurchaseService purchaseService = new PurchaseService(purchaseStore, eventStore, ticketStore, userStore);
        TicketService ticketService = new TicketService(ticketStore);
        CardService cardService = new CardService(cardStore);

        userStore.desserializer();

        // Adicionar um novo usuário e salvar no JSON
        Usuario user = userService.singUpUser("g123", "124", "ggg", "123", "gg@gmail.com", true);
        Usuario user2 = userService.singUpUser("a123", "123", "ala", "124", "ala@gmail.com", false);

        System.out.println(user2.getNome() + user2.getLogin() + user2.getSenha() + user2.getEmail() + user.getID());

        user2 = null;
        // Atualizar um usuário e salvar no JSON
        Usuario userToUpdate = userStore.getByLogin("a123");
        if (userToUpdate != null) {
            userStore.updateUser(userToUpdate, "novoNome", "novoEmail", "novaSenha", "novoLogin");
            System.out.println(userToUpdate.getNome() + userToUpdate.getLogin() + userToUpdate.getSenha() + userToUpdate.getEmail() + user.getID());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 10);
        Date data = calendar.getTime();

        Usuario user3 = userStore.getByLogin("novoLogin");
        Evento event = eventService.createEvent(user, "festa", "do balacobaco", data);
        eventService.adicionarAssentoEvento("festa", "a1");
        Ingresso ticket = ticketService.createTicket(event.getID(), "a1", user3.getID());
        event.setfalse();
        eventService.calculateRating(event);
        System.out.println(event.getRate());
        cardService.createCard("novoNOme", "visa", "777", "123", data, 123, user3.getID());
        List<Ingresso> tickets = ticketStore.getTicketsByUserID(user3.getID());
        for(Ingresso s : tickets) {
            System.out.println(s.getUserID() + " " + s.getAssento() + " " + s.getPreco());
        }
    }
}