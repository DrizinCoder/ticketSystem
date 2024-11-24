package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.controller.mainController;
import com.pbl.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;

public class LoginController implements RequiresMainController, LanguageChange {

    private mainController mainController;

    private NavigatorController navigatorController;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label subtitleLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label noAccountMessage;

    @FXML
    private Label signUpLink;

    @FXML
    private Button languageToggle;

    public void initialize(){
        LanguageManager.registerListener(this);
    }

    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    @FXML
    protected void handleLoginButton() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();


        if (mainController.signIn(username, password)) {
            Usuario user = mainController.getUserByLogin(username);
            navigatorController.setLoggedUser(user);
            System.out.println(user);
            passwordField.clear();
            navigatorController.showMainPage();
        } else {
            showErrorAlert("Erro de login", "Nome de usuário ou senha inválidos.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

    public void handleSignUpLink(MouseEvent mouseEvent) throws IOException {
        navigatorController.showSignUp();
    }

    public void updateLanguage(){
        welcomeLabel.setText(LanguageManager.getString("title.welcome"));
        subtitleLabel.setText(LanguageManager.getString("subtitle.credentials"));
        loginButton.setText(LanguageManager.getString("button.login"));
        cancelButton.setText(LanguageManager.getString("button.cancel"));
        noAccountMessage.setText(LanguageManager.getString("message.noAccount"));
        signUpLink.setText(LanguageManager.getString("link.signUp"));
        usernameField.setPromptText(LanguageManager.getString("field.username"));
        passwordField.setPromptText(LanguageManager.getString("field.password"));
        languageToggle.setText(LanguageManager.getString("button.language"));
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

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }
}
