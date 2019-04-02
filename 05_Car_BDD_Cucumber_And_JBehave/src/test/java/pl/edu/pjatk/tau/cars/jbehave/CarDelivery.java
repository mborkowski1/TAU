package pl.edu.pjatk.tau.cars.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.Story;
import org.junit.Assert;
import pl.edu.pjatk.tau.cars.dao.CarInMemoryDao;
import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.ArrayList;

public class CarDelivery extends Story {

    private CarInMemoryDao carInMemoryDao;
    private int previousQuantityOfCars;

    @Given("Delivery of 3 cars")
    public void delivery_of_3_cars() {
        carInMemoryDao = new CarInMemoryDao();
        carInMemoryDao.cars = new ArrayList<>();
    }

    @When("Cars has been delivered")
    public void cars_has_been_delivered() {
        previousQuantityOfCars = carInMemoryDao.cars.size();
        carInMemoryDao.save(new Car(1L, "Volvo", "XC60", 2018, 20_000));
        carInMemoryDao.save(new Car(2L, "Audi", "A4", 2017, 40_000));
        carInMemoryDao.save(new Car(3L, "BMW", "X5", 2016, 60_000));
    }

    @Then("Quantity of cars has been increased by $quantityOfAddedCars")
    public void quantity_of_cars_has_been_increased_by(int quantityOfAddedCars) {
        Assert.assertEquals(previousQuantityOfCars + quantityOfAddedCars, carInMemoryDao.cars.size());
    }

}
