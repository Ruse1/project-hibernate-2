package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Language;
import org.hibernate.SessionFactory;

public class LanguageDao extends DaoBase<Byte, Language> {

    public LanguageDao(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
}
