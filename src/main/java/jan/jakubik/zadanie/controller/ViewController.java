package jan.jakubik.zadanie.controller;


import jan.jakubik.zadanie.model.Person;
import jan.jakubik.zadanie.model.PersonRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ViewController {
    private final PersonRepo personRepo;
    private Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    public ViewController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @GetMapping("/count")
    long Counter() {
        logger.info("Displayed counts");
        return personRepo.count();
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    List<Person> getAll(@RequestParam("page") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size) {
        logger.info("Displayed all persons");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "born"));
        Page<Person> pageResult = personRepo.findAll(pageable);
        if (pageResult.hasContent())
            return pageResult.getContent();
        else
            return new ArrayList<Person>();
    }

    @GetMapping("/OldOne")
    Person getOlderOne() {
        List<Person> list = personRepo.findAll(Sort.by(Sort.Direction.DESC, "born"));
        boolean check = false;
        int iterator = 0;
        Person toReturn = null;
        while (!check && !list.isEmpty() && iterator < list.size()) {
            if (list.get(iterator).getPhone().matches("[0-9]{9}")) {
                toReturn = list.get(iterator);
                check = true;
            } else {
                iterator++;
            }
        }
        logger.info("Displayed oldest person with phone number");
        return toReturn;
    }

    @GetMapping("/searchBy/{lastName}")
    List<Person> searchByLastName(@PathVariable String lastName) {
        List<Person> list = personRepo.findAll();
        List<Person> toReturn = new ArrayList<Person>();
        for (Person p : list) {
            if (p.getLastName().contains(lastName)) toReturn.add(p);
        }
        logger.info("Searched person by last name. LastName sequence: " + lastName);
        return toReturn;
    }
}
