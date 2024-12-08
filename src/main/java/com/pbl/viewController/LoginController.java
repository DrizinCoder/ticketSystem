package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;
import com.pbl.Interfaces.RequiresMainController;
import com.pbl.controller.mainController;
import com.pbl.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;

/**
 * Controlador responsável pela tela de login, gerenciando a autenticação do usuário, troca de idioma e ajuste do tamanho da fonte.
 *
 * Esta classe lida com eventos de login, exibe mensagens de erro ou sucesso e gerencia a troca de idiomas e tamanhos de fonte na interface.
 */
public class LoginController implements RequiresMainController, LanguageChange {

    private mainController mainController;

    private NavigatorController navigatorController;

    private String loginSucessfull;
    private String loginErro;
    private String loginPass;
    private String loginErroMsg;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label subtitleLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label noAccountMessage;

    @FXML
    private Label signUpLink;

    @FXML
    private Button languageToggle;

    @FXML
    private Button FontButton;

    /**
     * Inicializa o controlador e registra o ouvinte de mudança de idioma.
     */
    public void initialize(){
        LanguageManager.registerListener(this);
    }

    /**
     * Define as dependências do controlador, como o controlador principal e o controlador de navegação.
     *
     * @param mainController O controlador principal da aplicação.
     * @param navigatorController O controlador responsável pela navegação entre telas.
     */
    public void setDependencies(mainController mainController, NavigatorController navigatorController) {
        this.mainController = mainController;
        this.navigatorController = navigatorController;
        LanguageManager.notifyListeners();
    }

    /**
     * Método acionado quando o botão de login é pressionado. Verifica as credenciais do usuário e redireciona para a tela principal em caso de sucesso.
     *
     * @throws IOException Se ocorrer algum erro durante a navegação para a próxima tela.
     */
    @FXML
    protected void handleLoginButton() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();


        if (mainController.signIn(username, password)) {
            Usuario user = mainController.getUserByLogin(username);
            navigatorController.setLoggedUser(user);
            showConfimationAlert(loginSucessfull, loginPass);
            passwordField.clear();

            navigatorController.showMainPage();
        } else {
            showErrorAlert(loginErro, loginErroMsg);
        }
    }

    /**
     * Exibe um alerta de erro com título e mensagem personalizados.
     *
     * @param title O título da mensagem de erro.
     * @param message O conteúdo da mensagem de erro.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Exibe um alerta de confirmação com título e mensagem personalizados.
     *
     * @param title O título da mensagem de confirmação.
     * @param message O conteúdo da mensagem de confirmação.
     */
    private void showConfimationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Redireciona o usuário para a tela de registro ao clicar no link de cadastro.
     *
     * @param mouseEvent O evento de clique do mouse.
     * @throws IOException Se ocorrer algum erro durante a navegação.
     */
    public void handleSignUpLink(MouseEvent mouseEvent) throws IOException {
        navigatorController.showSignUp();
    }

    /**
     * Atualiza os textos e rótulos da interface gráfica com base no idioma atual.
     * Este método obtém as traduções apropriadas usando o `LanguageManager` e aplica os textos
     * nos componentes gráficos da tela de login.
     */
    public void updateLanguage(){
        welcomeLabel.setText(LanguageManager.getString("title.welcome"));
        subtitleLabel.setText(LanguageManager.getString("subtitle.credentials"));
        loginButton.setText(LanguageManager.getString("button.login"));
        noAccountMessage.setText(LanguageManager.getString("message.noAccount"));
        signUpLink.setText(LanguageManager.getString("link.signUp"));
        usernameField.setPromptText(LanguageManager.getString("field.username"));
        passwordField.setPromptText(LanguageManager.getString("field.password"));
        languageToggle.setText(LanguageManager.getString("button.language"));
        FontButton.setText(LanguageManager.getString("button.font"));
        loginSucessfull = LanguageManager.getString("operation.sucess");
        loginErro = LanguageManager.getString(("operation.error"));
        loginPass = LanguageManager.getString("login.pass");
        loginErroMsg = LanguageManager.getString("login.error");
    }

    /**
     * Alterna o idioma da aplicação entre inglês e português (Brasil).
     * Este método altera o idioma com base no valor de `LanguageManager.languageController`
     * e notifica todos os ouvintes sobre a mudança de idioma.
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
    }

    /**
     * Altera o tamanho da fonte da interface gráfica.
     * O método alterna entre dois tamanhos de fonte (pequeno e grande) ao modificar o controlador `FontSizeController`.
     *
     * Este método também notifica os ouvintes para que a interface seja atualizada com o novo tamanho de fonte.
     */
    public void changeFont(){
        LanguageManager.FontSizeController = !LanguageManager.FontSizeController;
        LanguageManager.notifyListeners();
    }

    /**
     * Método chamado quando ocorre uma mudança no idioma da aplicação.
     * Ele atualiza os textos na interface gráfica para refletir o novo idioma.
     *
     * @param currentLocale O idioma atual da aplicação.
     */
    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    /**
     * Método chamado quando ocorre uma mudança no tamanho da fonte da aplicação.
     * Ele chama o método `toggleFont()` para ajustar o estilo dos componentes de interface de acordo com o novo tamanho da fonte.
     */
    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    /**
     * Ajusta o estilo da fonte nos componentes da interface gráfica.
     * Aplica ou remove as classes de estilo apropriadas de acordo com o tamanho da fonte selecionado (pequeno ou grande).
     *
     * Caso o controlador `FontSizeController` indique que a fonte deve ser maior, ele aplica estilos com fontes maiores; caso contrário, usa estilos de fontes menores.
     */

    public void toggleFont() {
        //Remove os estilos existentes
        welcomeLabel.getStyleClass().removeAll("TittleFont", "text-title");
        subtitleLabel.getStyleClass().removeAll("subtitleFont", "text-subtitle");
        loginButton.getStyleClass().removeAll("buttonFontLogin", "button-login");
        languageToggle.getStyleClass().removeAll("buttonFontCancel", "button-cancel");
        FontButton.getStyleClass().removeAll("buttonFontCancel", "button-cancel");
        noAccountMessage.getStyleClass().removeAll("label-message2", "label-message");
        signUpLink.getStyleClass().removeAll("label-link", "label-link2");

        if(!LanguageManager.FontSizeController){
            //Aplica os estilos corretos
            welcomeLabel.getStyleClass().add("TittleFont");
            subtitleLabel.getStyleClass().add("subtitleFont");
            loginButton.getStyleClass().add("buttonFontLogin");
            languageToggle.getStyleClass().add("buttonFontCancel");
            FontButton.getStyleClass().add("buttonFontCancel");
            noAccountMessage.getStyleClass().add("label-message2");
            signUpLink.getStyleClass().add("label-link2");
        } else{
            //Aplica os estilos corretos
            welcomeLabel.getStyleClass().add("text-title");
            subtitleLabel.getStyleClass().add("text-subtitle");
            loginButton.getStyleClass().add("button-login");
            languageToggle.getStyleClass().add("button-cancel");
            FontButton.getStyleClass().add("button-cancel");
            noAccountMessage.getStyleClass().add("label-message");
            signUpLink.getStyleClass().add("label-link");
        }
    }
}
