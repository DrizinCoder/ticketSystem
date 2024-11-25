package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Ingresso;
import com.pbl.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ticketsController implements RequiresMainController, RequiresUser, LanguageChange {

    public Button changeLanguage;
    private mainController mainController;
    private NavigatorController navigatorController;
    private Usuario usuario;

    @FXML
    private Label tickets;

    @FXML
    private VBox ticketsContainer;

    @FXML
    private Button backButton;

    public void initialize() {
        LanguageManager.registerListener(this);
    }

    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    @Override
    public void setUser(Usuario user) {
        this.usuario = user;
        loadTickets();
    }

    public void loadTickets() {
        List<Ingresso> tickets = mainController.getUserIngressos(usuario);

        ticketsContainer.getChildren().clear();

        for(Ingresso e : tickets) {
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.getStyleClass().add("event-box");

            Evento event = mainController.getEventByID(e.getEventoID());
            Label tittleLabel = new Label(event.getName());
            tittleLabel.getStyleClass().add("event-title");

            Double preco = e.getPreco();
            Label descriptionLabel = new Label(preco.toString());
            descriptionLabel.getStyleClass().add("event-description");

            Label dataLabel = new Label(e.getAssento());
            dataLabel.getStyleClass().add("event-date");

            Button actionButton = new Button("Avaliar evento");
            actionButton.getStyleClass().add("button-cancel");
            actionButton.setOnAction(x -> {
                handleReviewButton();
            });

            // Colocar data e botão dentro de uma HBox
            HBox hbox = new HBox();
            hbox.setSpacing(940); // Espaçamento horizontal
            hbox.getChildren().addAll(dataLabel, actionButton);

            vbox.getChildren().addAll(tittleLabel, descriptionLabel, hbox);

            ticketsContainer.getChildren().add(vbox);
        }
    }

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

    public void updateLanguage() {
        tickets.setText(LanguageManager.getString("tickets.title"));
        changeLanguage.setText(LanguageManager.getString("menu.changeLanguage"));
        backButton.setText(LanguageManager.getString("button.cancel"));
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    public void handleReviewButton(){

    }

    public void hangleBackButton(MouseEvent mouseEvent) throws IOException {
        navigatorController.comeback();
    }
}
