package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresEvent;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Card;
import com.pbl.models.Evento;
import com.pbl.models.Review;
import com.pbl.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SingleEventController implements RequiresMainController, RequiresUser, RequiresEvent, LanguageChange {

    private com.pbl.controller.mainController mainController;
    private NavigatorController navigatorController;
    private Usuario user;
    private Evento event;
    private String payslip;

    @FXML
    private Label eventTittle;
    @FXML
    private Label eventDescription;
    @FXML
    private Label eventDate;
    @FXML
    private Label eventStatus;
    @FXML
    private Label reviews;
    @FXML
    private Button purchaseButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button languageToggle;
    @FXML
    private Label Rate;
    @FXML
    private VBox commentContainner;
    @FXML
    private ComboBox<String> paymentSelector;
    @FXML
    private ComboBox<String> seatSelector;

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
        this.user = user;
        loadPaymentSelector();
    }

    @Override
    public void setEvent(Evento event) {
        this.event = event;
        updateEventData();
        loadReviews();
        loadSeatSelector();
    }

    public void handleExitButton(MouseEvent mouseEvent) throws IOException {
        navigatorController.comeback();
    }

    public void handlePurchaseButton(MouseEvent mouseEvent) {
        String paymentMethod = paymentSelector.getValue();
        Card card = null;

        if(event.isAtivo()) {
            if (!paymentMethod.equals("Boleto")) {
                card = mainController.getCardByNumber(paymentMethod);
                String seat = seatSelector.getValue();

                if (seat != null && verifyCredentials(paymentMethod, seat)) {
                    mainController.buyTicket(user, event, card, seat);
                    mainController.removeSeat(seat, event);
                    // Atualiza a lista no ComboBox
                    ObservableList<String> updatedSeats = FXCollections.observableArrayList(mainController.getEventSeats(event.getID()));
                    seatSelector.setItems(updatedSeats);

                    showConfirmationAlert(LanguageManager.getString("event.purchasePass"));
                } else {
                    showConfirmationAlert(LanguageManager.getString("event.seat"));
                }
            } else {
                String seat = seatSelector.getValue();
                if (seat != null && verifyCredentials(paymentMethod, seat)) {
                    mainController.buyTicket(user, event, null, seat);
                    mainController.removeSeat(seat, event);
                    // Atualiza a lista no ComboBox
                    ObservableList<String> updatedSeats = FXCollections.observableArrayList(mainController.getEventSeats(event.getID()));
                    seatSelector.setItems(updatedSeats);

                    showConfirmationAlert(LanguageManager.getString("event.purchasePass"));
                } else {
                    showConfirmationAlert(LanguageManager.getString("event.seat"));
                }
            }
        } else{
            showErrorAlert(LanguageManager.getString("event.finished"));
        }
    }

    public boolean verifyCredentials(String paymentMethod, String seat){
        if(Objects.equals(paymentMethod, "")){
           showErrorAlert(LanguageManager.getString("event.payment"));
            return false;
        } else if(Objects.equals(seat, "")){
            showErrorAlert(LanguageManager.getString("event.seat"));
            return false;
        }
        return true;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void updateEventData() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        mainController.calculateEventRating(event);
        eventTittle.setText(event.getName());
        eventDescription.setText(event.getDescription());
        String formattedDate = formatter.format(event.getDate());
        eventDate.setText(formattedDate);
        Rate.setText(event.getRate().toString());
        String statusText;
        if (LanguageManager.languageController == 0) {
            statusText = event.isAtivo() ? "Ativo" : "Inativo";
        } else {
            statusText = event.isAtivo() ? "Active" : "Finished";
        }
        eventStatus.setText(statusText);
    }

    public void updateLanguage() {
        reviews.setText(LanguageManager.getString("event.review"));
        cancelButton.setText(LanguageManager.getString("button.cancel"));
        purchaseButton.setText(LanguageManager.getString("event.purchaseButton"));
        languageToggle.setText(LanguageManager.getString("button.language"));
        payslip = LanguageManager.getString("payslip");
    }

    public void changeLanguage(MouseEvent mouseEvent) {
        if (LanguageManager.languageController == 0) {
            LanguageManager.setLocale(Locale.ENGLISH);
            LanguageManager.languageController = 1;
        } else {
            LanguageManager.setLocale(Locale.forLanguageTag("pt-BR"));
            LanguageManager.languageController = 0;
        }
        LanguageManager.notifyListeners();
        updateEventData();
        loadPaymentSelector();
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    @Override
    public void toggleFont() {
    }

    public void loadReviews() {
        List<Review> reviews = mainController.getEventsReview(event.getID());

        commentContainner.getChildren().clear();

        for (Review r : reviews) {
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.getStyleClass().add("event-box");

            String userLogin = mainController.getUserByID(r.getUser()).getLogin();
            Label tittleLabel = new Label(userLogin);
            tittleLabel.getStyleClass().add("event-title");

            Label descriptionLabel = new Label(r.getComment());
            descriptionLabel.getStyleClass().add("event-description");

            Integer rating = r.getRating();
            Label dataLabel = new Label(rating.toString());
            dataLabel.getStyleClass().add("event-date");

            vbox.getChildren().addAll(tittleLabel, dataLabel, descriptionLabel);

            commentContainner.getChildren().add(vbox);
        }
    }

    public void loadPaymentSelector() {
        ObservableList<String> paymentOptions = FXCollections.observableArrayList(payslip);
        List<Card> userCards = mainController.getUserCards(user.getID());
        for(Card card : userCards) {
            paymentOptions.add(card.getCardNumber());
        }
        paymentSelector.setItems(paymentOptions);
        paymentSelector.setValue(payslip);
    }

    public void loadSeatSelector() {
        ObservableList<String> seatOptions = FXCollections.observableArrayList();
        List<String> seats = mainController.getEventSeats(event.getID());
        seatOptions.addAll(seats);
        seatSelector.setItems(seatOptions);
    }
}
