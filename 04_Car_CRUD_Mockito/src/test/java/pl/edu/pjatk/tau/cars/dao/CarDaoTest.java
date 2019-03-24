package pl.edu.pjatk.tau.cars.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.edu.pjatk.tau.cars.domain.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CarDaoTest {

    CarDaoJdbcImpl dao;
    static List<Car> initialDatabaseState;

    @Mock
    Connection connection;
    @Mock
    PreparedStatement selectAllStatementMock;
    @Mock
    PreparedStatement selectByIdStatementMock;
    @Mock
    PreparedStatement insertStatementMock;
    @Mock
    PreparedStatement updateStatementMock;

    abstract class AbstractResultSet implements ResultSet {
        int i;

        @Override
        public int getInt(String columnLabel) throws SQLException {
            if (columnLabel.equals("manufacture_year"))
                return initialDatabaseState.get(i-1).getManufactureYear();
            else if (columnLabel.equals("mileage"))
                return initialDatabaseState.get(i-1).getMileage();
            else
                throw new IllegalArgumentException(columnLabel + " column does not exists or does not store int value");
        }

        @Override
        public long getLong(String columnLabel) throws SQLException {
            if (columnLabel.equals("id"))
                return initialDatabaseState.get(i-1).getId();
            else
                throw new IllegalArgumentException(columnLabel + " column does not exists or does not store long value");
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            if (columnLabel.equals("brand"))
                return initialDatabaseState.get(i-1).getBrand();
            else if (columnLabel.equals("model"))
                return initialDatabaseState.get(i-1).getModel();
            else
                throw new IllegalArgumentException(columnLabel + " column does not exists or does not store string value");
        }

        @Override
        public boolean next() throws SQLException {
            i++;
            return i <= initialDatabaseState.size();
        }
    }

    @Before
    public void setup() throws SQLException {
        initialDatabaseState = new ArrayList<>();
        Collections.addAll(initialDatabaseState,
                new Car(1L, "Volvo", "XC60", 2018, 20000),
                new Car(2L, "Audi", "Q5", 2017, 40000),
                new Car(3L, "Jaguar", "XF", 2016, 60000));
        when(connection.prepareStatement("SELECT id, brand, model, manufacture_year, mileage FROM Car ORDER BY id")).thenReturn(selectAllStatementMock);
        when(connection.prepareStatement("SELECT id, brand, model, manufacture_year, mileage FROM Car WHERE id = ?")).thenReturn(selectByIdStatementMock);
        when(connection.prepareStatement("INSERT INTO Car (brand, model, manufacture_year, mileage) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
        when(connection.prepareStatement("UPDATE Car SET mileage = ? WHERE id = ?")).thenReturn(updateStatementMock);
        dao = new CarDaoJdbcImpl();
        dao.setConnection(connection);
    }

    @Test
    public void setConnectionTest() throws SQLException {
        assertNotNull(dao.getConnection());
        assertEquals(dao.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesTest() throws SQLException {
        assertNotNull(dao.getAllPreparedStatement);
        verify(connection).prepareStatement("SELECT id, brand, model, manufacture_year, mileage FROM Car ORDER BY id");
    }

    @Test
    public void addingInOrderTest() throws SQLException {
        InOrder inOrder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Car car = new Car();
        car.setBrand("BMW");
        car.setModel("X6");
        car.setManufactureYear(2019);
        car.setMileage(1000);
        dao.addCar(car);

        inOrder.verify(insertStatementMock, times(1)).setString(1, "BMW");
        inOrder.verify(insertStatementMock, times(1)).setString(2, "X6");
        inOrder.verify(insertStatementMock, times(1)).setInt(3, 2019);
        inOrder.verify(insertStatementMock, times(1)).setInt(4, 1000);
        inOrder.verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void updatingTest() throws SQLException {
        when(updateStatementMock.executeUpdate()).thenReturn(1);

        Car car = initialDatabaseState.get(0);
        car.setMileage(300000);
        dao.updateCar(car);

        verify(updateStatementMock, times(1)).setInt(1, 300000);
        verify(updateStatementMock, times(1)).setLong(2, 1L);
        verify(updateStatementMock).executeUpdate();
    }

    @Test
    public void gettingAllTest() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("brand")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(mockedResultSet.getInt("manufacture_year")).thenCallRealMethod();
        when(mockedResultSet.getInt("mileage")).thenCallRealMethod();
        when(selectAllStatementMock.executeQuery()).thenReturn(mockedResultSet);

        List<Car> retrievedCars = dao.getAllCars();
        assertThat(retrievedCars, equalTo(initialDatabaseState));

        verify(selectAllStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("brand");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("model");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("manufacture_year");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("mileage");
        verify(mockedResultSet, times(initialDatabaseState.size()+1)).next();
    }

    @Test
    public void gettingByIdTest() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenReturn(initialDatabaseState.get(2).getId());
        when(mockedResultSet.getString("brand")).thenReturn(initialDatabaseState.get(2).getBrand());
        when(mockedResultSet.getString("model")).thenReturn(initialDatabaseState.get(2).getModel());
        when(mockedResultSet.getInt("manufacture_year")).thenReturn(initialDatabaseState.get(2).getManufactureYear());
        when(mockedResultSet.getInt("mileage")).thenReturn(initialDatabaseState.get(2).getMileage());
        when(selectByIdStatementMock.executeQuery()).thenReturn(mockedResultSet);

        Car retrievedCar = dao.getCar(2L);
        assertEquals(retrievedCar, initialDatabaseState.get(2));

        verify(selectByIdStatementMock, times(1)).setLong(1, 2);
        verify(selectByIdStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getLong("id");
        verify(mockedResultSet, times(1)).getString("brand");
        verify(mockedResultSet, times(1)).getString("model");
        verify(mockedResultSet, times(1)).getInt("manufacture_year");
        verify(mockedResultSet, times(1)).getInt("mileage");
        verify(mockedResultSet, times(1)).next();
    }

}
