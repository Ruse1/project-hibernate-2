package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Inventory;
import org.hibernate.SessionFactory;

public class InventoryDao extends DaoBase<Integer, Inventory> {

    public InventoryDao(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
