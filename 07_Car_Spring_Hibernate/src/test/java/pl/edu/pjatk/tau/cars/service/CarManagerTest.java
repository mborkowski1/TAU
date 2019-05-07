package pl.edu.pjatk.tau.cars.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjatk.tau.cars.domain.Car;
import pl.edu.pjatk.tau.cars.domain.Person;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
@Commit
@Transactional(transactionManager = "txManager")
public class CarManagerTest {

    @Autowired
    CarManager carManager;

    List<Long> carIds;
    List<Long> personIds;

    private Person addPersonHelper(String firstName, List<Car> cars) {
        Long personId;
		Person person;
		person = new Person();
		person.setFirstName(firstName);
		person.setCars(cars);
		person.setId(null);
		personIds.add(personId = carManager.addPerson(person));
		assertNotNull(personId);
        return person;
    }

    private Car addCarHelper(String brand, String model, Integer manufactureYear, Integer mileage) {
        Long carId;
        Car car;
        car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setManufactureYear(manufactureYear);
        car.setMileage(mileage);
        car.setRegistrationDate(LocalDate.now());
        carIds.add(carId = carManager.addCar(car));
        assertNotNull(carId);
        return car;
    }

    @Before
    public void setup() {
        carIds = new LinkedList<>();
        personIds = new LinkedList<>();
        Car car = addCarHelper("Volvo", "XC60", 2018, 40000);
        addCarHelper("BMW", "X5", 2017, 60000);
        addCarHelper("Audi", "A4", 2016, 75000);
        List<Car> cars = new LinkedList<>();
        cars.add(car);
        addPersonHelper("Janusz", new LinkedList<>());
        addPersonHelper("Michal", cars);
    }

    @Test
    public void addCarTest() {
        Car car = addCarHelper("Lexus", "NX", 2019, 5000);
        assertTrue(carIds.size() > 0);
        assertEquals(car.getId(), carIds.get(3));
    }

    @Test
    public void addPersonTest() {
        Person person = addPersonHelper("Grazyna", new LinkedList<>());
        assertTrue(personIds.size() > 0);
        assertEquals(person.getId(), personIds.get(2));
    }

    @Test
    public void findAllTest() {
        List<Long> foundIds = new LinkedList<>();
        for (Car car : carManager.findAll()) {
            if (carIds.contains(car.getId())) foundIds.add(car.getId());
        }
        assertEquals(carIds.size(), foundIds.size());
    }

    @Test
	public void findAllPersonsTest() {
		List <Long> foundIds = new LinkedList<>();
		for (Person person: carManager.findAllPersons()) {
			if (personIds.contains(person.getId())) foundIds.add(person.getId());
		}
		assertEquals(personIds.size(), foundIds.size());
    }

    @Test
    public void findCarsByModelTest() {
        List<Car> cars = carManager.findCarsByModel("A");
        assertEquals("A4", cars.get(0).getModel());
    }

    @Test
    public void findCarByIdWithOwnerTest() {
        Car car = carManager.findById(carIds.get(0));
        assertNotNull(car);
        assertEquals(car.getId(), carIds.get(0));
        assertNotNull(car.getPerson());
    }

    @Test
    public void findCarByIdWithoutOwnerTest() {
        Car car = carManager.findById(carIds.get(2));
        assertNotNull(car);
        assertEquals(car.getId(), carIds.get(2));
        assertNull(car.getPerson());
    }

    @Test
    public void findPersonByIdWithCarsTest() {
        Person person = carManager.findPersonById(personIds.get(1));
        assertNotNull(person);
        assertEquals(person.getId(), personIds.get(1));
        assertEquals(1, person.getCars().size());
    }

    @Test
    public void findPersonByIdWithoutCarsTest() {
        Person person = carManager.findPersonById(personIds.get(0));
        assertNotNull(person);
        assertEquals(person.getId(), personIds.get(0));
        assertEquals(0, person.getCars().size());
    }

    @Test()
    public void updateCarTest() {
        Car car = carManager.findById(carIds.get(0));
        car.setMileage(45000);
        carManager.updateCar(car);
        assertEquals(Integer.valueOf(45000), carManager.findById(carIds.get(0)).getMileage());
    }

    @Test() 
    public void updatePersonTest() {
        Person person = carManager.findPersonById(personIds.get(0));
        assertEquals(0, person.getCars().size());
        person.getCars().add(carManager.findById(carIds.get(1)));
        carManager.updatePerson(person);
        assertEquals(1, carManager.findPersonById(personIds.get(0)).getCars().size());
    }

    @Test()
    public void deletePersonTest() {
        int prevSize = carManager.findAllPersons().size();
        Person person = carManager.findPersonById(personIds.get(0));
        assertNotNull(person);
        carManager.deletePerson(person);
        assertNull(carManager.findPersonById(personIds.get(0)));
        assertEquals(prevSize - 1, carManager.findAllPersons().size());
    }

    @Test()
    public void changeCarOwnerTest() {
        Car car = carManager.findById(carIds.get(0));
        Person person1 = carManager.findPersonById(personIds.get(1));
        Person person2 = carManager.findPersonById(personIds.get(0));
        carManager.changeCarOwner(car, person1, person2);
        assertEquals(0, carManager.findPersonById(personIds.get(1)).getCars().size());
        assertEquals(1, carManager.findPersonById(personIds.get(0)).getCars().size());
        assertEquals(car.getId(), carManager.findPersonById(personIds.get(0)).getCars().get(0).getId());
    }

    @Test
    public void deleteCarWithOwnerTest() {
        Person person = carManager.findPersonById(personIds.get(1));
        assertEquals(1, person.getCars().size());
        int prevSize = carManager.findAll().size();
        Car car = carManager.findById(carIds.get(0));
        assertNotNull(car);
        carManager.deleteCar(car);
        assertNull(carManager.findById(carIds.get(0)));
        assertEquals(prevSize - 1, carManager.findAll().size());
        assertEquals(0, person.getCars().size());
    }

    @Test
    public void deleteCarWithoutOwnerTest() {
        int prevSize = carManager.findAll().size();
        Car car = carManager.findById(carIds.get(2));
        assertNotNull(car);
        carManager.deleteCar(car);
        assertNull(carManager.findById(carIds.get(2)));
        assertEquals(prevSize - 1, carManager.findAll().size());
    }

}
