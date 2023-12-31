package ru.digitalhabits.homework3.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
public class DepartmentFullResponse {
    private Integer id;
    private String name;
    private boolean closed;
    private List<PersonShortResponse> persons;
}
