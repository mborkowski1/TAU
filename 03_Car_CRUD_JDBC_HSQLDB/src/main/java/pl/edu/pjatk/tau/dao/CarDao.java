package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Car;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CarDao {

    Connection getConnection();
    void setConnection(Connection connection) throws SQLException;
    int addCar(Car car);
    int updateCar(Car car) throws SQLException;
    int deleteCar(Car car) throws SQLException;
    List<Car> getAllCars();
    Car getCarById(long id) throws SQLException;

}
