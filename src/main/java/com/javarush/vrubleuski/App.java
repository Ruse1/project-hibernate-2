package com.javarush.vrubleuski;

import com.javarush.vrubleuski.entity.*;
import org.hibernate.Session;

import java.util.List;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try (Session session = MySessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();

            try {
                Country country = new Country();
                country.setName("Belarus");

                session.persist(country);

                City city = new City();
                city.setName("Minsk");
                city.setCountry(country);

                session.persist(city);

                Address address = new Address();
                address.setAddress("Beleckogo18");
                address.setDistrict("Mockovski");
                address.setCity(city);
                address.setPostal_code("222222");
                address.setPhone("6628762");

                session.persist(address);

                Customer customer = new Customer();
                customer.setFirstName("Ruslan");
                customer.setLastName("Vrubleuski");
                customer.setEmail("6628762@mail.ru");
                customer.setIsActive(true);
                customer.setAddress(address);

                session.persist(customer);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

}
