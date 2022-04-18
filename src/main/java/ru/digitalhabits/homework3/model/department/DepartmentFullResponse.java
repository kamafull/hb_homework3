package ru.digitalhabits.homework3.model.department;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.model.person.PersonShortResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DepartmentFullResponse {
    private Integer id;
    private String name;
    private boolean closed;
    private List<PersonShortResponse> persons;

    public DepartmentFullResponse(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.closed = department.isClosed();
        Optional.ofNullable(department.getPersons())
                .map(person -> this.persons = person.stream()
                        .map(PersonShortResponse::new)
                        .collect(Collectors.toList()));
    }
}
