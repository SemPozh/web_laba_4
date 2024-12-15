package com.itmo.backend.model.dao;

import com.itmo.backend.model.dto.UserDTO;
import com.itmo.backend.model.entity.User;
import com.itmo.backend.utils.SessionFactoryManager;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Stateless
public class UserDAO {

    @EJB
    private SessionFactoryManager sessionFactoryManager;

    public void addUser(User user){
        SessionFactory sessionFactory = sessionFactoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }

    public User getUserByUsername(String username){
        SessionFactory sessionFactory = sessionFactoryManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user = null;
        try {
            user = (User) session.createQuery("from User where username=:username").setParameter("username", username).getSingleResult();
        } catch (Exception e){
            session.close();
            return null;
        }
        session.close();
        return user;
    }
}
