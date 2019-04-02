package pl.edu.pjatk.tau.cars.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.cars.dao.CarInMemoryDao;
import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CarFindingByFiltering{

    private CarInMemoryDao carInMemoryDao;
    private List<Car> filteredCars;
    private String choosedBrand;
    private String choosedModel;
    private int choosedMaxMileage;
    private int choosedMinManufactureYear;

    @Given("User is on page with cars search engine")
    public void user_is_on_page_with_cars_search_engine() {
        carInMemoryDao = new CarInMemoryDao();
        carInMemoryDao.cars = new ArrayList<>();
        Collections.addAll(carInMemoryDao.cars,
                new Car(1L, "Volvo", "XC60", 2018, 20_000),
                new Car(2L, "Audi", "A4", 2017, 40_000),
                new Car(3L, "BMW", "X5", 2016, 60_000),
                new Car(4L, "Audi", "A4", 2018, 30000),
                new Car(5L, "Audi", "A4", 2018, 70000),
                new Car(6L, "Audi", "A4", 2019, 5000),
                new Car(7L, "Audi", "A4", 2019, 10000),
                new Car(8L, "Audi", "A5", 2018, 25000),
                new Car(9L, "Audi", "A4", 2018, 35000));
        filteredCars = carInMemoryDao.getAll();
    }

    @When("User sets the brand filtering to $brand")
    public void user_sets_the_brand_filtering_to(String brand) {
        choosedBrand = brand;
        filteredCars = filteredCars.parallelStream().filter(car -> car.getBrand().equals(choosedBrand)).collect(Collectors.toList());
    }

    @When("User sets the model filtering to $model")
    public void user_sets_the_model_filtering_to(String model) {
        choosedModel = model;
        filteredCars = filteredCars.parallelStream().filter(car -> car.getModel().equals(choosedModel)).collect(Collectors.toList());
    }

    @When("User sets max mileage limit filtering to $mileage")
    public void user_sets_max_mileage_limit_filtering_to(int mileage) {
        choosedMaxMileage = mileage;
        filteredCars = filteredCars.parallelStream().filter(car -> car.getMileage() <= choosedMaxMileage).collect(Collectors.toList());
    }

    @When("User sets min manufacture year filtering to $manufactureYear")
    public void user_sets_min_manufacture_year_filtering_to(int manufactureYear) {
        choosedMinManufactureYear = manufactureYear;
        filteredCars = filteredCars.parallelStream().filter(car -> car.getManufactureYear() >= choosedMinManufactureYear).collect(Collectors.toList());
    }

    @Then(value = "User finds cars that meet the criteria given by him", priority = 1)
    public void user_finds_cars_that_meet_the_criteria_given_by_him() {
        Assert.assertEquals(4, filteredCars.size());
    }

}
