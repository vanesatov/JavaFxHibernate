package org.example.javafxhibernate.dao;

import org.example.javafxhibernate.models.Pelicula;
import org.example.javafxhibernate.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de la entidad Usuario.
 */
public class UsuarioDAO implements DAO<Usuario> {

    private SessionFactory sessionFactory;

    /**
     * Constructor de UsuarioDAO.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate.
     */
    public UsuarioDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return una lista de todos los usuarios.
     */
    @Override
    public List<Usuario> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Usuario ", Usuario.class).list();
        } catch (Exception e) {
            return new ArrayList<Usuario>(0);
        }
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id el ID del usuario.
     * @return el usuario encontrado o null si no se encuentra.
     */
    @Override
    public Usuario findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Usuario.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario el usuario a guardar.
     */
    @Override
    public void save(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.persist(usuario));
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param usuario el usuario a actualizar.
     */
    @Override
    public void update(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.merge(usuario));
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param usuario el usuario a eliminar.
     */
    @Override
    public void delete(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.remove(usuario));
    }

    /**
     * Valida un usuario por su nombre de usuario y contraseña.
     *
     * @param nombre_usuario el nombre de usuario.
     * @param contraseña la contraseña del usuario.
     * @return el usuario validado o null si no se encuentra.
     */
    public Usuario validarUsuario(String nombre_usuario, String contraseña) {
        try (Session session = sessionFactory.openSession()) {
            Query<Usuario> q = session.createQuery(
                    "select u from Usuario u where u.nombre_usuario = :nombre_usuario and u.contraseña = :contraseña",
                    Usuario.class
            );
            q.setParameter("nombre_usuario", nombre_usuario);
            q.setParameter("contraseña", contraseña);
            return q.list().getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}