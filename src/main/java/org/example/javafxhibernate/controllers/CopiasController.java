package org.example.javafxhibernate.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.javafxhibernate.Application;
import org.example.javafxhibernate.HibernateUtil;
import org.example.javafxhibernate.Session;
import org.example.javafxhibernate.dao.CopiaDAO;
import org.example.javafxhibernate.models.Copia;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar las copias de películas.
 * Implementa la interfaz Initializable para inicializar el controlador después de que su elemento raíz
 * haya sido completamente procesado.
 */
public class CopiasController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn<Copia, String> cTitulo;
    @javafx.fxml.FXML
    private TableColumn<Copia, String> cSoporte;
    @javafx.fxml.FXML
    private Button AgregarCopia;
    @javafx.fxml.FXML
    private TableColumn<Copia, String> cEstado;
    @javafx.fxml.FXML
    private TableView<Copia> table;
    @javafx.fxml.FXML
    private Button btnSalir;
    @javafx.fxml.FXML
    private Button btnPeliculas;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private Button btnEditar;

    private CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());

    /**
     * Inicializa la clase del controlador.
     * Configura las columnas de la tabla y los botones según el rol del usuario(administrador / no administrador).
     *
     * @param url La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si la ubicación no es conocida.
     * @param resourceBundle Los recursos utilizados para localizar el objeto raíz, o null si el objeto raíz no fue localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cTitulo.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getPelicula().getTitulo());
        });
        cSoporte.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getSoporte());
        });
        cEstado.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getEstado());
        });

        btnPeliculas.setVisible(false);
        if (Session.currentUser.getAdmin() == true) {
            btnPeliculas.setVisible(true);
        }

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) return;
            Session.currentCopia = newValue;
            Application.loadFXML("views/copia-view.fxml", "Copia");
        });

        btnEliminar.setOnAction(actionEvent -> eliminar());

        tableRefresh();
    }

    /**
     * Refresca la tabla con las copias del usuario actual.
     */
    private void tableRefresh() {
        table.getItems().clear();
        table.getItems().addAll(new CopiaDAO(HibernateUtil.getSessionFactory()).findByUser(Session.currentUser));
    }

    /**
     * Maneja el evento de acción para el botón de agregar copia.
     * Carga la vista para agregar una nueva copia.
     */
    @javafx.fxml.FXML
    public void addCopia(ActionEvent actionEvent) {
        Session.currentCopia = null;
        Application.loadFXML("views/addCopia-view.fxml", "Nueva Copia");
    }

    /**
     * Maneja el evento de acción para el botón de salir.
     * Cierra la sesión del usuario actual y carga la vista de login.
     */
    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        Session.currentUser = null;
        Application.loadFXML("views/login-view.fxml", "Login");
    }

    /**
     * Maneja el evento de acción para el botón de ir a películas.
     * Carga la vista de películas.
     */
    @javafx.fxml.FXML
    public void irPelis(ActionEvent actionEvent) {
        Session.currentCopia = null;
        Application.loadFXML("views/peliculas-view.fxml", "Películas");
    }

    /**
     * Maneja el evento de acción para el botón de eliminar.
     * Elimina la copia seleccionada de la tabla y de la base de datos.
     */
    @javafx.fxml.FXML
    private void eliminar() {
        Copia seleccionada = table.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación:");
            alerta.setHeaderText("¿Estás seguro de que deseas eliminar la copia seleccionada?");
            alerta.setContentText("Copia a eliminar: " + seleccionada.getPelicula().getTitulo());
            alerta.getDialogPane().getStylesheets().add(getClass().getResource("/org/example/javafxhibernate/CSS/alert.css").toExternalForm());
            Optional<ButtonType> result = alerta.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                copiaDAO.delete(seleccionada);
                table.getItems().remove(seleccionada);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ninguna copia seleccionada");
            alert.setHeaderText("No se ha seleccionado ninguna copia para eliminar");
            alert.setContentText("Por favor, selecciona una copia en la tabla y vuelve a intentarlo.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/example/javafxhibernate/CSS/alert.css").toExternalForm());
            alert.showAndWait();
        }
    }

    /**
     * Maneja el evento de acción para el botón de editar.
     * Carga la vista para editar la copia seleccionada.
     */
    @javafx.fxml.FXML
    public void editar(ActionEvent actionEvent) {
        Copia seleccionada = (Copia) table.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            Session.currentCopia = seleccionada;
            Application.loadFXML("views/updateCopia-view.fxml", "Editar Copia");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ninguna copia seleccionada");
            alert.setHeaderText("No se ha seleccionado ninguna copia para editar");
            alert.setContentText("Por favor, selecciona una copia en la tabla y vuelve a intentarlo.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/example/javafxhibernate/CSS/alert.css").toExternalForm());
            alert.showAndWait();
        }
    }
}
