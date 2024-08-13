package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Film;
import org.hibernate.SessionFactory;

public class FilmDao extends DaoBase<Short, Film> {

    public FilmDao(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }
}
