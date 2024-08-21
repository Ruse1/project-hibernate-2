package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.Year;
import java.util.Optional;

public class FilmDao extends DaoBase<Short, Film> {

    public FilmDao(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }
    public Optional<Film> getByNameYearLanguage(String name, Year year, Byte language) {
        Session session = getSessionFactory().getCurrentSession();
        Query<Film> filmQuery = session.createQuery(
                        """
                                SELECT f 
                                FROM Film f 
                                WHERE f.title = :film AND f.releaseYear = :year 
                                                      AND f.language.id = :language""", getClazz())
                .setParameter("film", name)
                .setParameter("year", year)
                .setParameter("language", language)
                .setMaxResults(1);
        return Optional.ofNullable(filmQuery.uniqueResult());
    }
}
