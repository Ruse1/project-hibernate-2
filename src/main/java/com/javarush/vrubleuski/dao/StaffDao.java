package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Staff;
import org.hibernate.SessionFactory;

public class StaffDao extends DaoBase<Byte, Staff> {

    public StaffDao(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
