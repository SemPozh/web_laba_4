package com.itmo.backend.utils;

import com.itmo.backend.model.entity.Shot;
import com.itmo.backend.model.entity.User;
import jakarta.ejb.Singleton;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Singleton
public class SessionFactoryManager {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            Configuration configuration
                    = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Shot.class);
            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
