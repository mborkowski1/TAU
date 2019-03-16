package pl.edu.pjatk.tau.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface CarDao {

    Connection getConnection();
    void setConnection(Connection connection) throws SQLException;

}
