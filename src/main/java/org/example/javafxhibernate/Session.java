package org.example.javafxhibernate;

import org.example.javafxhibernate.models.Copia;
import org.example.javafxhibernate.models.Pelicula;
import org.example.javafxhibernate.models.Usuario;

/**
 * Clase que mantiene la sesión actual de la aplicación.
 */
public class Session {
    /**
     * Usuario actual de la sesión.
     */
    public static Usuario currentUser = null;

    /**
     * Copia actual de la sesión.
     */
    public static Copia currentCopia = null;

}
