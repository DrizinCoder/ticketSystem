package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Card;
import com.pbl.models.Review;
import com.pbl.models.Usuario;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Controlador responsável pela gestão de cartões de crédito do usuário.
 * Permite adicionar, remover e visualizar os cartões do usuário.
 */
public class CardManagmentController implements RequiresMainController, RequiresUser, LanguageChange {

    private mainController mainController;
    private NavigatorController navigatorController;
    private Usuario user;

    @FXML
    private TextField cardHolderNameField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private DatePicker expiryDatePicker;

    @FXML
    private TextField cvvField;

    @FXML
    private Button addCardButton;

    @FXML
    private Button backButton;

    @FXML
    private VBox cardListContainer;

    @FXML
    private Label cardTitle;

    @FXML
    private Label cardTitle2;

    @FXML
    private Button languageToggle;

    private Text ExpiryDate;
    private Text CardNumberInfo;


    /**
     * Inicializa o controlador, registrando o ouvinte de alterações de idioma.
     * Configura os textos iniciais de data de expiração e informações do número do cartão.
     */
    public void initialize() {
        LanguageManager.registerListener(this);
        ExpiryDate = new Text();
        ExpiryDate.setText(LanguageManager.getString("ExpiryDate"));
        CardNumberInfo = new Text();
        CardNumberInfo.setText(LanguageManager.getString("CardNumberInfo"));
    }

    /**
     * Define as dependências do controlador, incluindo o controlador principal e o controlador de navegação.
     * @param mainController Controlador principal da aplicação.
     * @param navigatorController Controlador de navegação.
     */
    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    /**
     * Define o usuário atual e carrega os cartões associados a ele.
     * @param user O usuário cujos cartões serão carregados.
     */
    @Override
    public void setUser(Usuario user) {
        this.user = user;
        loadCard();
    }

    /**
     * Manipula o evento de adicionar um novo cartão.
     * Verifica a validade dos dados inseridos e tenta adicionar o cartão ao sistema.
     * @param actionEvent Evento disparado ao clicar no botão de adicionar cartão.
     */
    public void handleAddCard(ActionEvent actionEvent) {
        String holderName = cardHolderNameField.getText();
        String cardNumber = cardNumberField.getText();
        Date expiryDate = convertDateType(expiryDatePicker.getValue());
        if(verifyCvv() && !cvvField.getText().isEmpty()) {
            int cvv = Integer.parseInt(cvvField.getText());
            if(verifyCredentials(holderName, cardNumber, expiryDate.toString())) {
                try {
                    mainController.addCreditCard(holderName, cardNumber, expiryDate, cvv, user.getID());
                    showConfirmationAlert(LanguageManager.getString("cardSucess"));
                    loadCard();
                } catch (IllegalArgumentException e) {
                    showErrorAlert(e.getMessage());
                }
            }
        }
    }

    /**
     * Verifica se o CVV inserido é um número válido.
     * @return true se o CVV for válido, false caso contrário.
     */
    public boolean verifyCvv() {
        try {
                Integer.parseInt(cvvField.getText());
                return true;
        } catch (NumberFormatException e) {
                showErrorAlert(LanguageManager.getString("cardErro"));
                return false;
        }
    }

    /**
     * Verifica a validade dos dados inseridos para o nome do titular, número do cartão e data de expiração.
     * @param holderName Nome do titular do cartão.
     * @param cardNumber Número do cartão.
     * @param expiryDate Data de expiração do cartão.
     * @return true se todos os dados forem válidos, false caso contrário.
     */
    public boolean verifyCredentials(String holderName, String cardNumber, String expiryDate) {
        if(Objects.equals(holderName, "")){
            showErrorAlert(LanguageManager.getString("cardErro"));
            return false;
        } else if(Objects.equals(cardNumber, "")){
            showErrorAlert(LanguageManager.getString("cardErro"));
            return false;
        } else if(Objects.equals(expiryDate, "")){
            showErrorAlert(LanguageManager.getString("cardErro"));
            return false;
        }
        return true;
    }

    /**
     * Manipula a remoção de um cartão.
     * @param card O cartão a ser removido.
     */
    public void handleRemoveCard(Card card) {
        mainController.removeCreditCard(card);
        loadCard();
    }

    /**
     * Manipula o evento de voltar para a tela anterior.
     * @param actionEvent Evento disparado ao clicar no botão de voltar.
     * @throws IOException Se ocorrer um erro ao navegar para a tela anterior.
     */
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

    /**
     * Converte uma data do tipo LocalDate para Date.
     * @param localDate A data a ser convertida.
     * @return A data convertida ou null se a conversão falhar.
     */
    public Date convertDateType(LocalDate localDate) {
        if(localDate != null) {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else{
            showErrorAlert(LanguageManager.getString("cardErro"));
            return null;
        }
    }

    /**
     * Exibe uma mensagem de alerta de erro.
     * @param message A mensagem de erro a ser exibida.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Exibe uma mensagem de confirmação.
     * @param message A mensagem de confirmação a ser exibida.
     */
    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Carrega a lista de cartões do usuário e os exibe na interface.
     */
    public void loadCard(){
        List<Card> cards = mainController.getUserCards(user.getID());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        cardListContainer.getChildren().clear();

        for (Card c : cards) {
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.getStyleClass().add("event-box");

            Label tittleLabel = new Label(CardNumberInfo.getText()+ ": " + c.getCardNumber());
            tittleLabel.getStyleClass().add("event-title");

            String FormattedDate = formatter.format(c.getExpiryDate());
            Label dataLabel = new Label(ExpiryDate.getText() + ": " + FormattedDate);
            dataLabel.getStyleClass().add("event-date");

            Button actionButton = new Button("Deletar");
            actionButton.getStyleClass().add("button-delete");
            actionButton.getStyleClass().add("event-button");
            actionButton.setOnAction(e -> {
                handleRemoveCard(c);
            });

            // Colocar data e botão dentro de uma HBox
            HBox hbox = new HBox();
            hbox.setSpacing(10); // Espaçamento horizontal
            hbox.getChildren().addAll(dataLabel, actionButton);

            // Adicionar título e HBox ao VBox
            vbox.getChildren().addAll(tittleLabel, hbox);

            cardListContainer.getChildren().add(vbox);
        }
    }

    /**
     * Atualiza os textos e elementos da interface de acordo com o idioma atual.
     * Este método altera o conteúdo textual dos elementos gráficos baseando-se na configuração do idioma,
     * utilizando a classe `LanguageManager` para acessar as traduções.
     */
    public void updateLanguage(){
        cardTitle.setText(LanguageManager.getString("card.title"));
        addCardButton.setText(LanguageManager.getString("card.addButton"));
        cardHolderNameField.setPromptText(LanguageManager.getString("card.HolderName"));
        cardNumberField.setPromptText(LanguageManager.getString("card.number"));
        expiryDatePicker.setPromptText(LanguageManager.getString("card.date"));
        cvvField.setPromptText(LanguageManager.getString("card.cvv"));
        backButton.setText(LanguageManager.getString("button.cancel"));
        cardTitle2.setText(LanguageManager.getString("card.title2"));
        languageToggle.setText(LanguageManager.getString("button.language"));
        ExpiryDate.setText(LanguageManager.getString("ExpiryDate"));
        CardNumberInfo.setText(LanguageManager.getString("CardNumberInfo"));
    }

    /**
     * Alterna o idioma da interface entre inglês e português (Brasil).
     * O método verifica o idioma atual e alterna para o outro idioma, atualizando a interface em tempo real.
     *
     * @param mouseEvent O evento de clique do mouse para alternar o idioma.
     */
    public void changeLanguage(MouseEvent mouseEvent) {
        if (LanguageManager.languageController == 0) {
            LanguageManager.setLocale(Locale.ENGLISH);
            LanguageManager.languageController = 1;
        } else {
            LanguageManager.setLocale(Locale.forLanguageTag("pt-BR"));
            LanguageManager.languageController = 0;
        }
        LanguageManager.notifyListeners();
        loadCard();
    }

    /**
     * Método de callback acionado quando o idioma é alterado.
     * Atualiza os elementos da interface com os textos correspondentes ao novo idioma.
     *
     * @param currentLocale O idioma atual configurado na aplicação.
     */
    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    /**
     * Método de callback acionado quando o tamanho da fonte é alterado.
     * Este método altera a aparência dos elementos da interface gráfica com base no tamanho da fonte.
     */
    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    /**
     * Alterna entre diferentes estilos de fonte para os elementos da interface.
     * Se a variável `LanguageManager.FontSizeController` for verdadeira, aplica um estilo de fonte maior.
     * Caso contrário, aplica um estilo de fonte menor.
     */
    public void toggleFont() {
        cardTitle.getStyleClass().removeAll("text-title", "text-title2");
        addCardButton.getStyleClass().removeAll("button-login", "button-login2");
        backButton.getStyleClass().removeAll("button-cancel", "button-cancel2");
        languageToggle.getStyleClass().removeAll("button-cancel", "button-cancel2");

        if(!LanguageManager.FontSizeController){
            cardTitle.getStyleClass().add("text-title2");
            addCardButton.getStyleClass().add("button-login2");
            languageToggle.getStyleClass().add("button-cancel2");
            backButton.getStyleClass().add("button-cancel2");
        } else{
            cardTitle.getStyleClass().add("text-title");
            addCardButton.getStyleClass().add("button-login");
            languageToggle.getStyleClass().add("button-cancel");
            backButton.getStyleClass().add("button-cancel");
        }
    }
}
