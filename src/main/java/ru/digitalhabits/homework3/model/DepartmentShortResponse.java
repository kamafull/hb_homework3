package ru.digitalhabits.homework3.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class DepartmentShortResponse {
    private Integer id;
    private String name;
}
