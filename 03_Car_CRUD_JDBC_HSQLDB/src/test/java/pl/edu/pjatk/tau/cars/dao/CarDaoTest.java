package pl.edu.pjatk.tau.cars.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.edu.pjatk.tau.cars.domain.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CarDaoTest {

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    CarDao carDao;
    List<Car> initialDatabaseState;

    @Before
    public void setup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        initialDatabaseState = new ArrayList<>();
        try {
            connection.createStatement()
            .executeUpdate("CREATE TABLE IF NOT EXISTS " +
                    "Car(id bigint GENERATED BY DEFAULT AS IDENTITY, "
                    + "brand varchar(50) NOT NULL, "
                    + "model varchar(50) NOT NULL, "
                    + "manufacture_year integer NOT NULL, "
                    + "mileage integer NOT NULL)");
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }

        PreparedStatement addCarPreparedStatement = connection.prepareStatement(
                "INSERT INTO Car (brand, model, manufacture_year, mileage) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        List<Car> cars = new ArrayList<>();
        Collections.addAll(cars,
                new Car("Volvo", "XC60", 2018, 20000),
                new Car("Audi", "Q5", 2017, 40000),
                new Car("Jaguar", "XF", 2016, 60000));

        for (Car car : cars) {
            try {
                addCarPreparedStatement.setString(1, car.getBrand());
                addCarPreparedStatement.setString(2, car.getModel());
                addCarPreparedStatement.setInt(3, car.getManufactureYear());
                addCarPreparedStatement.setInt(4, car.getMileage());
                addCarPreparedStatement.executeUpdate();
                ResultSet generatedKeys = addCarPreparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getLong(1));
                }
                initialDatabaseState.add(car);
            }
            catch (SQLException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }

        carDao = new CarDaoJdbcImpl(connection);
    }

    @After
    public void cleanup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.prepareStatement("DELETE FROM Car").executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Test
    public void setConnectionTest() throws SQLException {
        assertNotNull(carDao.getConnection());
    }

    @Test
    public void addingTest() {
        Car car = new Car();
        car.setBrand("BMW");
        car.setModel("X5");
        car.setManufactureYear(2019);
        car.setMileage(1000);
        assertEquals(1, carDao.addCar(car));
        initialDatabaseState.add(car);
        assertThat(carDao.getAllCars(), equalTo(initialDatabaseState));
    }

    @Test
    public void updatingTest() throws SQLException {
        Car car = new Car(initialDatabaseState.get(2));
        car.setMileage(70000);
        initialDatabaseState.set(2, car);
        assertEquals(1, carDao.updateCar(car));
        assertThat(carDao.getAllCars(), equalTo(initialDatabaseState));
    }

    @Test(expected = SQLException.class)
    public void updatingSQLExceptionTest() throws SQLException {
        Car car = new Car("Audi", "A4", 2014, 100000);
        assertEquals(1, carDao.updateCar(car));
    }

    @Test
    public void deletingTest() throws SQLException {
        Car car = initialDatabaseState.get(2);
        initialDatabaseState.remove(car);
        assertEquals(1, carDao.deleteCar(car));
        assertThat(carDao.getAllCars(), equalTo(initialDatabaseState));
    }

    @Test(expected = SQLException.class)
    public void deletingSQLExceptionTest() throws SQLException {
        Car car = new Car("Audi", "A4", 2014, 100000);
        carDao.deleteCar(car);
    }

    @Test
    public void gettingAllTest() {
        List<Car> retrievedCars = carDao.getAllCars();
        assertThat(retrievedCars, equalTo(initialDatabaseState));
    }

    @Test
    public void gettingByIdTest() throws SQLException {
        Car car = initialDatabaseState.get(2);
        assertEquals(car, carDao.getCarById(car.getId()));
    }

    @Test(expected = SQLException.class)
    public void gettingByIdExceptionTest() throws SQLException {
        Car car = carDao.getCarById(-1);
    }

}