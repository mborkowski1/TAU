package pl.edu.pjatk.tau.cars.service;

import pl.edu.pjatk.tau.cars.domain.Car;
import pl.edu.pjatk.tau.cars.domain.Person;

import java.util.List;

public interface CarManager {

	List<Person> findAllPersons();
	Person findPersonById(Long id);
	Long addPerson(Person person);
	void updatePerson(Person person);
	void deletePerson(Person person);

	void changeCarOwner(Car car, Person person1, Person person2);

	List<Car> findAll();
	List<Car> findCarsByModel(String modelNameFragment);
	Car findById(Long id);
	Long addCar(Car car);
	void updateCar(Car car);
	void deleteCar(Car car);

}
