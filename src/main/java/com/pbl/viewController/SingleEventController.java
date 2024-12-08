package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresEvent;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Card;
import com.pbl.models.Evento;
import com.pbl.models.Review;
import com.pbl.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Controlador responsável por exibir as informações de um único evento, incluindo a compra de ingressos, avaliações e a troca de idioma.
 * Implementa as interfaces necessárias para conectar com o controlador principal, o usuário e o evento.
 */
public class SingleEventController implements RequiresMainController, RequiresUser, RequiresEvent, LanguageChange {

    private com.pbl.controller.mainController mainController;
    private NavigatorController navigatorController;
    private Usuario user;
    private Evento event;
    private String payslip;

    @FXML
    private Label eventTittle;
    @FXML
    private Label eventDescription;
    @FXML
    private Label eventDate;
    @FXML
    private Label eventStatus;
    @FXML
    private Label reviews;
    @FXML
    private Button purchaseButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button languageToggle;
    @FXML
    private Label Rate;
    @FXML
    private VBox commentContainner;
    @FXML
    private ComboBox<String> paymentSelector;
    @FXML
    private ComboBox<String> seatSelector;

    /**
     * Inicializa o controlador, registrando o ouvinte de mudanças no idioma.
     */
    public void initialize() {
        LanguageManager.registerListener(this);
    }

    /**
     * Define as dependências necessárias para o controlador.
     *
     * @param mainController O controlador principal.
     * @param navigatorController O controlador de navegação.
     */
    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    /**
     * Define o usuário atual para o controlador.
     *
     * @param user O usuário atual.
     */
    @Override
    public void setUser(Usuario user) {
        this.user = user;
        loadPaymentSelector();
    }

    /**
     * Define o evento atual para o controlador.
     *
     * @param event O evento a ser exibido.
     */
    @Override
    public void setEvent(Evento event) {
        this.event = event;
        updateEventData();
        loadReviews();
        loadSeatSelector();
    }

    /**
     * Manipula o clique no botão de saída, retornando à tela anterior.
     *
     * @param mouseEvent O evento gerado pelo clique.
     * @throws IOException Se ocorrer um erro ao voltar à tela anterior.
     */
    public void handleExitButton(MouseEvent mouseEvent) throws IOException {
        navigatorController.comeback();
    }

    /**
     * Manipula o clique no botão de compra de ingresso.
     * Verifica os dados fornecidos pelo usuário e realiza a compra do ingresso.
     *
     * @param mouseEvent O evento gerado pelo clique.
     */
    public void handlePurchaseButton(MouseEvent mouseEvent) {
        String paymentMethod = paymentSelector.getValue();
        Card card = null;

        if(event.isAtivo()) {
            if (!paymentMethod.equals("Boleto")) {
                card = mainController.getCardByNumber(paymentMethod);
                String seat = seatSelector.getValue();

                if (seat != null && verifyCredentials(paymentMethod, seat)) {
                    mainController.buyTicket(user, event, card, seat);
                    mainController.removeSeat(seat, event);
                    // Atualiza a lista no ComboBox
                    ObservableList<String> updatedSeats = FXCollections.observableArrayList(mainController.getEventSeats(event.getID()));
                    seatSelector.setItems(updatedSeats);

                    showConfirmationAlert(LanguageManager.getString("event.purchasePass"));
                } else {
                    showConfirmationAlert(LanguageManager.getString("event.seat"));
                }
            } else {
                String seat = seatSelector.getValue();
                if (seat != null && verifyCredentials(paymentMethod, seat)) {
                    mainController.buyTicket(user, event, null, seat);
                    mainController.removeSeat(seat, event);
                    // Atualiza a lista no ComboBox
                    ObservableList<String> updatedSeats = FXCollections.observableArrayList(mainController.getEventSeats(event.getID()));
                    seatSelector.setItems(updatedSeats);

                    showConfirmationAlert(LanguageManager.getString("event.purchasePass"));
                } else {
                    showConfirmationAlert(LanguageManager.getString("event.seat"));
                }
            }
        } else{
            showErrorAlert(LanguageManager.getString("event.finished"));
        }
    }

    /**
     * Verifica as credenciais fornecidas pelo usuário (método de pagamento e assento).
     *
     * @param paymentMethod O método de pagamento selecionado.
     * @param seat O assento selecionado.
     * @return true se as credenciais forem válidas, false caso contrário.
     */
    public boolean verifyCredentials(String paymentMethod, String seat){
        if(Objects.equals(paymentMethod, "")){
           showErrorAlert(LanguageManager.getString("event.payment"));
            return false;
        } else if(Objects.equals(seat, "")){
            showErrorAlert(LanguageManager.getString("event.seat"));
            return false;
        }
        return true;
    }

    /**
     * Exibe uma mensagem de erro em um alerta.
     *
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
     * Exibe uma mensagem de confirmação em um alerta.
     *
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
     * Atualiza os dados do evento exibido na interface.
     */
    private void updateEventData() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        mainController.calculateEventRating(event);
        eventTittle.setText(event.getName());
        eventDescription.setText(event.getDescription());
        String formattedDate = formatter.format(event.getDate());
        eventDate.setText(formattedDate);
        Rate.setText(event.getRate().toString());
        String statusText;
        if (LanguageManager.languageController == 0) {
            statusText = event.isAtivo() ? "Ativo" : "Inativo";
        } else {
            statusText = event.isAtivo() ? "Active" : "Finished";
        }
        eventStatus.setText(statusText);
    }

    /**
     * Atualiza os textos da interface com base no idioma atual.
     */
    public void updateLanguage() {
        reviews.setText(LanguageManager.getString("event.review"));
        cancelButton.setText(LanguageManager.getString("button.cancel"));
        purchaseButton.setText(LanguageManager.getString("event.purchaseButton"));
        languageToggle.setText(LanguageManager.getString("button.language"));
        payslip = LanguageManager.getString("payslip");
    }

    /**
     * Altera o idioma da aplicação entre português e inglês.
     *
     * @param mouseEvent O evento gerado pelo clique no botão de troca de idioma.
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
        updateEventData();
        loadPaymentSelector();
    }

    /**
     * Método chamado quando o idioma da aplicação é alterado.
     * Atualiza os textos da interface.
     *
     * @param currentLocale O idioma atual da aplicação.
     */
    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    /**
     * Método chamado quando a configuração de fonte é alterada.
     * Ajusta o estilo de fonte na interface.
     */
    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    /**
     * Alterna o estilo da fonte entre uma configuração maior e menor, dependendo do estado do controlador de tamanho de fonte.
     * Altera as classes CSS dos elementos da interface para ajustar o estilo de fonte.
     */
    public void toggleFont() {
        if(!LanguageManager.FontSizeController){
            eventTittle.getStyleClass().removeAll("TittleFont", "text-title");
            eventDescription.getStyleClass().removeAll("subtitleFont", "text-subtitle");
            eventStatus.getStyleClass().removeAll("subtitleFont", "text-subtitle");
            eventDate.getStyleClass().removeAll("subtitleFont", "text-subtitle");
            Rate.getStyleClass().removeAll("subtitleFont", "text-subtitle");
            cancelButton.getStyleClass().removeAll("buttonFontCancel", "button-cancel");
            purchaseButton.getStyleClass().removeAll("buttonFontCancel", "button-cancel");
            languageToggle.getStyleClass().removeAll("buttonFontCancel", "button-cancel");

            eventTittle.getStyleClass().add("TittleFont");
            eventDescription.getStyleClass().add("subtitleFont");
            eventStatus.getStyleClass().add("subtitleFont");
            eventDate.getStyleClass().add("subtitleFont");
            Rate.getStyleClass().add("subtitleFont");
            cancelButton.getStyleClass().add("buttonFontCancel");
            purchaseButton.getStyleClass().add("buttonFontCancel");
            languageToggle.getStyleClass().add("buttonFontCancel");
        }else{
            eventTittle.getStyleClass().removeAll("TittleFont", "text-title");
            eventDescription.getStyleClass().removeAll("subtitleFont", "text-subtitle");
            eventStatus.getStyleClass().removeAll("subtitleFont", "text-subtitle");
            eventDate.getStyleClass().removeAll("subtitleFont", "text-subtitle");
            Rate.getStyleClass().removeAll("subtitleFont", "text-subtitle");
            cancelButton.getStyleClass().removeAll("buttonFontCancel", "button-cancel");
            purchaseButton.getStyleClass().removeAll("buttonFontCancel", "button-cancel");
            languageToggle.getStyleClass().removeAll("buttonFontCancel", "button-cancel");

            eventTittle.getStyleClass().add("text-title");
            eventDescription.getStyleClass().add("text-subtitle");
            eventStatus.getStyleClass().add("text-subtitle");
            eventDate.getStyleClass().add("text-subtitle");
            Rate.getStyleClass().add("text-subtitle");
            cancelButton.getStyleClass().add("button-cancel");
            purchaseButton.getStyleClass().add("button-cancel");
            languageToggle.getStyleClass().add("button-cancel");
        }
    }

    /**
     * Carrega as avaliações do evento e as exibe na interface.
     * Para cada avaliação, cria um VBox contendo o login do usuário, a avaliação numérica e o comentário.
     */
    public void loadReviews() {
        List<Review> reviews = mainController.getEventsReview(event.getID());

        commentContainner.getChildren().clear();

        for (Review r : reviews) {
            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.getStyleClass().add("event-box");

            String userLogin = mainController.getUserByID(r.getUser()).getLogin();
            Label tittleLabel = new Label(userLogin);
            tittleLabel.getStyleClass().add("event-title");

            Label descriptionLabel = new Label(r.getComment());
            descriptionLabel.getStyleClass().add("event-description");

            Integer rating = r.getRating();
            Label dataLabel = new Label(rating.toString());
            dataLabel.getStyleClass().add("event-date");

            vbox.getChildren().addAll(tittleLabel, dataLabel, descriptionLabel);

            commentContainner.getChildren().add(vbox);
        }
    }

    /**
     * Carrega o seletor de pagamento, populando com as opções de pagamento disponíveis,
     * incluindo os cartões do usuário e o método de pagamento "Boleto".
     */
    public void loadPaymentSelector() {
        ObservableList<String> paymentOptions = FXCollections.observableArrayList(payslip);
        List<Card> userCards = mainController.getUserCards(user.getID());
        for(Card card : userCards) {
            paymentOptions.add(card.getCardNumber());
        }
        paymentSelector.setItems(paymentOptions);
        paymentSelector.setValue(payslip);
    }

    /**
     * Carrega o seletor de assentos, populando com os assentos disponíveis para o evento.
     */
    public void loadSeatSelector() {
        ObservableList<String> seatOptions = FXCollections.observableArrayList();
        List<String> seats = mainController.getEventSeats(event.getID());
        seatOptions.addAll(seats);
        seatSelector.setItems(seatOptions);
    }
}
