package tests;

import Stores.UserStore;
import controller.mainController;
import models.Usuario;
import org.junit.Before;
import org.junit.Test;
import service.UserService;

import java.util.List;

import static org.junit.Assert.*;

public class UserTest {
    private mainController mcontroller;
    private UserService userService;
    private UserStore userStore;

    @Before
    public void setUp() {
        userStore =  new UserStore();
        userService = new UserService(userStore);
        mcontroller = new mainController(null, null, userService,
                null, null, null);
    }

    @Test
    public void duplicateUserInDataBaseTest() throws Exception {
        mcontroller.signUp("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        try{
            mcontroller.signUp("johndoe", "senha456", "John Doe", "12345678901", "john.doe@example.com", false);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Login, email e/ou cpf já está em uso.");
        }
    }

    @Test
    public void createUserInDataBaseTest() throws Exception {
        Usuario user = mcontroller.signUp("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Usuario createdUser = userStore.getByID(user.getID());

        assertNotNull(createdUser);
        assertEquals(user.getID(), createdUser.getID());
    }

    @Test
    public void createUsersInDataBaseTest() throws Exception {
        mcontroller.signUp("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        mcontroller.signUp("lucas", "senha456", "John Doe", "12345678902", "lucas.doe@example.com", false);

        List<Usuario> users = userStore.get();

        assertEquals(2, users.size());
    }


    @Test
    public void deleteUserTest() throws Exception {
        Usuario user = mcontroller.signUp("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        userStore.remove(user);

        Usuario createdUser = userStore.getByID(user.getID());

        assertNull(createdUser);
    }

    /*@Test
    public void updateUserTest() throws Exception {
        Usuario firstUser = mcontroller.signUp("john", "senha123", "John", "12345678901", "john@example.com", false);
        Usuario secondUser = mcontroller.signUp("lucas", "senha456", "Lucas", "12345678902", "lucas@example.com", false);

        firstUser.setEmail(secondUser.getEmail());

        try{
            userStore.update(firstUser);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Email já está em uso.");
        }

        firstUser.setEmail("john123@example.com");

        userController.update(firstUser);

        Usuario updatedUser = userController.getById(firstUser.getID());

        assertEquals(updatedUser.getEmail(), firstUser.getEmail());

    }*/
}