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

    private Text eventDateMensage;

    public void initialize() {
        LanguageManager.registerListener(this);
        eventDateMensage = new Text();
        eventDateMensage.setText(LanguageManager.getString("events.date"));
    }

    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        loadEvents();
        LanguageManager.notifyListeners();
    }

    public void setUser(Usuario user) {
        this.user = user;
        updateUserData();
    }

    private void updateUserData(){
        if (user != null && LanguageManager.languageController == 0) {
            username.setText("Bem vindo\n" + user.getNome() + "!");
        } else if(user != null && LanguageManager.languageController == 1){
            username.setText("Welcome\n" + user.getNome() + "!");
        }else if(LanguageManager.languageController == 0){
            username.setText("USUARIO NAO ENCONTRADO!");
        } else {
            username.setText("USER NOT FOUND");
        }
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

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

    public void updateLanguage(){
        profile.setText(LanguageManager.getString("menu.profile"));
        editButton.setText(LanguageManager.getString("menu.creditCards"));
        seePurchasesButton.setText(LanguageManager.getString("menu.tickets"));
        changeLanguage.setText(LanguageManager.getString("menu.changeLanguage"));
        logoutButton.setText(LanguageManager.getString("menu.logout"));
        events.setText(LanguageManager.getString("events.title"));
        eventDateMensage.setText(LanguageManager.getString("events.date"));
        mailbox.setText(LanguageManager.getString("menu.mailbox"));
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
        updateUserData();
        loadEvents();
        setUser(navigatorController.getUser());
        updateUserData();
    }

    @Override
    public void toggleFont() {

    }

    public void handleEvent(MouseEvent mouseEvent, Evento e) throws IOException {
        navigatorController.setEventOn(e); // Injeta o evento no NavigatorController
        navigatorController.showEvent();
    }

    public void handleProfile(MouseEvent mouseEvent) throws IOException {
        navigatorController.showPerfil();
    }

    public void handleCard(MouseEvent mouseEvent) throws IOException {
        navigatorController.showCard();
    }

    public void handleTickets(MouseEvent mouseEvent) throws IOException {
        navigatorController.showTickets();
    }

    public void handleMailBox(MouseEvent mouseEvent) throws IOException {
        navigatorController.showMailBox();
    }
}