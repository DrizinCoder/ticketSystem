package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresEvent;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Ingresso;
import com.pbl.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    private String seat;
    private String cost;

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
            Label descriptionLabel = new Label(cost + " " + preco);
            descriptionLabel.getStyleClass().add("event-description");

            Label dataLabel = new Label(seat + " " + e.getAssento());
            dataLabel.getStyleClass().add("event-date");

            Button actionButton = new Button();
            actionButton.setText(LanguageManager.getString("button.action"));
            actionButton.getStyleClass().add("button-cancel");
            actionButton.setOnAction(x -> {
                handleReviewButton(e);
            });

            // Colocar data e botão dentro de uma HBox
            HBox hbox = new HBox();
            hbox.setSpacing(20); // Espaçamento horizontal
            hbox.getChildren().addAll(dataLabel, actionButton);

            vbox.getChildren().addAll(tittleLabel, descriptionLabel, hbox);

            ticketsContainer.getChildren().add(vbox);
        }
    }

    public void changeLanguage(MouseEvent mouseEvent) {
        if(LanguageManager.languageController == 0){
            LanguageManager.setLocale(Locale.ENGLISH);
            LanguageManager.languageController = 1;
        } else{
            LanguageManager.setLocale(Locale.forLanguageTag("pt-BR"));
            LanguageManager.languageController = 0;
        }
        LanguageManager.notifyListeners();
        loadTickets();
    }

    public void updateLanguage() {
        tickets.setText(LanguageManager.getString("tickets.title"));
        changeLanguage.setText(LanguageManager.getString("menu.changeLanguage"));
        backButton.setText(LanguageManager.getString("button.cancel"));
        seat = LanguageManager.getString("seat");
        cost = LanguageManager.getString("cost");
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    public void toggleFont() {
        tickets.getStyleClass().removeAll("labelEvent-message", "labelEvent-message2");
        changeLanguage.getStyleClass().removeAll("button-cancel", "button-cancel2");
        backButton.getStyleClass().removeAll("button-cancel", "button-cancel2");

        if(!LanguageManager.FontSizeController){
            tickets.getStyleClass().add("labelEvent-message2");
            changeLanguage.getStyleClass().add("button-cancel2");
            backButton.getStyleClass().add("button-cancel2");
        } else{
            tickets.getStyleClass().add("labelEvent-message");
            changeLanguage.getStyleClass().add("button-cancel");
            backButton.getStyleClass().add("button-cancel");
        }
    }

    public void handleReviewButton(Ingresso e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReviewEventPopup.fxml"));
            Parent root = loader.load();

            Object viewController = loader.getController();
            Evento evento = mainController.getEventByID(e.getEventoID());
            ((RequiresEvent) viewController).setEvent(evento);
            ((RequiresMainController) viewController).setDependencies(navigatorController.getController(),
                    navigatorController);
            ((RequiresUser) viewController).setUser(navigatorController.getUser());

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setTitle("Avaliar Evento");
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);

            popupStage.showAndWait();
        } catch (Exception erro) {
            erro.printStackTrace();
        }

    }

    public void hangleBackButton(MouseEvent mouseEvent) throws IOException {
        navigatorController.comeback();
    }
}
