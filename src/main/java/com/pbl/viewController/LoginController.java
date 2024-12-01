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

    private String loginSucessfull;
    private String loginErro;
    private String loginPass;
    private String loginErroMsg;

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
    private Label noAccountMessage;

    @FXML
    private Label signUpLink;

    @FXML
    private Button languageToggle;

    @FXML
    private Button FontButton;

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
            showConfimationAlert(loginSucessfull, loginPass);
            passwordField.clear();

            navigatorController.showMainPage();
        } else {
            showErrorAlert(loginErro, loginErroMsg);
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

    private void showConfimationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void handleSignUpLink(MouseEvent mouseEvent) throws IOException {
        navigatorController.showSignUp();
    }

    public void updateLanguage(){
        welcomeLabel.setText(LanguageManager.getString("title.welcome"));
        subtitleLabel.setText(LanguageManager.getString("subtitle.credentials"));
        loginButton.setText(LanguageManager.getString("button.login"));
        noAccountMessage.setText(LanguageManager.getString("message.noAccount"));
        signUpLink.setText(LanguageManager.getString("link.signUp"));
        usernameField.setPromptText(LanguageManager.getString("field.username"));
        passwordField.setPromptText(LanguageManager.getString("field.password"));
        languageToggle.setText(LanguageManager.getString("button.language"));
        FontButton.setText(LanguageManager.getString("button.font"));
        loginSucessfull = LanguageManager.getString("operation.sucess");
        loginErro = LanguageManager.getString(("operation.error"));
        loginPass = LanguageManager.getString("login.pass");
        loginErroMsg = LanguageManager.getString("login.error");
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
    }

    public void changeFont(){
        LanguageManager.notifyListeners();
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    /*
    * Toda classe de LanguageManager deve implementar ToggleFont para alterar o tamanho das paradas.
    * */

    public void toggleFont() {
        if(!LanguageManager.FontSizeController){
            welcomeLabel.getStyleClass().add("TittleFont");
            subtitleLabel.getStyleClass().add("subtitleFont");
            loginButton.getStyleClass().add("buttonFontLogin");
            languageToggle.getStyleClass().add("buttonFontCancel");
            FontButton.getStyleClass().add("buttonFontCancel");
            noAccountMessage.getStyleClass().add("label-message2");
            signUpLink.getStyleClass().add("label-link2");
        } else{
            welcomeLabel.getStyleClass().remove("TittleFont");
            subtitleLabel.getStyleClass().remove("subtitleFont");
            loginButton.getStyleClass().remove("buttonFontLogin");
            languageToggle.getStyleClass().remove("buttonFontCancel");
            FontButton.getStyleClass().remove("buttonFontCancel");
            noAccountMessage.getStyleClass().remove("label-message2");
            signUpLink.getStyleClass().remove("label-link2");
        }
        LanguageManager.FontSizeController = !LanguageManager.FontSizeController;
    }
}
