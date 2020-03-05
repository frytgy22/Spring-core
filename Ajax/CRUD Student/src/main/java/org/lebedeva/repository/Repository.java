package org.lebedeva.repository;

import java.util.List;

public interface Repository<T, ID> {
    ID save(T data);

    void update(T data);

    boolean delete(ID id);

    List<T> findAll();

    T findById(ID id);
}
