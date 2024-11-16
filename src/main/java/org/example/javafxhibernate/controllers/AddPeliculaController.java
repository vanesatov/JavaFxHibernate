package org.example.javafxhibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.javafxhibernate.Application;
import org.example.javafxhibernate.HibernateUtil;
import org.example.javafxhibernate.dao.PeliculaDAO;
import org.example.javafxhibernate.models.Pelicula;
import org.example.javafxhibernate.controllers.AddCopiaController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para agregar una nueva película.
 * Implementa la interfaz Initializable para inicializar el controlador después de que su elemento raíz
 * haya sido completamente procesado.
 */
public class AddPeliculaController implements Initializable {
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerAnho;
    @javafx.fxml.FXML
    private Label lbDirector;
    @javafx.fxml.FXML
    private TextField texfieldTitulo;
    @javafx.fxml.FXML
    private Label lbGenero;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Label lbAnho;
    @javafx.fxml.FXML
    private Label lbDescripcion;
    @javafx.fxml.FXML
    private Label lbTitulo;
    @javafx.fxml.FXML
    private TextArea textareaDescripcion;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private ComboBox<String> comboGenero;
    @javafx.fxml.FXML
    private TextField textfieldDirector;

    /**
     * Inicializa la clase del controlador.
     * Llena el combo box de géneros y configura el spinner de años.
     *
     * @param url La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si la ubicación no es conocida.
     * @param resourceBundle Los recursos utilizados para localizar el objeto raíz, o null si el objeto raíz no fue localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboGenero.getItems().addAll("Sci-Fi", "Drama", "Terror", "Fantasía", "Acción", "Aventura", "Musical", "Romántica", "Animación");

        spinnerAnho.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2024, 2024, 1)
        );
    }

    /**
     * Maneja el evento de acción para el botón de guardar.
     * Crea una nueva película y la guarda en la base de datos.
     * Luego, carga la vista de películas y muestra una alerta de confirmación.
     */

    @javafx.fxml.FXML
    public void guardarPeli(ActionEvent actionEvent) {
        String titulo = texfieldTitulo.getText();
        String director = textfieldDirector.getText();
        String genero = comboGenero.getValue();
        int anho = spinnerAnho.getValue();
        String descripcion = textareaDescripcion.getText();

        if (titulo.isEmpty() || director.isEmpty() || genero == null || descripcion.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, rellene todos los campos para guardar.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/example/javafxhibernate/CSS/alert.css").toExternalForm());
            alert.showAndWait();
            return;
        }

        Pelicula nuevaPelicula = new Pelicula();
        nuevaPelicula.setTitulo(titulo);
        nuevaPelicula.setDirector(director);
        nuevaPelicula.setGenero(genero);
        nuevaPelicula.setAño(anho);
        nuevaPelicula.setDescripcion(descripcion);

        new PeliculaDAO(HibernateUtil.getSessionFactory()).save(nuevaPelicula);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Película Guardada");
        alert.setHeaderText(null);
        alert.setContentText("La película se ha guardado correctamente.");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/org/example/javafxhibernate/CSS/alert.css").toExternalForm());
        alert.showAndWait();

        Application.loadFXML("views/peliculas-view.fxml", "Películas");
    }

    /**
     * Maneja el evento de acción para el botón de cancelar.
     * Carga la vista de películas.
     */

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        Application.loadFXML("views/peliculas-view.fxml", "Películas");
    }
}
