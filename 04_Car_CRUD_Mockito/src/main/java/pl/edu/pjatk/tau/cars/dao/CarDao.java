package pl.edu.pjatk.tau.cars.dao;

import pl.edu.pjatk.tau.cars.domain.Car;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CarDao {

    Connection getConnection();
    void setConnection(Connection connection) throws SQLException;
    int addCar(Car car) throws SQLException;
    List<Car> getAllCars();
    Car getCar(long id) throws SQLException;

}
