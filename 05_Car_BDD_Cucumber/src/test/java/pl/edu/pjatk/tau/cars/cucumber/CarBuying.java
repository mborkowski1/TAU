package pl.edu.pjatk.tau.cars.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.cars.dao.CarInMemoryDao;
import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.ArrayList;
import java.util.Collections;

public class CarBuying {

    private CarInMemoryDao carInMemoryDao;
    private String choosedBrand;
    private String choosedModel;

    @Given("^Customer chooses a car$")
    public void customer_chooses_a_car() {
        carInMemoryDao = new CarInMemoryDao();
        carInMemoryDao.cars = new ArrayList<>();
        Collections.addAll(carInMemoryDao.cars,
                new Car(1L, "Volvo", "XC60", 2018, 20_000),
                new Car(2L, "Audi", "A4", 2017, 40_000),
                new Car(3L, "BMW", "X5", 2016, 60_000));
    }

    @When("^Customer chose brand \"([^\"]*)\"$")
    public void customer_choose_brand(String brand) {
        choosedBrand = brand;
    }

    @And("^Customer chose model \"([^\"]*)\"$")
    public void customer_chose_model(String model) {
        choosedModel = model;
    }

    @Then("^Car has been sold$")
    public void car_has_been_sold() {
        Car choosedCar = carInMemoryDao.getAll().stream().filter(car -> car.getBrand().equals(choosedBrand) && car.getModel().equals(choosedModel)).findFirst().get();
        Assert.assertEquals(choosedCar, carInMemoryDao.getById(choosedCar.getId()).get());
        carInMemoryDao.delete(choosedCar);
        Assert.assertEquals(2, carInMemoryDao.cars.size());
    }

}
