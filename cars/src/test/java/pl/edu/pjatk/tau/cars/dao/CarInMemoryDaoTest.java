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

    @Test(expected = IllegalArgumentException.class)
    public void savingExistsTest() {
        Car car = new Car(1L, "Audi", "A6", 2018, 15000);
        carDao.save(car);
    }

    @Test
    public void updatingTest() {
        Car car = carDao.getById(1L).get();
        car.setMileage(20000);
        carDao.update(car);
        assertEquals(20000, carDao.getById(1L).get().getMileage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatingExistsTest() {
        Car car = new Car(3L, "Audi", "A6", 2019, 1000);
        carDao.update(car);
    }

    @Test
    public void deletingTest() {
        Car car = carDao.getById(1L).get();
        carDao.delete(car);
        assertEquals(1, carDao.cars.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deletingExistsTest() {
        Car car = carDao.getById(3L).get();
        carDao.delete(car);
        assertEquals(1, carDao.cars.size());
    }

    @Test
    public void gettingAllCarsTest() {
        assertArrayEquals(carDao.cars.toArray(), carDao.getAll().toArray());
        assertEquals(carDao.cars.size(), carDao.getAll().size());
    }

    @Test
    public void gettingByIdTest() {
        Car car = new Car(3L, "Jaguar", "XF", 2019, 5000);
        carDao.save(car);
        assertEquals(car, carDao.getById(3L).get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void gettingExistsTest() {
        carDao.getById(3L);
    }

}
