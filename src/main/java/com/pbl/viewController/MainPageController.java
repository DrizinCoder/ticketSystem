package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;

import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MainPageController implements RequiresMainController, RequiresUser, LanguageChange {

    private mainController mainController;
    private NavigatorController navigatorController;
    private Usuario user;

    @FXML
    private Label username;

    @FXML
    private Label events;

    @FXML
    private Button profile;

    @FXML
    private Button editButton;

    @FXML
    private Button seePurchasesButton;

    @FXML
    private Button changeLanguage;

    @FXML
    private Button logoutButton;

    @FXML
    private Button mailbox;

    @FXML
    private VBox eventContainer;

    @FXML
    private Button FontButton;

    private Text eventDateMensage;

    /**
     * Inicializa o controlador da página principal.
     * Registra o controlador como ouvinte para mudanças de idioma e define a mensagem de data do evento.
     */
    public void initialize() {
        LanguageManager.registerListener(this);
        eventDateMensage = new Text();
        eventDateMensage.setText(LanguageManager.getString("events.date"));
    }

    /**
     * Define as dependências necessárias para o controlador.
     * Este método é chamado para inicializar as dependências, como o controlador principal e o controlador de navegação,
     * e carrega os eventos ao iniciar a página.
     *
     * @param mainController O controlador principal da aplicação.
     * @param navigatorController O controlador responsável pela navegação entre as páginas.
     */
    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        loadEvents();
        LanguageManager.notifyListeners();
    }

    /**
     * Define o usuário atual na página principal.
     * Atualiza as informações de boas-vindas com base no idioma atual.
     *
     * @param user O usuário a ser definido.
     */
    public void setUser(Usuario user) {
        this.user = user;
        updateUserData();
    }

    /**
     * Atualiza os dados do usuário na interface gráfica, exibindo uma mensagem de boas-vindas ou erro de acordo com o idioma atual.
     */
    private void updateUserData(){
        if (user != null && LanguageManager.languageController == 0) {
            username.setText("Bem vindo!\n");
        } else if(user != null && LanguageManager.languageController == 1){
            username.setText("Welcome!\n");
        }else if(LanguageManager.languageController == 0){
            username.setText("USUARIO NAO ENCONTRADO!");
        } else {
            username.setText("USER NOT FOUND");
        }
    }

    /**
     * Lida com o evento de clique no botão "Logout".
     * Esse método navega para a página anterior quando o usuário decide sair da aplicação.
     *
     * @param actionEvent O evento de ação gerado pelo clique no botão.
     * @throws IOException Se ocorrer algum erro durante a navegação.
     */
    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

    /**
     * Altera o idioma da aplicação entre inglês e português (Brasil).
     * Notifica todos os ouvintes sobre a mudança de idioma.
     *
     * @param mouseEvent O evento de clique do mouse para acionar a troca de idioma.
     */
    @FXML
    public void changeLanguage(MouseEvent mouseEvent) {
        if(LanguageManager.languageController == 0){
            LanguageManager.setLocale(Locale.ENGLISH);
            LanguageManager.languageController = 1;
            LanguageManager.notifyListeners();
        } else{
            LanguageManager.setLocale(Locale.forLanguageTag("pt-BR"));
            LanguageManager.languageController = 0;
            LanguageManager.notifyListeners();
        }
    }

    /**
     * Carrega e exibe os eventos disponíveis na página principal.
     * Recupera os eventos do controlador principal e exibe as informações, como nome, descrição e data, na interface gráfica.
     */
    private void loadEvents() {
        List<Evento> events = mainController.getAllEvents();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        eventContainer.getChildren().clear();

        for(Evento e : events){
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.getStyleClass().add("event-box");

            Label tittleLabel = new Label(e.getName());
            tittleLabel.getStyleClass().add("event-title");

            Label descriptionLabel = new Label(e.getDescription());
            descriptionLabel.getStyleClass().add("event-description");

            String formattedDate = formatter.format(e.getDate());
            Label dataLabel = new Label(eventDateMensage.getText()+ ": " + formattedDate);
            dataLabel.getStyleClass().add("event-date");

            vbox.getChildren().addAll(tittleLabel, descriptionLabel, dataLabel);

            vbox.setOnMouseClicked(mouseEvent -> {
                try {
                    handleEvent(mouseEvent, e); // Passa o MouseEvent e o evento associado
                } catch (IOException erro) {
                    erro.printStackTrace();
                }
            });

            eventContainer.getChildren().add(vbox);
        }
    }

    /**
     * Atualiza os textos da interface gráfica de acordo com o idioma atual.
     * Este método aplica os textos traduzidos nos rótulos e botões da página principal.
     */
    public void updateLanguage(){
        profile.setText(LanguageManager.getString("menu.profile"));
        editButton.setText(LanguageManager.getString("menu.creditCards"));
        seePurchasesButton.setText(LanguageManager.getString("menu.tickets"));
        changeLanguage.setText(LanguageManager.getString("menu.changeLanguage"));
        logoutButton.setText(LanguageManager.getString("menu.logout"));
        events.setText(LanguageManager.getString("events.title"));
        eventDateMensage.setText(LanguageManager.getString("events.date"));
        mailbox.setText(LanguageManager.getString("menu.mailbox"));
        FontButton.setText(LanguageManager.getString("button.font"));
    }

    /**
     * Método chamado quando ocorre uma mudança no idioma da aplicação.
     * Atualiza os textos da interface para refletir o novo idioma e carrega novamente os eventos e dados do usuário.
     *
     * @param currentLocale O idioma atual da aplicação.
     */
    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
        updateUserData();
        loadEvents();
        setUser(navigatorController.getUser());
        updateUserData();
    }

    /**
     * Método chamado quando ocorre uma mudança no tamanho da fonte da aplicação.
     * Ajusta o estilo dos componentes da interface de acordo com o novo tamanho de fonte.
     */
    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    /**
     * Altera o tamanho da fonte dos componentes da interface gráfica.
     * Aplica os estilos corretos dependendo da configuração de fonte.
     */
    public void toggleFont() {
        username.getStyleClass().removeAll("label-message2", "label-message");
        events.getStyleClass().removeAll("labelEvent-message2", "labelEvent-message2");
        profile.getStyleClass().removeAll("button-menu2", "button-menu");
        editButton.getStyleClass().removeAll("button-menu2", "button-menu");
        seePurchasesButton.getStyleClass().removeAll("button-menu2", "button-menu");
        changeLanguage.getStyleClass().removeAll("button-menu2", "button-menu");
        logoutButton.getStyleClass().removeAll("button-menu2", "button-menu");
        mailbox.getStyleClass().removeAll("button-menu2", "button-menu");
        FontButton.getStyleClass().removeAll("button-menu2", "button-menu");

        if(!LanguageManager.FontSizeController){
            username.getStyleClass().add("label-message2");
            events.getStyleClass().add("labelEvent-message2");
            profile.getStyleClass().add("button-menu2");
            editButton.getStyleClass().add("button-menu2");
            seePurchasesButton.getStyleClass().add("button-menu2");
            changeLanguage.getStyleClass().add("button-menu2");
            logoutButton.getStyleClass().add("button-menu2");
            mailbox.getStyleClass().add("button-menu2");
            FontButton.getStyleClass().add("button-menu2");
        }else{
            username.getStyleClass().add("label-message");
            events.getStyleClass().add("labelEvent-message");
            profile.getStyleClass().add("button-menu");
            editButton.getStyleClass().add("button-menu");
            seePurchasesButton.getStyleClass().add("button-menu");
            changeLanguage.getStyleClass().add("button-menu");
            logoutButton.getStyleClass().add("button-menu");
            mailbox.getStyleClass().add("button-menu");
            FontButton.getStyleClass().add("button-menu");
        }
    }

    /**
     * Lida com o evento de clique em um evento na interface.
     * Esse método injeta o evento selecionado no controlador de navegação e exibe os detalhes do evento.
     *
     * @param mouseEvent O evento de clique do mouse no evento.
     * @param e O evento que foi clicado.
     * @throws IOException Se ocorrer um erro ao carregar a página do evento.
     */
    public void handleEvent(MouseEvent mouseEvent, Evento e) throws IOException {
        navigatorController.setEventOn(e); // Injeta o evento no NavigatorController
        navigatorController.showEvent();
    }

    /**
     * Lida com o evento de clique no botão de perfil.
     * Esse método navega para a página de perfil do usuário.
     *
     * @param mouseEvent O evento de clique do mouse no botão de perfil.
     * @throws IOException Se ocorrer um erro ao carregar a página de perfil.
     */
    public void handleProfile(MouseEvent mouseEvent) throws IOException {
        navigatorController.showPerfil();
    }

    /**
     * Lida com o evento de clique no botão de cartões de crédito.
     * Esse método navega para a página de gerenciamento de cartões de crédito.
     *
     * @param mouseEvent O evento de clique do mouse no botão de cartões.
     * @throws IOException Se ocorrer um erro ao carregar a página de cartões de crédito.
     */
    public void handleCard(MouseEvent mouseEvent) throws IOException {
        navigatorController.showCard();
    }

    /**
     * Lida com o evento de clique no botão de ingressos.
     * Esse método navega para a página onde o usuário pode visualizar seus ingressos.
     *
     * @param mouseEvent O evento de clique do mouse no botão de ingressos.
     * @throws IOException Se ocorrer um erro ao carregar a página de ingressos.
     */
    public void handleTickets(MouseEvent mouseEvent) throws IOException {
        navigatorController.showTickets();
    }

    /**
     * Lida com o evento de clique no botão da caixa de entrada de mensagens.
     * Esse método navega para a página de caixa de entrada de mensagens do usuário.
     *
     * @param mouseEvent O evento de clique do mouse no botão da caixa de entrada.
     * @throws IOException Se ocorrer um erro ao carregar a página de caixa de entrada.
     */
    public void handleMailBox(MouseEvent mouseEvent) throws IOException {
        navigatorController.showMailBox();
    }

    /**
     * Altera o tamanho da fonte na interface gráfica.
     * Esse método alterna entre os tamanhos de fonte definidos e notifica os ouvintes sobre a mudança.
     *
     * @param actionEvent O evento de ação gerado ao clicar no botão de alteração de fonte.
     */
    public void changeFont(ActionEvent actionEvent) {
        LanguageManager.FontSizeController = !LanguageManager.FontSizeController;
        LanguageManager.notifyListeners();
    }
}