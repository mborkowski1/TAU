package pl.edu.pjatk.tau.cars.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjatk.tau.cars.domain.Car;

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
    public Long addCar(Car car) {
        return (Long) sessionFactory.getCurrentSession().save(car);
    }

}
