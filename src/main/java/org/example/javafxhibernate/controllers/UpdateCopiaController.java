package org.example.javafxhibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.example.javafxhibernate.Application;
import org.example.javafxhibernate.HibernateUtil;
import org.example.javafxhibernate.Session;
import org.example.javafxhibernate.dao.CopiaDAO;
import org.example.javafxhibernate.models.Copia;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de actualización de copias.
 */
public class UpdateCopiaController implements Initializable {
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Label lbEstado;
    @javafx.fxml.FXML
    private Label lbTitulo;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private Label lbSoporte;
    @javafx.fxml.FXML
    private ChoiceBox chbEstado;
    @javafx.fxml.FXML
    private ChoiceBox chbSoporte;

    private CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());

    /**
     * Inicializa el controlador.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle el ResourceBundle utilizado para localizar el objeto raíz, o null si no se ha localizado el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (Session.currentCopia != null) {
            lbTitulo.setText(Session.currentCopia.getPelicula().getTitulo());
        }

        chbEstado.getItems().addAll(copiaDAO.getEstados());
        chbEstado.getSelectionModel().select(Session.currentCopia.getEstado());

        chbSoporte.getItems().addAll(copiaDAO.getSoportes());
        chbSoporte.getSelectionModel().select(Session.currentCopia.getSoporte());

    }

    /**
     * Maneja el evento de acción para el botón de cancelar.
     * Carga la vista de copias del usuario actual.
     */
    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {

        Application.loadFXML("views/copias-view.fxml", "Copias usuario " + Session.currentUser.getNombre_usuario());
    }

    /**
     * Maneja el evento de acción para el botón de guardar copia.
     * Guarda la copia actual o crea una nueva copia y la guarda.
     */
    @javafx.fxml.FXML
    public void guardarCopia(ActionEvent actionEvent) {
        if (Session.currentCopia != null) {
            Session.currentCopia.setEstado((String) chbEstado.getValue());
            Session.currentCopia.setSoporte((String) chbSoporte.getValue());
            copiaDAO.update(Session.currentCopia);
        } else {
            Copia nuevaCopia = new Copia();
            nuevaCopia.setEstado((String) chbEstado.getValue());
            nuevaCopia.setSoporte((String) chbSoporte.getValue());
            nuevaCopia.setUsuario(Session.currentUser);
            copiaDAO.save(nuevaCopia);
        }
        Application.loadFXML("views/copias-view.fxml", "Copias usuario " + Session.currentUser.getNombre_usuario());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Copia guardada");
        alert.setHeaderText(null);
        alert.setContentText("La copia ha sido guardada correctamente.");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/example/javafxhibernate/CSS/alert.css").toExternalForm());
        alert.showAndWait();
    }
}
