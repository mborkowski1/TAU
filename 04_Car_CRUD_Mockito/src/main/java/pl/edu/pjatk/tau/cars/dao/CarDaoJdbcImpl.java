package pl.edu.pjatk.tau.cars.dao;

import pl.edu.pjatk.tau.cars.domain.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoJdbcImpl implements CarDao {

    public PreparedStatement insertPreparedStatement;
    public PreparedStatement getAllPreparedStatement;
    public PreparedStatement getCarPreparedStatement;
    Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        insertPreparedStatement = connection.prepareStatement("INSERT INTO Car (brand, model, manufacture_year, mileage) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        getAllPreparedStatement = connection.prepareStatement("SELECT id, brand, model, manufacture_year, mileage FROM Car ORDER BY id");
        getCarPreparedStatement = connection.prepareStatement("SELECT id, brand, model, manufacture_year, mileage FROM Car WHERE id = ?");
    }

    @Override
    public int addCar(Car car) throws SQLException {
        int count;
        try {
            insertPreparedStatement.setString(1, car.getBrand());
            insertPreparedStatement.setString(2, car.getModel());
            insertPreparedStatement.setInt(3, car.getManufactureYear());
            insertPreparedStatement.setInt(4, car.getMileage());
            count = insertPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return count;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try {
            ResultSet result = getAllPreparedStatement.executeQuery();
            while (result.next()) {
                Car car = new Car();
                car.setId(result.getLong("id"));
                car.setBrand(result.getString("brand"));
                car.setModel(result.getString("model"));
                car.setManufactureYear(result.getInt("manufacture_year"));
                car.setMileage(result.getInt("mileage"));
                cars.add(car);
            }
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return cars;
    }

    @Override
    public Car getCar(long id) throws SQLException {
        try {
            getCarPreparedStatement.setLong(1, id);
            ResultSet result = getCarPreparedStatement.executeQuery();
            if (result.next()) {
                Car car = new Car();
                car.setId(result.getLong("id"));
                car.setBrand(result.getString("brand"));
                car.setModel(result.getString("model"));
                car.setManufactureYear(result.getInt("manufacture_year"));
                car.setMileage(result.getInt("mileage"));
                return car;
            }
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        throw new SQLException("Car with id " + id + " does not exists");
    }

}
