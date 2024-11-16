package org.example.javafxhibernate.dao;

import org.example.javafxhibernate.models.Copia;
import org.example.javafxhibernate.models.Pelicula;
import org.example.javafxhibernate.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Clase DAO para gestionar las operaciones CRUD de la entidad Copia.
 */
public class CopiaDAO implements DAO<Copia> {

    private SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el DAO con una SessionFactory.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate.
     */
    public CopiaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Obtiene todas las copias de la base de datos.
     *
     * @return una lista de todas las copias.
     */
    @Override
    public List<Copia> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia", Copia.class).list();
        } catch (Exception e) {
            return new ArrayList<Copia>(0);
        }
    }

    /**
     * Busca una copia por su ID.
     *
     * @param id el ID de la copia.
     * @return la copia encontrada o null si no se encuentra.
     */
    @Override
    public Copia findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Copia.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Guarda una nueva copia en la base de datos.
     *
     * @param copia la copia a guardar.
     */
    @Override
    public void save(Copia copia) {
        sessionFactory.inTransaction(session -> session.persist(copia));
    }

    /**
     * Actualiza una copia existente en la base de datos.
     *
     * @param copia la copia a actualizar.
     */
    @Override
    public void update(Copia copia) {
        sessionFactory.inTransaction(session -> session.merge(copia));
    }

    /**
     * Elimina una copia de la base de datos.
     *
     * @param copia la copia a eliminar.
     */
    @Override
    public void delete(Copia copia) {
        sessionFactory.inTransaction(session -> session.remove(copia));
    }

    /**
     * Busca copias por usuario.
     *
     * @param usuario el usuario cuyas copias se buscan.
     * @return una lista de copias del usuario.
     */
    public List<Copia> findByUser(Usuario usuario) {
        try (Session session = sessionFactory.openSession()) {
            Query<Copia> q = session.createQuery(
                    "select c from Copia c where c.usuario = :usuario", Copia.class
            );
            q.setParameter("usuario", usuario);
            return q.list();
        }
    }

    /**
     * Obtiene una lista de estados distintos de las copias.
     *
     * @return una lista de estados.
     */
    public List<String> getEstados() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct c.estado from Copia c order by estado", String.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene una lista de soportes distintos de las copias.
     *
     * @return una lista de soportes.
     */
    public List<String> getSoportes() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct c.soporte from Copia c order by soporte", String.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene una lista de títulos de películas.
     *
     * @return una lista de títulos.
     */
    public List<String> getTitulos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select titulo from Pelicula", String.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
