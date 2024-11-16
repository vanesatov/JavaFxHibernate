package org.example.javafxhibernate.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Clase que representa una copia de una película.
 */
@Data
@Entity
@Table(name = "Copia")
public class Copia implements Serializable {

    /**
     * Identificador único de la copia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Estado de la copia.
     */
    private String estado;

    /**
     * Soporte de la copia (por ejemplo, DVD, Blu-ray).
     */
    private String soporte;

    /**
     * Película a la que pertenece la copia.
     */
    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    /**
     * Usuario que posee la copia.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Devuelve una representación en cadena de la copia.
     *
     * @return una cadena que representa la copia.
     */
    @Override
    public String toString() {
        return "Copia{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", soporte='" + soporte + '\'' +
                ", pelicula=" + pelicula +
                ", usuario=" + usuario.getNombre_usuario() +
                '}';
    }
}
