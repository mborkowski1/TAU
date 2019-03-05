package pl.edu.pjatk.tau.cars.domain;

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
        car.setModel("A4");
        assertEquals(1, car.getId());
        assertEquals("A4", car.getModel());
    }

}
