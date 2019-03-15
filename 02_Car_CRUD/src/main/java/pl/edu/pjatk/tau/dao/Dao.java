package pl.edu.pjatk.tau.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> getAll();
    Optional<T> getById(Long id);
    void save(T o);
    void update(T o);
    void delete(T o);

}
