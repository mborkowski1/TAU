package pl.edu.pjatk.tau.cars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.tau.cars.domain.Car;
import pl.edu.pjatk.tau.cars.service.CarManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CarController {

    @Autowired
    CarManager carManager;

    @RequestMapping("/cars")
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        for (Car c : carManager.findAll()) {
            cars.add(c.clone());
        }
        return cars;
    }

    @RequestMapping(value = "/cars", method = RequestMethod.POST)
    public Car addCar(@RequestBody Car car) {
        car.setId(carManager.addCar(car));
        car.setRegistrationDate(LocalDate.now());
        return car;
    }

    @RequestMapping(value = "/car/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Car getCar(@PathVariable Long id) {
        return carManager.findById(id).clone();
    }

    @RequestMapping(value = "/car/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCar(@PathVariable Long id) {
        carManager.deleteCar(carManager.findById(id));
        return "OK";
    }

    @RequestMapping(value = "/car/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCar(@PathVariable Long id, @RequestBody Car car) {
        Car carToUpdate = carManager.findById(id);
        carToUpdate.setMileage(car.getMileage());
        carManager.updateCar(carToUpdate);
        return "OK";
    }

}
