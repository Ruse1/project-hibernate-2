package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.FilmText;
import org.hibernate.SessionFactory;

public class FilmTextDao extends DaoBase<Short, FilmText> {

    public FilmTextDao(SessionFactory sessionFactory) {
        super(FilmText.class, sessionFactory);
    }
}
