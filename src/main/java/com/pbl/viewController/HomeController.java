package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.controller.mainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Locale;


public class HomeController  implements RequiresMainController, LanguageChange {

    private mainController mainController;

    private NavigatorController navigatorController;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button languageToggle;

    public void initialize() {
        LanguageManager.registerListener(this);
    }

    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        navigatorController.showLogin();
    }

    public void handleSignUpButton(ActionEvent actionEvent) throws IOException {
        navigatorController.showSignUp();
    }

    public void updateLanguage(){
        loginButton.setText(LanguageManager.getString("button.login"));
        signUpButton.setText(LanguageManager.getString("signup.registerButton"));
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
