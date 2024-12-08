package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresEvent;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.Interfaces.RequiresUser;
import com.pbl.controller.mainController;
import com.pbl.models.Evento;
import com.pbl.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Locale;

/**
 * Controlador para a tela de pop-up de revisão de um evento.
 * Permite ao usuário enviar uma revisão com uma avaliação e um comentário sobre um evento.
 */
public class ReviewPopUpController implements RequiresMainController, RequiresUser, RequiresEvent, LanguageChange {

    @FXML
    private Label reviewTitle;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<Integer> gradeSelector;
    @FXML
    private TextArea reviewText;

    private String writeText;
    private String reviewSend;
    private String gradeErro;
    private String eventErro;

    private mainController mainController;
    private NavigatorController navigatorController;
    private Usuario user;
    private Evento event;

    /**
     * Inicializa a interface, carregando as opções de avaliação e atualizando os textos.
     */
    public void initialize() {
        updateLanguage();
        loadGradeSelector();
    }

    /**
     * Configura as dependências do controlador com o controlador principal e de navegação.
     *
     * @param mainController O controlador principal da aplicação.
     * @param navigatorController O controlador de navegação.
     */
    @Override
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    /**
     * Configura o evento sobre o qual a revisão será realizada.
     *
     * @param event O evento sobre o qual o usuário vai escrever a revisão.
     */
    @Override
    public void setEvent(Evento event) {
        this.event = event;
    }

    /**
     * Configura o usuário que está fazendo a revisão.
     *
     * @param user O usuário que está fazendo a revisão.
     */
    @Override
    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     * Manipula o evento de clique no botão de envio da revisão.
     * Valida os campos e, caso tudo esteja correto, envia a revisão do evento.
     */
    @FXML
    public void handleSubmitButton() {
        if(!event.isAtivo()) {
            String comment = reviewText.getText();
            if (gradeSelector.getValue() != null) {
                int grade = gradeSelector.getValue();

                if (verifyReview(comment)) {
                    mainController.reviewEvent(event, user, comment, grade);
                    showSucessAlert(reviewSend);
                    closePopup();
                }
            } else {
                showErrorAlert(gradeErro);
            }
        } else{
            showErrorAlert(eventErro);
        }
    }

    /**
     * Manipula o evento de clique no botão de cancelamento.
     * Fecha o pop-up de revisão sem fazer alterações.
     */
    @FXML
    public void handleCancelButton() {
        closePopup();
    }

    /**
     * Verifica se o comentário da revisão está vazio ou nulo.
     *
     * @param comment O comentário da revisão.
     * @return Retorna true se o comentário for válido (não vazio), caso contrário, false.
     */
    private boolean verifyReview(String comment){
        if(comment == null || comment.isEmpty()){
            showErrorAlert(writeText);
            return false;
        }
        return true;
    }

    /**
     * Exibe um alerta de erro com a mensagem fornecida.
     *
     * @param message A mensagem de erro a ser exibida no alerta.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Exibe um alerta de sucesso com a mensagem fornecida.
     *
     * @param message A mensagem de sucesso a ser exibida no alerta.
     */
    private void showSucessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Carrega as opções de avaliação no ComboBox de seleções de nota.
     * As opções de avaliação variam de 0 a 5.
     */
    public void loadGradeSelector() {
        ObservableList<Integer> gradeOptions = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);
        gradeSelector.setItems(gradeOptions);
    }

    /**
     * Fecha o pop-up de revisão.
     */
    private void closePopup() {
        Stage stage = (Stage) reviewText.getScene().getWindow();
        stage.close();
    }

    /**
     * Atualiza a interface com as traduções adequadas de acordo com o idioma atual.
     */
    private void updateLanguage() {
        submitButton.setText(LanguageManager.getString("submitButton"));
        cancelButton.setText(LanguageManager.getString("cancelButton"));
        reviewTitle.setText(LanguageManager.getString("reviewTitle"));
        reviewText.setPromptText(LanguageManager.getString("reviewText"));
        gradeSelector.setPromptText(LanguageManager.getString("gradeOptions"));
        writeText = LanguageManager.getString("writeText");
        reviewSend = LanguageManager.getString("reviewSend");
        gradeErro = LanguageManager.getString("gradeErro");
        eventErro = LanguageManager.getString("eventErro");
    }

    /**
     * Manipula o evento de mudança de idioma, atualizando os textos da interface.
     *
     * @param currentLocale O local atual para o idioma.
     */
    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    /**
     * Manipula o evento de alternância de fontes.
     * Este método pode ser implementado para modificar o estilo da interface ao mudar a fonte.
     */
    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    public void toggleFont() {

    }
}