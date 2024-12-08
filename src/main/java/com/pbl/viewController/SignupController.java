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

/**
 * Controlador para a tela de cadastro de um novo usuário.
 * Permite ao usuário se cadastrar fornecendo informações como login, senha, nome, CPF e e-mail.
 */
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

    /**
     * Inicializa o controlador e registra o ouvinte para alterações de idioma.
     */
    public void initialize(){
        LanguageManager.registerListener(this);
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
     * Manipula o evento de clique no botão de cadastro.
     * Verifica se os dados estão corretos e, caso estejam, cria um novo usuário e o navega para a página principal.
     *
     * @param actionEvent O evento gerado pelo clique no botão de cadastro.
     * @throws IOException Se ocorrer um erro ao navegar para outra página.
     */
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
                showConfimationAlert(signUpPass);
                loginField.clear();
                passwordField.clear();
                usernameField.clear();
                CpfField.clear();
                emailField.clear();


                navigatorController.showMainPage();
            } else {
                showErrorAlert(signUpErro);
            }
        }
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
     * Exibe um alerta de confirmação com a mensagem fornecida.
     *
     * @param message A mensagem de confirmação a ser exibida no alerta.
     */
    private void showConfimationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Manipula o evento de clique no botão de cancelamento.
     * Fecha o formulário de cadastro e volta para a página anterior.
     *
     * @param actionEvent O evento gerado pelo clique no botão de cancelamento.
     * @throws IOException Se ocorrer um erro ao navegar para a página anterior.
     */
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

    /**
     * Verifica se as credenciais fornecidas pelo usuário são válidas.
     * Certifica-se de que todos os campos obrigatórios estejam preenchidos.
     *
     * @param login O login do usuário.
     * @param password A senha do usuário.
     * @param cpf O CPF do usuário.
     * @param email O e-mail do usuário.
     * @param username O nome de usuário.
     * @return Retorna true se todas as credenciais forem válidas, caso contrário, false.
     */
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

    /**
     * Verifica se o login fornecido já está em uso por outro usuário.
     *
     * @param login O login a ser verificado.
     * @return Retorna true se o login estiver disponível (não em uso), caso contrário, false.
     */
    public boolean verifyLogin(String login){
        Usuario user = mainController.getUserByLogin(login);
        return user == null;
    }

    /**
     * Atualiza o texto e os prompts da interface com base no idioma atual.
     * Este método é chamado sempre que o idioma da aplicação é alterado.
     */
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

    /**
     * Altera o idioma da aplicação entre inglês e português.
     * Este método é acionado ao clicar no botão de troca de idioma.
     *
     * @param mouseEvent O evento gerado pelo clique do botão de troca de idioma.
     */

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

    /**
     * Método chamado quando o idioma da aplicação é alterado.
     * Atualiza os textos da interface com base no novo idioma.
     *
     * @param currentLocale O idioma atual da aplicação.
     */
    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    /**
     * Método chamado quando a fonte da interface é alterada.
     * Altera o estilo de fonte de todos os elementos visuais que dependem dessa configuração.
     */
    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    /**
     * Alterna o estilo de fonte entre um estilo maior e menor.
     * Aplica as mudanças de estilo nas etiquetas, botões e outros componentes visuais.
     */
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

    /**
     * Altera o tamanho da fonte da interface.
     * Este método é acionado ao clicar no botão de alteração de fonte.
     *
     * @param actionEvent O evento gerado pelo clique no botão de alteração de fonte.
     */
    public void changeFont(ActionEvent actionEvent) {
        LanguageManager.FontSizeController = !LanguageManager.FontSizeController;
        LanguageManager.notifyListeners();
    }
}
