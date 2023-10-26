package ru.digitalhabits.homework3.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Objects;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "closed")
    private Boolean closed = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }
}