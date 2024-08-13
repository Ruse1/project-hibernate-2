package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Country;
import org.hibernate.SessionFactory;

public class CountryDao extends DaoBase<Short, Country> {

    public CountryDao(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
