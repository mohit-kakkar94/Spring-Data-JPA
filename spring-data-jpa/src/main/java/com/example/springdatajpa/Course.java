package com.example.springdatajpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Course")
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            updatable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "department",
            updatable = false,
            columnDefinition = "TEXT"
    )
    private String department;

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Enrolment> enrolments = new ArrayList<>();

    public Course() {
    }

    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

}
