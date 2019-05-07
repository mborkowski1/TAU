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

}
