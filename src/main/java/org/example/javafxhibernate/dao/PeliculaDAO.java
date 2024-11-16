package org.example.javafxhibernate.dao;

import org.example.javafxhibernate.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase DAO para gestionar las operaciones CRUD de la entidad Pelicula.
 */
public class PeliculaDAO implements DAO<Pelicula> {

    private SessionFactory sessionFactory;

    /**
     * Constructor de PeliculaDAO.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate.
     */
    public PeliculaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Busca una película por su título.
     *
     * @param titulo el título de la película.
     * @return la película encontrada o null si no se encuentra.
     */
    public Pelicula findPeliculaByTitulo(String titulo) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Pelicula where titulo = :titulo", Pelicula.class)
                    .setParameter("titulo", titulo)
                    .uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Obtiene todas las películas de la base de datos.
     *
     * @return una lista de todas las películas.
     */
    @Override
    public List<Pelicula> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Pelicula", Pelicula.class).list();
        } catch (Exception e) {
            return new ArrayList<Pelicula>(0);
        }
    }

    /**
     * Busca una película por su ID.
     *
     * @param id el ID de la película.
     * @return la película encontrada o null si no se encuentra.
     */
    @Override
    public Pelicula findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Pelicula.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Guarda una nueva película en la base de datos.
     *
     * @param pelicula la película a guardar.
     */
    @Override
    public void save(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.persist(pelicula));
    }

    /**
     * Actualiza una película existente en la base de datos.
     *
     * @param pelicula la película a actualizar.
     */
    @Override
    public void update(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.merge(pelicula));
    }

    /**
     * Elimina una película de la base de datos.
     *
     * @param pelicula la película a eliminar.
     */
    @Override
    public void delete(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.remove(pelicula));
    }
}



