package pl.edu.pjatk.tau.cars.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.pjatk.tau.cars.domain.Car;
import pl.edu.pjatk.tau.cars.service.CarManager;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManager carManager;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(mockMvc);
    }

    @Test
    public void getAllCarsShouldReturnEmptyResults() throws Exception {
        this.mockMvc.perform(get("/cars")).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    public void getAllCarsShouldReturnSomeResults() throws Exception {
        List<Car> expectedResult = new ArrayList<>();
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Volvo");
        car.setModel("XC60");
        car.setManufactureYear(2018);
        car.setMileage(25000);
        expectedResult.add(car);
        when(carManager.findAll()).thenReturn(expectedResult);
        this.mockMvc.perform(get("/cars")).andExpect(status().isOk()).andExpect(content().json("[{\"id\":1,\"brand\":\"Volvo\",\"model\":\"XC60\",\"manufactureYear\":2018,\"mileage\":25000}]"));
    }

    @Test
    public void getCarShouldReturnSomeResult() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Audi");
        car.setModel("A4");
        car.setManufactureYear(2017);
        car.setMileage(40000);
        when(carManager.findById(car.getId())).thenReturn(car);
        this.mockMvc.perform(get("/car/1")).andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"brand\":\"Audi\",\"model\":\"A4\",\"manufactureYear\":2017,\"mileage\":40000}"));
    }

    @Test
    public void addCarShouldShouldReallyAddItToDatabase() throws Exception {
        Car car = new Car();
        car.setBrand("BMW");
        car.setModel("X5");
        car.setManufactureYear(2016);
        car.setMileage(60000);
        when(carManager.addCar(car)).thenReturn(1L);
        this.mockMvc.perform(post("/cars")
                .content("{\"brand\":\"BMW\",\"model\":\"X5\",\"manufactureYear\":2016,\"mileage\":60000}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"brand\":\"BMW\",\"model\":\"X5\",\"manufactureYear\":2016,\"mileage\":60000}"));
        car.setId(1L);
        verify(carManager).addCar(car);
    }

    @Test
    public void deleteCarFromDatabase() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Lexus");
        car.setModel("NX");
        car.setManufactureYear(2019);
        car.setMileage(5000);
        when(carManager.findById(1L)).thenReturn(car);

        this.mockMvc.perform(delete("/car/" + car.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));

        verify(carManager).deleteCar(car);
    }

    @Test
    public void updateCarInDatabase() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setBrand("Lexus");
        car.setModel("RX");
        car.setManufactureYear(2015);
        car.setMileage(100000);
        this.mockMvc.perform(put("/car/" + car.getId())
                .content("{\"id\":1,\"brand\":\"Lexus\",\"model\":\"RX\",\"manufactureYear\":2015,\"mileage\":100000}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        car.setMileage(100000);
        verify(carManager).updateCar(car);
    }

}
