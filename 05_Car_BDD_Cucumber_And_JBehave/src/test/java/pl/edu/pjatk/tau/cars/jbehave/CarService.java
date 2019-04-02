package pl.edu.pjatk.tau.cars.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.cars.dao.CarInMemoryDao;
import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.ArrayList;
import java.util.Collections;

public class CarService {

    private CarInMemoryDao carInMemoryDao;
    private Car carForService;

    @Given("Car $brand $model requires service")
    public void car_requires_service(String brand, String model) {
        carInMemoryDao = new CarInMemoryDao();
        carInMemoryDao.cars = new ArrayList<>();
        Collections.addAll(carInMemoryDao.cars,
                new Car(1L, "Volvo", "XC60", 2018, 20_000),
                new Car(2L, "Audi", "A4", 2017, 40_000),
                new Car(3L, "BMW", "X5", 2016, 60_000));
        carForService = new Car(carInMemoryDao.cars.stream().filter(car -> car.getBrand().equals(brand) && car.getModel().equals(model)).findFirst().get());
    }

    @When("Car mileage should be updated by $mileage")
    public void car_mileage_should_be_updated_by(int mileage) {
        carForService.setMileage(carForService.getMileage() + mileage);
        carInMemoryDao.update(carForService);
    }

    @Then(value = "Service of the car has been made and its mileage has been updated", priority = 1)
    public void service_of_the_car_has_been_made_and_its_mileage_has_been_updated() {
        Assert.assertEquals(30000, carInMemoryDao.cars.get(0).getMileage());
    }

}
