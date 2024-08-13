package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Customer;
import org.hibernate.SessionFactory;

public class CustomerDao extends DaoBase<Short, Customer> {

    public CustomerDao(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }
}
