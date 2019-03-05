package pl.edu.pjatk.tau.cars.dao;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();
    void save(T o);

}
