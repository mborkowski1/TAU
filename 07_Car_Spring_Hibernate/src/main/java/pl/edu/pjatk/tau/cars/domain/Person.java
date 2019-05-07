package pl.edu.pjatk.tau.cars.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.LinkedList;

@Data
@EqualsAndHashCode
@Entity(name = "Person")
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name = "person.findAll", query = "Select p from Person p")
})

public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval=false, mappedBy = "person")
    private List<Car> cars = new LinkedList<>();

}
