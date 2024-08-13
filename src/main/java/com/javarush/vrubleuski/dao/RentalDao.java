package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Rental;
import org.hibernate.SessionFactory;

public class RentalDao extends DaoBase<Integer, Rental> {

    public RentalDao(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }
}
