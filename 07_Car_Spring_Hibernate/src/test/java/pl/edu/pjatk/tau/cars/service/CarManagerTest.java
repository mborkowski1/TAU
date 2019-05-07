package pl.edu.pjatk.tau.cars.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjatk.tau.cars.domain.Car;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
@Commit
@Transactional(transactionManager = "txManager")
public class CarManagerTest {

    @Autowired
    CarManager carManager;

    List<Long> carIds;

    private Car addCarHelper(String brand, String model, Integer manufactureYear, Integer mileage) {
        Long carId;
        Car car;
        car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setManufactureYear(manufactureYear);
        car.setMileage(mileage);
        car.setRegistrationDate(LocalDate.now());
        carIds.add(carId = carManager.addCar(car));
        assertNotNull(carId);
        return car;
    }

    @Before
    public void setup() {
        carIds = new LinkedList<>();
        addCarHelper("Volvo", "XC60", 2018, 40000);
        addCarHelper("BMW", "X5", 2017, 60000);
        addCarHelper("Audi", "A4", 2016, 75000);
    }

    @Test
    public void addCarTest() {
        Car car = addCarHelper("Lexus", "NX", 2019, 5000);
        assertTrue(carIds.size() > 0);
        assertEquals(car.getId(), carIds.get(3));
    }

    @Test
    public void findAllTest() {
        List<Long> foundIds = new LinkedList<>();
        for (Car car : carManager.findAll()) {
            if (carIds.contains(car.getId())) foundIds.add(car.getId());
        }
        assertEquals(carIds.size(), foundIds.size());
    }

    @Test
    public void findCarsByModel() {
        List<Car> cars = carManager.findCarsByModel("A");
        assertEquals("A4", cars.get(0).getModel());
    }

    @Test
    public void findByIdTest() {
        Car car = carManager.findById(carIds.get(0));
        assertNotNull(car);
        assertEquals(car.getId(), carIds.get(0));
    }

}
