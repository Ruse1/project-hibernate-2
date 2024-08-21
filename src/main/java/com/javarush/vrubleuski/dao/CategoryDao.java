package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Category;
import com.javarush.vrubleuski.entity.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class CategoryDao extends DaoBase<Byte, Category> {

    public CategoryDao(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
    public Optional<Category> getByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        Query<Category> categoryQuery = session.createQuery("SELECT c FROM Category c WHERE c.name = :category", getClazz())
                .setParameter("category", name).setMaxResults(1);
        return Optional.ofNullable(categoryQuery.uniqueResult());
    }
}
