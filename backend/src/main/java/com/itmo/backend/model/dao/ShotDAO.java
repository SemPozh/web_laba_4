package com.itmo.backend.model.dao;

import com.itmo.backend.model.entity.Shot;
import com.itmo.backend.model.entity.User;
import com.itmo.backend.utils.SessionFactoryManager;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class ShotDAO {
    @EJB
    private SessionFactoryManager sessionFactoryManager;
    public List<Shot> getUserShots(User user){
        SessionFactory sessionFactory = sessionFactoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<Shot> shots = session.createQuery("from Shot where user.username=:username").setParameter("username", user.getUsername()).list();
        session.close();
        return shots;
    }

    public void addShot(Shot shot){
        SessionFactory sessionFactory = sessionFactoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(shot);
        transaction.commit();
        session.close();
    }

    public void clearShots(User user){
        SessionFactory sessionFactory = sessionFactoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from Shot x where x.user = :user")
                .setParameter("user", user)
                .executeUpdate();
        transaction.commit();
        session.close();
    }
}
