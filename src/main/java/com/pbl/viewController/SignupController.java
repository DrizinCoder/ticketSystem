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
import java.util.Objects;

public class SignupController  implements RequiresMainController, LanguageChange {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField CpfField;

    @FXML
    private TextField emailField;

    @FXML
    private Label titleLabel;

    @FXML
    private Label subtitleLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Button backButton;

    @FXML
    private Button languageToggle;

    @FXML
    private Button FontButton;

    private mainController mainController;

    private NavigatorController navigatorController;

    private String signUpErro;
    private String signUpPass;

    public void initialize(){
        LanguageManager.registerListener(this);
    }

    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    public void handleCadastrarButton(ActionEvent actionEvent) throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        String username = usernameField.getText();
        String cpf = CpfField.getText();
        String email = emailField.getText();


        if(verifyCredintials(login,password,cpf, email, username)) {
            if(verifyLogin(login)) {
                Usuario user = mainController.signUp(login, password, username, cpf, email, false);
                navigatorController.setLoggedUser(user);

                loginField.clear();
                passwordField.clear();
                usernameField.clear();
                CpfField.clear();
                emailField.clear();

                showConfimationAlert(signUpPass);
                navigatorController.showMainPage();
            } else {
                showErrorAlert(signUpErro);
            }
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void showConfimationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

    public boolean verifyCredintials(String login, String password, String cpf, String email, String username) {
        if(Objects.equals(login, "")){
            showErrorAlert(signUpErro);
            return false;
        } else if(Objects.equals(password, "")){
            showErrorAlert(signUpErro);
            return false;
        } else if(Objects.equals(username, "")){
            showErrorAlert(signUpErro);
            return false;
        } else if(Objects.equals(cpf, "")){
            showErrorAlert(signUpErro);
            return false;
        } else if(Objects.equals(email, "")){
            showErrorAlert(signUpErro);
            return false;
        }
        return true;
    }

    public boolean verifyLogin(String login){
        Usuario user = mainController.getUserByLogin(login);
        return user == null;
    }

    public void updateLanguage(){
        loginField.setPromptText(LanguageManager.getString("signup.loginField.prompt"));
        passwordField.setPromptText(LanguageManager.getString("signup.passwordField.prompt"));
        usernameField.setPromptText(LanguageManager.getString("signup.usernameField.prompt"));
        CpfField.setPromptText(LanguageManager.getString("signup.cpfField.prompt"));
        emailField.setPromptText(LanguageManager.getString("signup.emailField.prompt"));
        titleLabel.setText(LanguageManager.getString("signup.title"));
        subtitleLabel.setText(LanguageManager.getString("signup.subtitle"));
        confirmButton.setText(LanguageManager.getString("signup.registerButton"));
        backButton.setText(LanguageManager.getString("signup.backButton"));
        languageToggle.setText(LanguageManager.getString("button.language"));
        signUpErro = LanguageManager.getString("signUp.erro");
        signUpPass = LanguageManager.getString("signUp.pass");
        FontButton.setText(LanguageManager.getString("button.font"));
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

    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    public void toggleFont() {
        titleLabel.getStyleClass().removeAll("text-title", "text-title2");
        subtitleLabel.getStyleClass().removeAll("text-subtitle", "text-subtitle2");
        confirmButton.getStyleClass().removeAll("button-login", "button-login2");
        backButton.getStyleClass().removeAll("button-cancel", "button-cancel2");
        languageToggle.getStyleClass().removeAll("button-cancel", "button-cancel2");
        FontButton.getStyleClass().removeAll("button-cancel2", "button-cancel");

        if(!LanguageManager.FontSizeController){
            titleLabel.getStyleClass().add("text-title2");
            subtitleLabel.getStyleClass().add("text-subtitle2");
            confirmButton.getStyleClass().add("button-login2");
            backButton.getStyleClass().add("button-cancel2");
            languageToggle.getStyleClass().add("button-cancel2");
            FontButton.getStyleClass().add("button-cancel2");
        }else{
            titleLabel.getStyleClass().add("text-title");
            subtitleLabel.getStyleClass().add("text-subtitle");
            confirmButton.getStyleClass().add("button-login");
            backButton.getStyleClass().add("button-cancel");
            languageToggle.getStyleClass().add("button-cancel");
            FontButton.getStyleClass().add("button-cancel");
        }
    }

    public void changeFont(ActionEvent actionEvent) {
        LanguageManager.FontSizeController = !LanguageManager.FontSizeController;
        LanguageManager.notifyListeners();
    }
}
