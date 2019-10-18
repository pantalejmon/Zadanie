package jan.jakubik.zadanie.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("personRepository")
public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByLastName(String lastName);
    Person findByPhone(String phone);
}
