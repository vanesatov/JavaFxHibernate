package org.example.javafxhibernate.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.javafxhibernate.Application;
import org.example.javafxhibernate.HibernateUtil;
import org.example.javafxhibernate.Session;
import org.example.javafxhibernate.dao.PeliculaDAO;
import org.example.javafxhibernate.models.Pelicula;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de películas.
 */
public class PeliculasController implements Initializable {
    @javafx.fxml.FXML
    private Button btnAtras;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> cTitulo;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> cDirector;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> cAnho;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> cGenero;
    @javafx.fxml.FXML
    private Button btnAddPeli;
    @javafx.fxml.FXML
    private TableView<Pelicula> table;
    @javafx.fxml.FXML
    private Button btnSalir;

    /**
     * Inicializa el controlador.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle el ResourceBundle utilizado para localizar el objeto raíz, o null si no se ha localizado el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cTitulo.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getTitulo());
        });
        cDirector.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getDirector());
        });
        cAnho.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getAño().toString());
        });
        cGenero.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getGenero());
        });

        table.setRowFactory(tv -> {
            TableRow<Pelicula> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Pelicula rowData = row.getItem();
                    mostrarDetallesPelicula(rowData);
                }
            });
            return row;
        });
        loadPeliculas();
    }

    /**
     * Maneja el evento de acción para el botón de añadir película.
     * Carga la vista para añadir una nueva película.
     */
    @javafx.fxml.FXML
    public void addPelicula(ActionEvent actionEvent) {
        Application.loadFXML("views/addPelicula-view.fxml", "Añadir Película");
    }

    /**
     * Maneja el evento de acción para el botón Mis Copias.
     * Carga la vista de copias del usuario actual.
     */
    @javafx.fxml.FXML
    public void atras(ActionEvent actionEvent) {
        Application.loadFXML("views/copias-view.fxml", "Copias usuario " + Session.currentUser.getNombre_usuario());
    }

    /**
     * Carga la lista de películas desde la base de datos y las muestra en la tabla.
     */
    private void loadPeliculas() {
        List<Pelicula> peliculas = new PeliculaDAO(HibernateUtil.getSessionFactory()).findAll();
        table.getItems().setAll(peliculas);
    }

    /**
     * Maneja el evento de acción para el botón de salir.
     * Carga la vista de login.
     */
    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        Application.loadFXML("views/login-view.fxml", "Login");
    }

    /**
     * Muestra los detalles de la película en una alerta al hacer doble click en la pelicula seleccionada.
     *
     * @param pelicula la película de la cual se mostrarán los detalles.
     */
    private void mostrarDetallesPelicula(Pelicula pelicula) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles de la Película");
        alert.setHeaderText(pelicula.getTitulo());

        TextArea textArea = new TextArea("Director: " + pelicula.getDirector() + "\n"
                + "Año: " + pelicula.getAño() + "\n"
                + "Género: " + pelicula.getGenero() + "\n"
                + "Descripción: " + pelicula.getDescripcion());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setPrefWidth(400);
        textArea.setPrefHeight(200);

        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/example/javafxhibernate/CSS/alert.css").toExternalForm());
        alert.showAndWait();
    }
}
