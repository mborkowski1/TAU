package pl.edu.pjatk.tau.cars.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.edu.pjatk.tau.cars.domain.Car;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CarInMemoryDaoTest {

    CarInMemoryDao carDao;

    @Before
    public void setup() {
        carDao = new CarInMemoryDao();
        carDao.cars = new ArrayList<>();
        carDao.cars.add(new Car(1L, "Audi", "A4", 2018, 10000));
        carDao.cars.add(new Car(2L, "BMW", "X6", 2016, 60000));
    }

    @Test
    public void createDaoObjectTest() {
        assertNotNull(carDao);
    }

    @Test
    public void savingTest() {
        Car car = new Car(3L, "Volvo", "XC60", 2017, 40000);
        carDao.save(car);
        assertEquals(3L, carDao.cars.get(2).getId().longValue());
        assertEquals(3, carDao.cars.size());
    }

    @Test
    public void gettingAllCarsTest() {
        assertArrayEquals(carDao.cars.toArray(), carDao.getAll().toArray());
        assertEquals(carDao.cars.size(), carDao.getAll().size());
    }

}
