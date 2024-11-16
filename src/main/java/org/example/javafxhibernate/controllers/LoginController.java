package org.example.javafxhibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.javafxhibernate.Application;
import org.example.javafxhibernate.HibernateUtil;
import org.example.javafxhibernate.Session;
import org.example.javafxhibernate.dao.UsuarioDAO;
import org.example.javafxhibernate.models.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de login.
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label info;

    /**
     * Inicializa el controlador.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle el ResourceBundle utilizado para localizar el objeto raíz, o null si no se ha localizado el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Maneja el evento de acción para el botón de salir.
     * Cierra la aplicación.
     */
    @FXML
    public void salir(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Maneja el evento de acción para el botón de login.
     * Valida el usuario y carga la vista de copias si el usuario es válido.
     */
    @FXML
    public void login(ActionEvent actionEvent) {
        Usuario user = new UsuarioDAO(HibernateUtil.getSessionFactory()).validarUsuario(txtUsuario.getText(), txtPassword.getText());
        if (user == null) {
            info.setText("Usuario no encontrado");
        } else {
            Session.currentUser = user;

            Application.loadFXML("views/copias-view.fxml", "Copias usuario " + user.getNombre_usuario());
        }
    }
}