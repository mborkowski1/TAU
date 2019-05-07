package pl.edu.pjatk.tau.cars.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjatk.tau.cars.domain.Car;
import pl.edu.pjatk.tau.cars.domain.Person;

import java.util.List;

@Component
@Transactional
public class CarManagerHibernateImpl implements CarManager {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Car> findAll() {
        return sessionFactory.getCurrentSession().getNamedQuery("car.findAll").list();
    }

    @Override
    public List<Car> findCarsByModel(String modelNameFragment) {
        return sessionFactory.getCurrentSession().getNamedQuery("car.findCarsByModel").setParameter("modelNameFragment", "%"+modelNameFragment+"%").list();
    }

    @Override
    public Car findById(Long id) {
        return sessionFactory.getCurrentSession().get(Car.class, id);
    }

    @Override
    public Long addCar(Car car) {
        return (Long) sessionFactory.getCurrentSession().save(car);
    }

    @Override
    public void updateCar(Car car) {
        sessionFactory.getCurrentSession().update(car);
    }

    @Override
    public void deleteCar(Car car) {
        if (car.getPerson() != null) {
            car.getPerson().getCars().remove(car);
            sessionFactory.getCurrentSession().update(car.getPerson());
        }
        sessionFactory.getCurrentSession().delete(car);
    }

	@Override
	public List<Person> findAllPersons() {
		return sessionFactory.getCurrentSession().getNamedQuery("person.findAll").list();
	}

	@Override
	public Person findPersonById(Long id) {
		return sessionFactory.getCurrentSession().get(Person.class, id);
	}

	@Override
	public Long addPerson(Person person) {
		if (person.getId() != null) throw new IllegalArgumentException("the person ID should be null if added to database");
		sessionFactory.getCurrentSession().persist(person);
		for (Car car : person.getCars()) {
			car.setPerson(person);
			sessionFactory.getCurrentSession().update(car);
		}
		sessionFactory.getCurrentSession().flush();
        return person.getId();
	}

	@Override
	public void updatePerson(Person person) {
		sessionFactory.getCurrentSession().update(person);
	}

	@Override
	public void deletePerson(Person person) {
		person = sessionFactory.getCurrentSession().get(Person.class, person.getId());
        for (Car car : person.getCars()) {
            car.setPerson(null);
            sessionFactory.getCurrentSession().update(car);
        }
        sessionFactory.getCurrentSession().delete(person);
	}

	@Override
	public void changeCarOwner(Car car, Person person1, Person person2) {
        person1.getCars().remove(car);
        sessionFactory.getCurrentSession().update(person1);
        person2.getCars().add(car);
        sessionFactory.getCurrentSession().update(person2);
	}

}
