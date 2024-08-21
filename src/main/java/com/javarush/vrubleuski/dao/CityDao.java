package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class CityDao extends DaoBase<Short, City> {

    public CityDao(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public Optional<City> getByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        Query<City> countryQuery = session.createQuery("SELECT c FROM City c WHERE c.name = :city", getClazz())
                .setParameter("city", name).setMaxResults(1);
        return Optional.ofNullable(countryQuery.uniqueResult());
    }
}
