package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Store;
import org.hibernate.SessionFactory;

public class StoreDao extends DaoBase<Byte, Store> {

    public StoreDao(SessionFactory sessionFactory) {
        super(Store.class, sessionFactory);
    }
}
