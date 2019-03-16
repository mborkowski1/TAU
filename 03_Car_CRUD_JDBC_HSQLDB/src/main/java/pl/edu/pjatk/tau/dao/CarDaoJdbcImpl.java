package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoJdbcImpl implements CarDao {

    private Connection connection;
    private PreparedStatement addCarPreparedStatement;
    private PreparedStatement getAllCarsPreparedStatement;
    private PreparedStatement getCarByIdPreparedStatement;
    private PreparedStatement updateCarPreparedStatement;
    private PreparedStatement deleteCarPreparedStatement;

    public CarDaoJdbcImpl() { }

    public CarDaoJdbcImpl(Connection connection) throws SQLException {
        setConnection(connection);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addCarPreparedStatement = connection.prepareStatement(
                "INSERT INTO Car (brand, model, manufacture_year, mileage) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        getAllCarsPreparedStatement = connection.prepareStatement("SELECT id, brand, model, manufacture_year, mileage FROM Car ORDER By id");
        getCarByIdPreparedStatement = connection.prepareStatement("SELECT id, brand, model, manufacture_year, mileage FROM Car WHERE id = ?");
        updateCarPreparedStatement = connection.prepareStatement("UPDATE Car SET mileage = ? WHERE id = ?");
        deleteCarPreparedStatement = connection.prepareStatement("DELETE FROM Car WHERE id =  ?");
    }

    @Override
    public int addCar(Car car) {
        int count;
        try {
            addCarPreparedStatement.setString(1, car.getBrand());
            addCarPreparedStatement.setString(2, car.getModel());
            addCarPreparedStatement.setInt(3, car.getManufactureYear());
            addCarPreparedStatement.setInt(4, car.getMileage());
            count = addCarPreparedStatement.executeUpdate();
            ResultSet generatedKeys = addCarPreparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getLong(1));
            }
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return count;
    }

    @Override
    public int updateCar(Car car) throws SQLException {
        int count;
        try {
            updateCarPreparedStatement.setInt(1, car.getMileage());
            if (car.getId() != null) {
                updateCarPreparedStatement.setLong(2, car.getId());
            }
            else {
                updateCarPreparedStatement.setLong(2, -1);
            }
            count = updateCarPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        if (count == 1)
            return count;
        else
            throw new SQLException("Car not found");
    }

    @Override
    public int deleteCar(Car car) {
        try {
            deleteCarPreparedStatement.setLong(1, car.getId());
            return deleteCarPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try {
            ResultSet result = getAllCarsPreparedStatement.executeQuery();
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
    public Car getCarById(long id) throws SQLException {
        try {
            getCarByIdPreparedStatement.setLong(1, id);
            ResultSet result = getCarByIdPreparedStatement.executeQuery();
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
