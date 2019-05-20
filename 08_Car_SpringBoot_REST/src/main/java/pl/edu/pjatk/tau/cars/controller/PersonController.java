package pl.edu.pjatk.tau.cars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.tau.cars.domain.Person;
import pl.edu.pjatk.tau.cars.service.CarManager;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    CarManager carManager;

    @RequestMapping("/persons")
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        for (Person p : carManager.findAllPersons()) {
            persons.add(p.clone());
        }
        return persons;
    }

    @RequestMapping(value = "persons", method = RequestMethod.POST)
    public Person addPerson(@RequestBody Person person) {
        person.setId(carManager.addPerson(person));
        return person;
    }

    @RequestMapping(value = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Person getPerson(@PathVariable Long id) {
        return carManager.findPersonById(id).clone();
    }

    @RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Person> getPersons(@RequestParam(value = "filter", required = false) String f) {
        List<Person> persons = new ArrayList<>();
        for (Person p : carManager.findAllPersons()) {
            if (f == null) {
                persons.add(p.clone());
            }
            else if (p.getFirstName().contains(f)) {
                persons.add(p);
            }
        }
        return persons;
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deletePerson(@PathVariable Long id) {
        carManager.deletePerson(carManager.findPersonById(id));
        return "OK";
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Person personToUpdate = carManager.findPersonById(id);
        personToUpdate.setFirstName(person.getFirstName());
        carManager.updatePerson(personToUpdate);
        return "OK";
    }

}
