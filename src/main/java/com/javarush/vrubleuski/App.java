package com.javarush.vrubleuski;

import com.javarush.vrubleuski.entity.*;
import org.hibernate.Session;

import java.util.List;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try (Session session = SessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<Film> films = session
                    .createQuery("""
                            FROM Film f
                            WHERE f.id = 2
                            """, Film.class).list();
//            Film film = films.get(0);
//            film.setRating(Rating.NC17);
            System.out.println(films.get(0));
            session.getTransaction().commit();
        }
    }

}
