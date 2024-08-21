package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Actor;
import com.javarush.vrubleuski.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class ActorDao extends DaoBase<Short, Actor> {

    public ActorDao(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
    public Optional<Actor> getByFirstNameLastName(String firstName, String lastName) {
        Session session = getSessionFactory().getCurrentSession();
        Query<Actor> categoryQuery = session.createQuery(
                "SELECT a FROM Actor a WHERE a.firstName = :firstname AND a.lastName = :lastname", getClazz())
                .setParameter("firstname", firstName)
                .setParameter("lastname", lastName)
                .setMaxResults(1);
        return Optional.ofNullable(categoryQuery.uniqueResult());
    }
}
