package pl.edu.pjatk.tau.cars.service;

import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.List;

public interface CarManager {

	List<Car> findAll();
	Long addCar(Car car);

}
