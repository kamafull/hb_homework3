package ru.digitalhabits.homework3.model.department;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.digitalhabits.homework3.domain.Department;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DepartmentShortResponse {
    private Integer id;
    private String name;

    public DepartmentShortResponse(Department department) {
        this.id = department.getId();
        this.name = department.getName();
    }
}
