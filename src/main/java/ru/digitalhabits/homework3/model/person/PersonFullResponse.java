package ru.digitalhabits.homework3.model.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.department.DepartmentShortResponse;

import java.util.Optional;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PersonFullResponse {
    private Integer id;
    private String fullName;
    private Integer age;
    private DepartmentShortResponse department;

    public PersonFullResponse(Person person) {
        this.id = person.getId();
        this.fullName = person.getFullName();
        this.age = person.getAge();
        Optional.ofNullable(person.getDepartment())
                .map(department -> this.department = new DepartmentShortResponse(department));
    }
}
