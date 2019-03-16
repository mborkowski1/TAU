package pl.edu.pjatk.tau.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class CarDaoTest {

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    CarDao carDao;

    @Before
    public void setup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.createStatement()
            .executeUpdate("CREATE TABLE " +
                    "Car(id bigint GENERATED BY DEFAULT AS IDENTITY, "
                    + "brand varchar(50) NOT NULL, "
                    + "model varchar(50) NOT NULL, "
                    + "manufacture_year integer(4) NOT NULL, "
                    + "mileage integer NOT NULL)");
        }
        catch (SQLException e) { }
        carDao = new CarDaoJdbcImpl(connection);
    }

    @After
    public void cleanup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.prepareStatement("DELETE FROM Car").executeUpdate();
        }
        catch (SQLException e) { }
    }

    @Test
    public void setConnectionTest() throws SQLException {
        assertNotNull(carDao.getConnection());
    }

}