package pl.edu.pjatk.tau.cars.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Car")
@Table(name = "car")
@NamedQueries({
        @NamedQuery(name = "car.findAll", query = "Select c from Car c"),
        @NamedQuery(name = "car.findCarsByModel", query = "Select c from Car c where c.model like :modelNameFragment")
})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String brand;

    private String model;

    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    private Integer mileage;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @JsonIgnoreProperties("cars")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model) &&
                Objects.equals(manufactureYear, car.manufactureYear) &&
                Objects.equals(mileage, car.mileage) &&
                Objects.equals(registrationDate, car.registrationDate) &&
                Objects.equals(person, car.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, manufactureYear, mileage, registrationDate, person);
    }

}
