package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Category;
import org.hibernate.SessionFactory;

public class CategoryDao extends DaoBase<Byte, Category> {

    public CategoryDao(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
}
