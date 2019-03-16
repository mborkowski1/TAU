package pl.edu.pjatk.tau.dao;

import pl.edu.pjatk.tau.domain.Car;

import java.sql.*;

public class CarDaoJdbcImpl implements CarDao {

    private Connection connection;
    private PreparedStatement addCarPreparedStatement;

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

}
