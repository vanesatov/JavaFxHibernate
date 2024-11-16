package org.example.javafxhibernate.models;

import jakarta.persistence.*;
import lombok.Data;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una película.
 */
@Data
@Entity
@Table(name = "Pelicula")
public class Pelicula implements Serializable {

    /**
     * Identificador único de la película.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título de la película.
     */
    private String titulo;

    /**
     * Género de la película.
     */
    private String genero;

    /**
     * Año de lanzamiento de la película.
     */
    private Integer año;

    /**
     * Descripción de la película.
     */
    private String descripcion;

    /**
     * Director de la película.
     */
    private String director;

    /**
     * Lista de copias de la película.
     */
    @OneToMany(mappedBy = "pelicula", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Copia> copias = new ArrayList<>(0);

    /**
     * Añade una copia a la lista de copias de la película.
     *
     * @param c la copia a añadir.
     */
    public void addCopia(Copia c) {
        c.setPelicula(this);
        this.copias.add(c);
    }
}
