package com.javarush.vrubleuski;

import com.javarush.vrubleuski.converter.RatingConverter;
import com.javarush.vrubleuski.entity.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class SessionFactory {
    private static SessionFactory instance;
    private final org.hibernate.SessionFactory sessionFactory;

    private SessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.JAKARTA_JDBC_DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.JAKARTA_JDBC_USER, "root");
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, "mysql");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HIGHLIGHT_SQL, "true");

        sessionFactory = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Customer.class)
                .addAttributeConverter(RatingConverter.class)
                .buildSessionFactory();
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance.sessionFactory;
    }
}