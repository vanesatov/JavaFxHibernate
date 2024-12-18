package org.example.javafxhibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.example.javafxhibernate.HellaApplication;
import org.example.javafxhibernate.HibernateUtil;
import org.example.javafxhibernate.Session;
import org.example.javafxhibernate.dao.CopiaDAO;
import org.example.javafxhibernate.dao.PeliculaDAO;
import org.example.javafxhibernate.models.Copia;
import org.example.javafxhibernate.models.Pelicula;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase controlador para agregar una nueva copia de una película.
 * Implementa la interfaz Initializable para inicializar el controlador después de que su elemento raíz haya sido completamente procesado.
 */

public class AddCopiaController implements Initializable {
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private ChoiceBox chbTitulo;
    @javafx.fxml.FXML
    private ChoiceBox chbEstado;
    @javafx.fxml.FXML
    private ChoiceBox chbSoporte;

    private CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
    private PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());


    /**
     * Inicializa la clase del controlador.
     * Llena los choice boxes con los títulos, estados y soportes disponibles.
     *
     * @param url La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si la ubicación no es conocida.
     * @param resourceBundle Los recursos utilizados para localizar el objeto raíz, o null si el objeto raíz no fue localizado.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        chbTitulo.getItems().addAll(copiaDAO.getTitulos());
        chbTitulo.getSelectionModel().selectFirst();


        chbEstado.getItems().addAll(copiaDAO.getEstados());
        chbEstado.getSelectionModel().selectFirst();

        chbSoporte.getItems().addAll(copiaDAO.getSoportes());
        chbSoporte.getSelectionModel().selectFirst();

    }

    /**
     * Maneja el evento de acción para el botón de cancelar. Carga la vista de copias.
     */

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        HellaApplication.loadFXML("views/copias-view.fxml", "Copias usuario " + Session.currentUser.getNombre_usuario());
    }

    /**
     * Maneja el evento de acción para el botón de guardar. Crea una nueva copia de una película y la guarda en la base de datos.
     * Luego, carga la vista de copias y muestra una alerta de confirmación.
     */

    @javafx.fxml.FXML
    public void guardarCopia(ActionEvent actionEvent) {
        Copia nuevaCopia = new Copia();
        nuevaCopia.setEstado((String) chbEstado.getValue());
        nuevaCopia.setSoporte((String) chbSoporte.getValue());
        nuevaCopia.setUsuario(Session.currentUser);

        Pelicula pelicula = peliculaDAO.findPeliculaByTitulo((String) chbTitulo.getValue());
        nuevaCopia.setPelicula(pelicula);

        copiaDAO.save(nuevaCopia);

        HellaApplication.loadFXML("views/copias-view.fxml", "Copias usuario " + Session.currentUser.getNombre_usuario());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Copia guardada");
        alert.setHeaderText(null);
        alert.setContentText("La copia ha sido guardada correctamente.");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/example/javafxhibernate/CSS/alert.css").toExternalForm());
        alert.showAndWait();
    }
}
