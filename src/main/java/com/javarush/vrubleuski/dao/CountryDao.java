package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class CountryDao extends DaoBase<Short, Country> {

    public CountryDao(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }

    public Optional<Country> getByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        Query<Country> countryQuery = session.createQuery("SELECT c FROM Country c WHERE c.name = :country", getClazz())
                .setParameter("country", name).setMaxResults(1);
        return Optional.ofNullable(countryQuery.uniqueResult());
    }
}
