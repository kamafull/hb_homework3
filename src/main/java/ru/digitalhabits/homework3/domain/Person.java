package ru.digitalhabits.homework3.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.digitalhabits.homework3.model.PersonShortResponse;

import javax.persistence.*;
import java.util.Objects;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "age")
    private Integer age;
    @ManyToOne
    private Department department;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person that = (Person) o;
        return Objects.equals(id, that.id);
    }
}
