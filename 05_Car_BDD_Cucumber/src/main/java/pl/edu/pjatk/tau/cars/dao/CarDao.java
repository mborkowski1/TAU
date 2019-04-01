package pl.edu.pjatk.tau.cars.dao;

import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarDao {

    List<Car> getAll();
    Optional<Car> getById(Long id);
    void save(Car o);
    void update(Car o);
    void delete(Car o);

}
