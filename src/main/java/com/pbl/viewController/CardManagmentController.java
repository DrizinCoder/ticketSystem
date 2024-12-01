package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Card;
import com.pbl.models.Review;
import com.pbl.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CardManagmentController implements RequiresMainController, RequiresUser, LanguageChange {

    private mainController mainController;
    private NavigatorController navigatorController;
    private Usuario user;

    @FXML
    private TextField cardHolderNameField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private DatePicker expiryDatePicker;

    @FXML
    private TextField cvvField;

    @FXML
    private Button addCardButton;

    @FXML
    private Button backButton;

    @FXML
    private VBox cardListContainer;

    @FXML
    private Label cardTitle;

    @FXML
    private Label cardTitle2;

    @FXML
    private Button languageToggle;

    private Text ExpiryDate;
    private Text CardNumberInfo;

    public void initialize() {
        LanguageManager.registerListener(this);
        ExpiryDate = new Text();
        ExpiryDate.setText(LanguageManager.getString("ExpiryDate"));
        CardNumberInfo = new Text();
        CardNumberInfo.setText(LanguageManager.getString("CardNumberInfo"));
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
        loadCard();
    }

    public void handleAddCard(ActionEvent actionEvent) {
        String holderName = cardHolderNameField.getText();
        String cardNumber = cardNumberField.getText();
        Date expiryDate = convertDateType(expiryDatePicker.getValue());
        if(verifyCvv() && !cvvField.getText().isEmpty()) {
            int cvv = Integer.parseInt(cvvField.getText());
            if(verifyCredentials(holderName, cardNumber, expiryDate.toString())) {
                mainController.addCreditCard(holderName, cardNumber, expiryDate, cvv, user.getID());
                showConfirmationAlert(LanguageManager.getString("cardSucess"));
                loadCard();
            }
        }
    }

    public boolean verifyCvv() {
        try {
                Integer.parseInt(cvvField.getText());
                return true;
        } catch (NumberFormatException e) {
                showErrorAlert(LanguageManager.getString("cardErro"));
                return false;
        }
    }

    public boolean verifyCredentials(String holderName, String cardNumber, String expiryDate) {
        if(Objects.equals(holderName, "")){
            showErrorAlert(LanguageManager.getString("cardErro"));
            return false;
        } else if(Objects.equals(cardNumber, "")){
            showErrorAlert(LanguageManager.getString("cardErro"));
            return false;
        } else if(Objects.equals(expiryDate, "")){
            showErrorAlert(LanguageManager.getString("cardErro"));
            return false;
        }
        return true;
    }

    public void handleRemoveCard(Card card) {
        mainController.removeCreditCard(card);
        loadCard();
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

    public Date convertDateType(LocalDate localDate) {
        if(localDate != null) {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else{
            showErrorAlert(LanguageManager.getString("cardErro"));
            return null;
        }
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

    public void loadCard(){
        List<Card> cards = mainController.getUserCards(user.getID());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        cardListContainer.getChildren().clear();

        for (Card c : cards) {
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.getStyleClass().add("event-box");

            Label tittleLabel = new Label(CardNumberInfo.getText()+ ": " + c.getCardNumber());
            tittleLabel.getStyleClass().add("event-title");

            String FormattedDate = formatter.format(c.getExpiryDate());
            Label dataLabel = new Label(ExpiryDate.getText() + ": " + FormattedDate);
            dataLabel.getStyleClass().add("event-date");

            Button actionButton = new Button("Deletar");
            actionButton.getStyleClass().add("button-delete");
            actionButton.getStyleClass().add("event-button");
            actionButton.setOnAction(e -> {
                handleRemoveCard(c);
            });

            // Colocar data e botão dentro de uma HBox
            HBox hbox = new HBox();
            hbox.setSpacing(10); // Espaçamento horizontal
            hbox.getChildren().addAll(dataLabel, actionButton);

            // Adicionar título e HBox ao VBox
            vbox.getChildren().addAll(tittleLabel, hbox);

            cardListContainer.getChildren().add(vbox);
        }
    }

    public void updateLanguage(){
        cardTitle.setText(LanguageManager.getString("card.title"));
        addCardButton.setText(LanguageManager.getString("card.addButton"));
        cardHolderNameField.setPromptText(LanguageManager.getString("card.HolderName"));
        cardNumberField.setPromptText(LanguageManager.getString("card.number"));
        expiryDatePicker.setPromptText(LanguageManager.getString("card.date"));
        cvvField.setPromptText(LanguageManager.getString("card.cvv"));
        backButton.setText(LanguageManager.getString("button.cancel"));
        cardTitle2.setText(LanguageManager.getString("card.title2"));
        languageToggle.setText(LanguageManager.getString("button.language"));
        ExpiryDate.setText(LanguageManager.getString("ExpiryDate"));
        CardNumberInfo.setText(LanguageManager.getString("CardNumberInfo"));
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
        loadCard();
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    @Override
    public void onLocalToggleFont() {
        LanguageManager.notifyListeners();
    }

    public void toggleFont() {

    }
}
