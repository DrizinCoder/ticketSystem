package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class PerfilController implements RequiresMainController, RequiresUser, LanguageChange {

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

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

    private Usuario user;

    public void initialize(){
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
        updateUserData();
    }

    public void handleEditButton(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        if(verifyCredintials(login,password, email, username)) {
            mainController.editPerfil(user, username, email, password, login);
            Usuario userUpdated = mainController.getUserByID(user.getID());
            messageLabel.setText(LanguageManager.getString("perfil.messageLabel"));
            navigatorController.setLoggedUser(userUpdated);
            LanguageManager.notifyListeners();
        }
    }

    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
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

    public void updateUserData(){
        loginField.setText(user.getLogin());
        passwordField.setText(user.getSenha());
        usernameField.setText(user.getNome());
        CpfField.setText(user.getCpf());
        emailField.setText(user.getEmail());
    }

    public void updateLanguage(){
        loginField.setPromptText(LanguageManager.getString("signup.loginField.prompt"));
        passwordField.setPromptText(LanguageManager.getString("signup.passwordField.prompt"));
        usernameField.setPromptText(LanguageManager.getString("signup.usernameField.prompt"));
        CpfField.setPromptText(LanguageManager.getString("signup.cpfField.prompt"));
        emailField.setPromptText(LanguageManager.getString("signup.emailField.prompt"));
        titleLabel.setText(LanguageManager.getString("perfil.title"));
        subtitleLabel.setText(LanguageManager.getString("perfil.subtitle"));
        confirmButton.setText(LanguageManager.getString("perfil.confirmButton"));
        backButton.setText(LanguageManager.getString("signup.backButton"));
        languageToggle.setText(LanguageManager.getString("button.language"));
    }

    public boolean verifyCredintials(String login, String password, String email, String username) {
        if(Objects.equals(login, "")){
            messageLabel.setText("login inválido");
            return false;
        } else if(Objects.equals(password, "")){
            messageLabel.setText("senha inválida");
            return false;
        } else if(Objects.equals(username, "")){
            messageLabel.setText("nome de usuário inválido");
            return false;
        } else if(Objects.equals(email, "")){
            messageLabel.setText("email inválido");
            return false;
        }
        return true;
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }
}
