package ru.digitalhabits.homework3.model.person;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.digitalhabits.homework3.domain.Person;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PersonShortResponse {
    private Integer id;
    private String fullName;

    public PersonShortResponse(Person person) {
        this.id = person.getId();
        this.fullName = person.getFullName();
    }
}
