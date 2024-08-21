package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class RentalDao extends DaoBase<Integer, Rental> {

    public RentalDao(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Optional<Rental> getUnreturnedFilm() {
        Session session = getSessionFactory().getCurrentSession();
        Query<Rental> rentalQuery = session.createQuery(
                "SELECT r FROM Rental r WHERE r.returnDate IS NULL",
                Rental.class
        ).setMaxResults(1);
        return Optional.ofNullable(rentalQuery.uniqueResult());
    }
}
