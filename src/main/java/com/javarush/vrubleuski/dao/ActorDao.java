package com.javarush.vrubleuski.dao;

import com.javarush.vrubleuski.entity.Actor;
import org.hibernate.SessionFactory;

public class ActorDao extends DaoBase<Short, Actor> {

    public ActorDao(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
