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

/**
 * Controlador de navegação que gerencia a mudança de cenas na interface gráfica.
 * A classe carrega e exibe diferentes telas FXML, além de gerenciar a pilha de cenas para navegação entre elas.
 */
public class NavigatorController {

    private Stage stage;
    private final mainController controller;
    private Usuario loggedUser;
    private Evento eventOn;
    private Stack<Scene> sceneStack = new Stack<>();

    /**
     * Constrói o NavigatorController com a janela principal (Stage) e o controlador principal.
     *
     * @param stage A janela principal onde as cenas são exibidas.
     * @param mainController O controlador principal da aplicação.
     */
    public NavigatorController(Stage stage, mainController mainController) {
        this.stage = stage;
        this.controller = mainController;
    }

    /**
     * Define o usuário logado.
     *
     * @param loggedUser O usuário logado.
     */
    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Define o evento atualmente selecionado.
     *
     * @param event O evento selecionado.
     */
    public void setEventOn(Evento event) {
        this.eventOn = event;
    }

    /**
     * Carrega uma cena FXML e a exibe na janela principal.
     * Além disso, injeta as dependências necessárias (como o controlador principal e o usuário).
     *
     * @param fxmlPath O caminho do arquivo FXML para a cena a ser carregada.
     * @throws IOException Se ocorrer um erro ao carregar o arquivo FXML.
     */
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

    /**
     * Ajusta as dimensões da janela para 80% da largura e 90% da altura da tela.
     */
    private void getScreenDimensions() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setWidth(dimension.width * 0.8);
        stage.setHeight(dimension.height * 0.9);
    }

    /**
     * Exibe a tela inicial.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela inicial.
     */
    public void showHome() throws IOException {
        loadScene("/fxml/home-view.fxml");
    }

    /**
     * Exibe a tela de login.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela de login.
     */
    public void showLogin() throws IOException {
        loadScene("/fxml/login-view.fxml");
    }

    /**
     * Exibe a tela de cadastro.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela de cadastro.
     */
    public void showSignUp() throws IOException {
        loadScene("/fxml/signup-view.fxml");
    }

    /**
     * Exibe a página principal.
     *
     * @throws IOException Se ocorrer um erro ao carregar a página principal.
     */
    public void showMainPage() throws IOException {
        loadScene("/fxml/mainPage-view.fxml");
    }

    /**
     * Exibe a tela de detalhes do evento.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela do evento.
     */
    public void showEvent() throws IOException {
        loadScene("/fxml/event-view.fxml");
    }

    /**
     * Exibe a tela de compras.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela de compras.
     */
    public void showPurchase() throws IOException {
        loadScene("/fxml/purchase-view.fxml");
    }

    /**
     * Exibe a tela de perfil do usuário.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela de perfil.
     */
    public void showPerfil() throws IOException {
        loadScene("/fxml/perfil-view.fxml");
    }

    /**
     * Exibe a tela de gerenciamento de cartões de crédito.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela de cartões.
     */
    public void showCard() throws IOException {
        loadScene("/fxml/cardManagement-view.fxml");
    }

    /**
     * Exibe a tela de ingressos do usuário.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela de ingressos.
     */
    public void showTickets() throws  IOException{
        loadScene("/fxml/tickets-view.fxml");
    }

    /**
     * Retorna para a cena anterior na pilha de cenas.
     * Caso não haja uma cena anterior, nada acontece.
     *
     * @throws IOException Se ocorrer um erro ao carregar a cena anterior.
     */
    public void comeback() throws IOException {
        if(!sceneStack.empty()) {
            Scene scene = sceneStack.pop();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Remove a cena atual da pilha de cenas.
     *
     * @throws IOException Se ocorrer um erro ao remover a cena da pilha.
     */
    public void popStack() throws IOException {
        sceneStack.pop();
    }

    /**
     * Retorna o usuário logado.
     *
     * @return O usuário logado.
     */
    public Usuario getUser(){
        return loggedUser;
    }

    /**
     * Retorna o controlador principal.
     *
     * @return O controlador principal.
     */
    public mainController getController() {
        return controller;
    }

    /**
     * Exibe a tela da caixa de entrada de mensagens do usuário.
     *
     * @throws IOException Se ocorrer um erro ao carregar a tela de caixa de entrada.
     */
    public void showMailBox() throws IOException {
        loadScene("/fxml/mailBox-view.fxml");
    }
}
