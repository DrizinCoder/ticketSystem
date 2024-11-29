package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Ingresso;
import com.pbl.models.Purchase;
import com.pbl.models.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MailBoxController implements RequiresMainController, RequiresUser, LanguageChange {

    @FXML
    private VBox mailsContainer;
    @FXML
    private Button changeLanguage;
    @FXML
    private Button backButton;
    @FXML
    private Label mails;

    private mainController mainController;
    private NavigatorController navigatorController;
    private Usuario user;

    private String Mensage;
    private String thanksMensage;
    private String thanksMensage2;
    private String payslip;

    public void initialize() {
        LanguageManager.registerListener(this);
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
        loadPurchases();
    }

    public void hangleBackButton(MouseEvent mouseEvent) throws IOException {
        navigatorController.comeback();
    }

    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    @Override
    public void toggleFont() {

    }

    private void updateLanguage() {
        mails.setText(LanguageManager.getString("menu.mailbox"));
        changeLanguage.setText(LanguageManager.getString("menu.changeLanguage"));
        backButton.setText(LanguageManager.getString("button.cancel"));
        Mensage = LanguageManager.getString("proof");
        thanksMensage = LanguageManager.getString("proof.thanks");
        thanksMensage2 = LanguageManager.getString("proof.thanks2");
        payslip = LanguageManager.getString("proofId");
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
        loadPurchases();
    }

    public void loadPurchases(){
        List<Purchase> purchases = mainController.getUserPurchases(user.getID());

        mailsContainer.getChildren().clear();

        for(Purchase p : purchases) {
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.getStyleClass().add("event-box");

            Label tittleLabel = new Label(Mensage);
            tittleLabel.getStyleClass().add("event-title");
            Label proofMensage = new Label(thanksMensage);
            proofMensage.getStyleClass().add("event-description");
            Label proofMensage2 = new Label(user.getNome() + " " + thanksMensage2);
            proofMensage2.getStyleClass().add("event-description");
            Label idPurchase = new Label(payslip + ", " + p.getID());
            idPurchase.getStyleClass().add("event-title");

            vbox.getChildren().addAll(tittleLabel, proofMensage, proofMensage2, idPurchase);

            mailsContainer.getChildren().add(vbox);
    }
    }
}
