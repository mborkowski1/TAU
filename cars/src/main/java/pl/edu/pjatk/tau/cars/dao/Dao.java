package pl.edu.pjatk.tau.cars.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> getAll();
    Optional<T> getById(Long id);
    void save(T o);

}
