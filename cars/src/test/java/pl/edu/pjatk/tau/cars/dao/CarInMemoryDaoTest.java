package pl.edu.pjatk.tau.cars.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class CarInMemoryDaoTest {

    CarInMemoryDao carDao;

    @Before
    public void setup() {
        carDao = new CarInMemoryDao();
    }

    @Test
    public void createDaoObjectTest() {
        assertNotNull(carDao);
    }

}
