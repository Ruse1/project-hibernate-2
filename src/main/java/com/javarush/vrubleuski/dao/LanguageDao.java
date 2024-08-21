package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Language;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class LanguageDao extends DaoBase<Byte, Language> {

    public LanguageDao(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
    public Optional<Language> getByName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        Query<Language> languageQuery = session.createQuery("SELECT l FROM Language l WHERE l.name = :language", getClazz())
                .setParameter("language", name).setMaxResults(1);
        return Optional.ofNullable(languageQuery.uniqueResult());
    }
}
