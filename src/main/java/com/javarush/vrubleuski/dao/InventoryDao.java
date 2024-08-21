package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Inventory;
import com.javarush.vrubleuski.entity.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class InventoryDao extends DaoBase<Integer, Inventory> {

    public InventoryDao(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }

    public Optional<Inventory> getAvailableInventory() {
        Session session = getSessionFactory().getCurrentSession();
        Query<Inventory> inventoryQuery = session.createQuery("""
                SELECT i
                FROM Inventory i
                WHERE i.id NOT IN (
                            SELECT DISTINCT(r.inventory.id)
                            FROM Rental r
                            WHERE r.returnDate IS NULL OR r.returnDate > NOW())
                """, Inventory.class).setMaxResults(1);
        return Optional.ofNullable(inventoryQuery.uniqueResult());
    }
}
