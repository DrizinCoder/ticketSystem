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

/**
 * Controlador da página de perfil do usuário.
 * Permite visualizar e editar as informações do perfil do usuário, como login, senha, nome, CPF e e-mail.
 */
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

    private String wrongInfo;
    private String editPass;

    /**
     * Inicializa o controlador e registra o ouvinte de mudanças no idioma.
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
     * Configura o usuário cujas informações serão exibidas e editadas.
     *
     * @param user O usuário cujas informações serão exibidas e editadas.
     */
    @Override
    public void setUser(Usuario user) {
        this.user = user;
        updateUserData();
    }

    /**
     * Manipula o evento de clicar no botão de confirmação para editar o perfil do usuário.
     * Verifica se as credenciais são válidas e, em seguida, atualiza o perfil do usuário.
     *
     * @param actionEvent O evento de ação do clique no botão de confirmação.
     */
    public void handleEditButton(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        if(verifyCredintials(login,password, email, username)) {
            if(verifyLogin(login)) {
                mainController.editPerfil(user, username, email, password, login);
                Usuario userUpdated = mainController.getUserByID(user.getID());
                messageLabel.setText(LanguageManager.getString("perfil.messageLabel"));
                navigatorController.setLoggedUser(userUpdated);
                LanguageManager.notifyListeners();
            }
            else{
                messageLabel.setText(LanguageManager.getString("perfil.messageErrorLabel"));
            }
        }
    }

    /**
     * Verifica se o login fornecido é único (não existe outro usuário com o mesmo login).
     *
     * @param login O login a ser verificado.
     * @return Retorna true se o login for único, caso contrário, false.
     */
    public boolean verifyLogin(String login){
        Usuario user = mainController.getUserByLogin(login);
        return user == null;
    }

    /**
     * Manipula o evento de clicar no botão de cancelamento para voltar à tela anterior.
     *
     * @param actionEvent O evento de ação do clique no botão de cancelamento.
     * @throws IOException Se ocorrer um erro ao voltar para a tela anterior.
     */
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        navigatorController.comeback();
    }

    /**
     * Altera o idioma da interface entre português e inglês.
     * Atualiza a interface de acordo com a escolha do idioma.
     *
     * @param mouseEvent O evento de ação do clique no botão de alternância de idioma.
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
     * Atualiza os campos de entrada com os dados do usuário atual.
     */
    public void updateUserData(){
        loginField.setText(user.getLogin());
        passwordField.setText(user.getSenha());
        usernameField.setText(user.getNome());
        CpfField.setText(user.getCpf());
        emailField.setText(user.getEmail());
    }

    /**
     * Atualiza os textos e mensagens da interface de acordo com o idioma atual.
     */
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

    /**
     * Verifica se as credenciais fornecidas para editar o perfil do usuário são válidas.
     *
     * @param login O login do usuário.
     * @param password A senha do usuário.
     * @param email O e-mail do usuário.
     * @param username O nome do usuário.
     * @return Retorna true se as credenciais forem válidas, caso contrário, false.
     */
    public boolean verifyCredintials(String login, String password, String email, String username) {
        if(Objects.equals(login, "")){
            messageLabel.setText(LanguageManager.getString("perfil.messageLabelErro"));
            return false;
        } else if(Objects.equals(password, "")){
            messageLabel.setText(LanguageManager.getString("perfil.messageLabelErro"));
            return false;
        } else if(Objects.equals(username, "")){
            messageLabel.setText(LanguageManager.getString("perfil.messageLabelErro"));
            return false;
        } else if(Objects.equals(email, "")){
            messageLabel.setText(LanguageManager.getString("perfil.messageLabelErro"));
            return false;
        }
        return true;
    }

    /**
     * Manipula a mudança no idioma, chamando o método para atualizar os textos da interface.
     *
     * @param currentLocale O local atual para o idioma.
     */
    @Override
    public void onLocaleChange(Locale currentLocale) {
        updateLanguage();
    }

    /**
     * Manipula o evento de alternância de fonte.
     * Alterna entre dois tamanhos de fonte.
     */
    @Override
    public void onLocalToggleFont() {
        toggleFont();
    }

    /**
     * Manipula a mudança no tamanho da fonte de exibição.
     * Alterna entre dois estilos de fonte.
     */
    public void toggleFont() {
        titleLabel.getStyleClass().removeAll("text-title", "text-title2");
        subtitleLabel.getStyleClass().removeAll("text-subtitle", "text-subtitle2");
        confirmButton.getStyleClass().removeAll("button-login", "button-login2");
        backButton.getStyleClass().removeAll("button-cancel", "button-cancel2");
        languageToggle.getStyleClass().removeAll("button-cancel", "button-cancel2");

        if(!LanguageManager.FontSizeController){
            titleLabel.getStyleClass().add("text-title2");
            subtitleLabel.getStyleClass().add("text-subtitle2");
            confirmButton.getStyleClass().add("button-login2");
            backButton.getStyleClass().add("button-cancel2");
            languageToggle.getStyleClass().add("button-cancel2");
        }else{
            titleLabel.getStyleClass().add("text-title");
            subtitleLabel.getStyleClass().add("text-subtitle");
            confirmButton.getStyleClass().add("button-login");
            backButton.getStyleClass().add("button-cancel");
            languageToggle.getStyleClass().add("button-cancel");
        }
    }
}
