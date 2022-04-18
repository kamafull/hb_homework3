package ru.digitalhabits.homework3.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "person")
@NamedQuery(name = "Person.findAll", query = "SELECT p from Person p")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(length = 80)
    private String middleName;

    @Column
    private int age;

    @ManyToOne
    @JoinColumn
    private Department department;

    public String getFullName() {
        return Stream.of(firstName, lastName, middleName)
                .filter(v -> v != null && !v.isEmpty())
                .collect(Collectors.joining(" "));
    }
}
