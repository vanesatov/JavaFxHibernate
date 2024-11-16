package org.example.javafxhibernate.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un usuario.
 */
@Data
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario.
     */
    private String nombre_usuario;

    /**
     * Contraseña del usuario.
     */
    private String contraseña;

    /**
     * Indica si el usuario es administrador.
     */
    private Boolean admin;

    /**
     * Lista de copias que posee el usuario.
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Copia> copias = new ArrayList<>(0);

    /**
     * Añade una copia a la lista de copias del usuario.
     *
     * @param c la copia a añadir.
     */
    public void addCopia(Copia c) {
        c.setUsuario(this);
        this.copias.add(c);
    }
}
