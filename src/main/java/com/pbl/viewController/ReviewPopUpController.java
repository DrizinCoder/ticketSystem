package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresEvent;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Locale;

public class ReviewPopUpController implements RequiresMainController, RequiresUser, RequiresEvent, LanguageChange {

    @FXML
    private Label reviewTitle;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<Integer> gradeSelector;
    @FXML
    private TextArea reviewText;

    private String writeText;
    private String reviewSend;
    private String gradeErro;
    private String eventErro;

    private mainController mainController;
    private NavigatorController navigatorController;
    private Usuario user;
    private Evento event;

    public void initialize() {
        updateLanguage();
        loadGradeSelector();
    }

    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    @Override
    public void setEvent(Evento event) {
        this.event = event;
    }

    @Override
    public void setUser(Usuario user) {
        this.user = user;
    }

    @FXML
    public void handleSubmitButton() {
        if(!event.isAtivo()) {
            String comment = reviewText.getText();
            if (gradeSelector.getValue() != null) {
                int grade = gradeSelector.getValue();

                if (verifyReview(comment)) {
                    mainController.reviewEvent(event, user, comment, grade);
                    showSucessAlert(reviewSend);
                    closePopup();
                }
            } else {
                showErrorAlert(gradeErro);
            }
        } else{
            showErrorAlert(eventErro);
        }
    }

    @FXML
    public void handleCancelButton() {
        closePopup();
    }

    private boolean verifyReview(String comment){
        if(comment == null || comment.isEmpty()){
            showErrorAlert(writeText);
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

    private void showSucessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void loadGradeSelector() {
        ObservableList<Integer> gradeOptions = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);
        gradeSelector.setItems(gradeOptions);
    }

    private void closePopup() {
        Stage stage = (Stage) reviewText.getScene().getWindow();
        stage.close();
    }

    private void updateLanguage() {
        submitButton.setText(LanguageManager.getString("submitButton"));
        cancelButton.setText(LanguageManager.getString("cancelButton"));
        reviewTitle.setText(LanguageManager.getString("reviewTitle"));
        reviewText.setPromptText(LanguageManager.getString("reviewText"));
        gradeSelector.setPromptText(LanguageManager.getString("gradeOptions"));
        writeText = LanguageManager.getString("writeText");
        reviewSend = LanguageManager.getString("reviewSend");
        gradeErro = LanguageManager.getString("gradeErro");
        eventErro = LanguageManager.getString("eventErro");
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    @Override
    public void toggleFont() {

    }
}