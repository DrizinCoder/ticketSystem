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
                mainController.signUp(login, password, username, cpf, email, false);
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
    public void toggleFont() {

    }
}
