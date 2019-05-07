package pl.edu.pjatk.tau.cars.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjatk.tau.cars.domain.Car;

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

}
