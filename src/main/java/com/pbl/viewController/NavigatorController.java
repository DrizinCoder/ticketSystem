package com.pbl.viewController;

import com.pbl.Interfaces.RequiresEvent;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Usuario;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.tools.Tool;
import java.awt.*;
import java.io.IOException;

import java.util.Stack;

public class NavigatorController {

    private Stage stage;
    private final mainController controller;
    private Usuario loggedUser;
    private Evento eventOn;
    private Stack<Scene> sceneStack = new Stack<>();

    public NavigatorController(Stage stage, mainController mainController) {
        this.stage = stage;
        this.controller = mainController;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void setEventOn(Evento event) {
        this.eventOn = event;
    }

    private void loadScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Object viewController = loader.getController();
        if (viewController instanceof RequiresMainController) {
            ((RequiresMainController) viewController).setDependencies(controller, this);
        }

        if (viewController instanceof RequiresUser) {
            ((RequiresUser) viewController).setUser(loggedUser);
        }

        if (viewController instanceof RequiresEvent) {
            ((RequiresEvent) viewController).setEvent(eventOn);
        }

        Scene scene = new Scene(root);
        getScreenDimensions();
        stage.setResizable(false);

        if(stage.getScene() != null) {
            sceneStack.push(stage.getScene());
        }

        stage.setScene(scene);
        stage.show();
    }

    private void getScreenDimensions() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setWidth(dimension.width * 0.8);
        stage.setHeight(dimension.height * 0.9);
    }

    public void showHome() throws IOException {
        loadScene("/fxml/home-view.fxml");
    }

    public void showLogin() throws IOException {
        loadScene("/fxml/login-view.fxml");
    }

    public void showSignUp() throws IOException {
        loadScene("/fxml/signup-view.fxml");
    }

    public void showMainPage() throws IOException {
        loadScene("/fxml/mainPage-view.fxml");
    }

    public void showEvent() throws IOException {
        loadScene("/fxml/event-view.fxml");
    }

    public void showPurchase() throws IOException {
        loadScene("/fxml/purchase-view.fxml");
    }

    public void showPerfil() throws IOException {
        loadScene("/fxml/perfil-view.fxml");
    }

    public void showCard() throws IOException {
        loadScene("/fxml/cardManagement-view.fxml");
    }

    public void showTickets() throws  IOException{
        loadScene("/fxml/tickets-view.fxml");
    }

    public void comeback() throws IOException {
        if(!sceneStack.empty()) {
            Scene scene = sceneStack.pop();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void popStack() throws IOException {
        sceneStack.pop();
    }

    public Usuario getUser(){
        return loggedUser;
    }

    public mainController getController() {
        return controller;
    }
}
