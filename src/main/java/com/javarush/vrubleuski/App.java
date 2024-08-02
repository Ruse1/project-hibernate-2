package com.javarush.vrubleuski;

import com.javarush.vrubleuski.entity.Address;
import com.javarush.vrubleuski.entity.Film;
import org.hibernate.Session;

import java.util.List;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try (Session session = MySessionFactory.getSessionFactory().openSession()) {
            List<Address> films = session
                    .createQuery("""
                            FROM Address f
                            """, Address.class).list();
            System.out.println(films);
        }
    }

}
