package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Payment;
import org.hibernate.SessionFactory;

public class PaymentDao extends DaoBase<Short, Payment> {

    public PaymentDao(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
