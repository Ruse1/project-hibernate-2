package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Address;
import org.hibernate.SessionFactory;

public class AddressDao extends DaoBase<Short, Address> {

    public AddressDao(SessionFactory sessionFactory) {
        super(Address.class, sessionFactory);
    }
}
