package org.example.javafxhibernate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 */
public class Application extends javafx.application.Application {

    /**
     * Ventana principal de la aplicación.
     */
    private static Stage ventana;

    /**
     * Método de inicio de la aplicación.
     *
     * @param stage la ventana principal de la aplicación.
     * @throws IOException si ocurre un error al cargar la vista FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        loadFXML("views/login-view.fxml","Login");
        stage.show();
    }

    /**
     * Carga una vista FXML y la establece en la ventana principal.
     *
     * @param view  la ruta de la vista FXML.
     * @param title el título de la ventana.
     */
    public static void loadFXML(String view, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(),800,600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
        //ventana.setResizable(false);
    }

    /**
     * Método principal de la aplicación.
     *
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}