package pl.edu.pjatk.tau.cars.dao;

import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.List;
import java.util.Optional;

public class CarInMemoryDao implements Dao<Car> {

    protected List<Car> cars;

    @Override
    public List<Car> getAll() {
        return cars;
    }

    @Override
    public Optional<Car> getById(Long id) throws IllegalArgumentException {
        if (cars.stream().noneMatch(car -> car.getId().equals(id)))
            throw new IllegalArgumentException("Car with id " + id + " not exists");
        return cars.stream().filter(car -> car.getId().equals(id)).findFirst();
    }

    @Override
    public void save(Car o) throws IllegalArgumentException {
        if (cars.stream().anyMatch(car -> car.getId().equals(o.getId())))
            throw new IllegalArgumentException("Car has already exists");
        cars.add(o);
    }

}
