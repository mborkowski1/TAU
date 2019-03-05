package pl.edu.pjatk.tau.cars.dao;

import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.List;

public class CarInMemoryDao implements Dao<Car> {

    protected List<Car> cars;

    @Override
    public void save(Car o) {
        cars.add(o);
    }

}
