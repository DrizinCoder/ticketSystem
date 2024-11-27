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
    private Label messageLabel;

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
                showConfimationAlert("Operação confirmada", "Usuário cadastrado com sucesso!");
                navigatorController.showMainPage();
            } else {
                showErrorAlert("Operação inválida", "Login já está em uso.");
            }
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

    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

    public boolean verifyCredintials(String login, String password, String cpf, String email, String username) {
        if(Objects.equals(login, "")){
            messageLabel.setText("login inválido");
            return false;
        } else if(Objects.equals(password, "")){
            messageLabel.setText("senha inválida");
            return false;
        } else if(Objects.equals(username, "")){
            messageLabel.setText("nome de usuário inválido");
            return false;
        } else if(Objects.equals(cpf, "")){
            messageLabel.setText("cpf inválido");
            return false;
        } else if(Objects.equals(email, "")){
            messageLabel.setText("email inválido");
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
