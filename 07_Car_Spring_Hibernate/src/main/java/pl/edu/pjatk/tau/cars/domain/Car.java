package pl.edu.pjatk.tau.cars.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

}
