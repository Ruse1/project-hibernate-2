package com.javarush.vrubleuski.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Dao<K extends Serializable, E> {

    E save(E entity);

    void update(E entity);

    void delete(E entity);

    Optional<E> findById(K id);

    List<E> findAll();

    List<E> getItems(int offset, int limit);
}
