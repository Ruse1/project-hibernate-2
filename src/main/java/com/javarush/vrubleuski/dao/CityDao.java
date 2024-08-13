package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.City;
import org.hibernate.SessionFactory;

public class CityDao extends DaoBase<Short, City> {

    public CityDao(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }
}
