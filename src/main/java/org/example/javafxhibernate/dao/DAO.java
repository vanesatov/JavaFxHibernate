package org.example.javafxhibernate.dao;

import java.util.List;

/**
 * Interfaz DAO para gestionar las operaciones CRUD de una entidad.
 *
 * @param <T> el tipo de la entidad.
 */
public interface DAO<T> {
    /**
     * Obtiene todas las entidades de la base de datos.
     *
     * @return una lista de todas las entidades.
     */
    public List<T> findAll();

    /**
     * Busca una entidad por su ID.
     *
     * @param id el ID de la entidad.
     * @return la entidad encontrada o null si no se encuentra.
     */
    public T findById(Long id);

    /**
     * Guarda una nueva entidad en la base de datos.
     *
     * @param t la entidad a guardar.
     */
    public void save(T t);

    /**
     * Actualiza una entidad existente en la base de datos.
     *
     * @param t la entidad a actualizar.
     */
    public void update(T t);

    /**
     * Elimina una entidad de la base de datos.
     *
     * @param t la entidad a eliminar.
     */
    public void delete(T t);
}
