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

    /**
     * Inicializa o controlador da caixa de entrada. Registra o controlador como ouvinte para mudanças de idioma.
     */
    public void initialize() {
        LanguageManager.registerListener(this);
    }

    /**
     * Altera o idioma da aplicação entre inglês e português (Brasil).
     * Alterna o idioma conforme o estado atual do `LanguageManager.languageController` e notifica todos os ouvintes sobre a mudança de idioma.
     *
     * @param mouseEvent O evento de clique do mouse para acionar a troca de idioma.
     */
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

    /**
     * Lida com o evento de clique no botão "Voltar".
     * Esse método navega de volta para a página anterior no aplicativo.
     *
     * @param mouseEvent O evento de clique do mouse para acionar o retorno à página anterior.
     * @throws IOException Se ocorrer algum erro durante a navegação.
     */
    public void hangleBackButton(MouseEvent mouseEvent) throws IOException {
        navigatorController.comeback();
    }

    /**
     * Método chamado quando ocorre uma mudança no idioma da aplicação.
     * Atualiza os textos da interface gráfica para refletir o novo idioma.
     *
     * @param currentLocale O idioma atual da aplicação.
     */
    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    /**
     * Método chamado quando ocorre uma mudança no tamanho da fonte da aplicação.
     * Ele ajusta o estilo dos componentes da interface de acordo com o novo tamanho de fonte.
     */
    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    /**
     * Ajusta o estilo da fonte nos componentes da interface gráfica, alterando entre dois tamanhos de fonte.
     * Aplica ou remove as classes de estilo apropriadas dependendo da configuração atual do tamanho da fonte.
     */
    public void toggleFont() {
        mails.getStyleClass().removeAll("labelEvent-message", "labelEvent-message2");
        changeLanguage.getStyleClass().removeAll("button-cancel", "button-cancel2");
        backButton.getStyleClass().removeAll("button-cancel", "button-cancel2");

        if(!LanguageManager.FontSizeController){
            mails.getStyleClass().add("labelEvent-message2");
            changeLanguage.getStyleClass().add("button-cancel2");
            backButton.getStyleClass().add("button-cancel2");
        } else{
            mails.getStyleClass().add("labelEvent-message");
            changeLanguage.getStyleClass().add("button-cancel");
            backButton.getStyleClass().add("button-cancel");
        }
    }

    /**
     * Atualiza os textos da interface gráfica de acordo com o idioma atual.
     * Este método aplica os textos traduzidos nos rótulos e botões da tela de caixa de entrada.
     */
    private void updateLanguage() {
        mails.setText(LanguageManager.getString("menu.mailbox"));
        changeLanguage.setText(LanguageManager.getString("menu.changeLanguage"));
        backButton.setText(LanguageManager.getString("button.cancel"));
        Mensage = LanguageManager.getString("proof");
        thanksMensage = LanguageManager.getString("proof.thanks");
        thanksMensage2 = LanguageManager.getString("proof.thanks2");
        payslip = LanguageManager.getString("proofId");
    }

    /**
     * Define as dependências necessárias para o controlador.
     * Este método é chamado para inicializar as dependências necessárias, como o controlador principal e o controlador de navegação.
     *
     * @param mainController O controlador principal da aplicação.
     * @param navigatorController O controlador responsável pela navegação entre as páginas.
     */
    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    /**
     * Define o usuário atual.
     * Esse método é chamado para configurar o usuário que está acessando a caixa de entrada e carregar suas compras.
     *
     * @param user O usuário que está acessando a caixa de entrada.
     */
    @Override
    public void setUser(Usuario user) {
        this.user = user;
        loadPurchases();
    }

    /**
     * Carrega as compras do usuário e exibe as informações na caixa de entrada.
     * Este método recupera as compras feitas pelo usuário através do controlador principal e exibe-as na interface gráfica.
     */
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
