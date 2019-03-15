package pl.edu.pjatk.tau.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CarTest {

    @Test
    public void createObjectTest() {
        Car car = new Car();
        assertNotNull(car);
    }

    @Test
    public void carGettersAndSettersTest() {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Audi");
        car.setModel("A4");
        car.setMileage(10000);
        car.setManufactureYear(2018);
        assertEquals(1L, car.getId().longValue());
        assertEquals("Audi", car.getBrand());
        assertEquals("A4", car.getModel());
        assertEquals(10000, car.getMileage());
        assertEquals(2018, car.getManufactureYear());
    }

}
