package com.javarush.vrubleuski.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class DaoBase<K extends Serializable, E> implements Dao<K, E> {

    private final Class<E> clazz;
    private final SessionFactory sessionFactory;

    public DaoBase(Class<E> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public E save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;

    }

    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);

    }

    @Override
    public void delete(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
        session.flush();

    }

    @Override
    public Optional<E> findById(K id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<E> critQuery = criteriaBuilder.createQuery(clazz);
        critQuery.select(critQuery.from(clazz));
        return session.createQuery(critQuery).getResultList();
    }

    @Override
    public List<E> getItems(int offset, int limit) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<E> critQuery = criteriaBuilder.createQuery(clazz);
        critQuery.select(critQuery.from(clazz));
        return session.createQuery(critQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
    }
    public long getGeneralCount() {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = builder.createQuery(Long.class);
        Root<E> from = critQuery.from(clazz);
        critQuery.select(builder.count(from));
        Query<Long> query = session.createQuery(critQuery);
        return query.uniqueResult();
    }

    protected Class<E> getClazz() {
        return clazz;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
