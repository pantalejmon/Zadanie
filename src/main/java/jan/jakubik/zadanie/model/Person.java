package jan.jakubik.zadanie.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    @NotNull()
    private String firstName;

    @Column(name = "last_name")
    @NotNull()
    private String lastName;

    @Column(name = "born")
    @NotNull()
    @Temporal(TemporalType.DATE)
    private Calendar born;

    @Column(name = "phone", length = 9, nullable = true)
    private String phone;
}
