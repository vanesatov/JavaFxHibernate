package org.example.javafxhibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase utilitaria para gestionar la configuración de Hibernate.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static{
      sessionFactory =  new Configuration()
              .configure()
              .setProperty("hibernate.connection.password", System.getenv("hibernate_password"))
                .setProperty("hibernate.connection.username", System.getenv("hibernate_username"))
              .buildSessionFactory();
    }

    /**
     * Obtiene la instancia de SessionFactory.
     *
     * @return la instancia de SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
