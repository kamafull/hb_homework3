package ru.digitalhabits.homework3.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "department")
@NamedQuery(name = "Department.findAll", query = "SELECT d from Department d")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean closed;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private List<Person> persons;


}