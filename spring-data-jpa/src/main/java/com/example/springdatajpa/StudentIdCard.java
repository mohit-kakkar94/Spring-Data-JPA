package com.example.springdatajpa;

import javax.persistence.*;

@Entity(name = "StudentIdCard")
@Table(
        name = "student_id_card",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "student_id_card_number_unique",
                    columnNames = "card_number"
            )
        }
)
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_card_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "card_number",
            nullable = true,
            length = 15
    )
    private String cardNumber;

    @OneToOne()
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_id_card_student_id_fk"
            )
    )
    private Student student; //Unless we add a referencing column in the student table, this relationship is
                             //unidirectional

    public StudentIdCard() {
    }

    public StudentIdCard(Student student, String cardNumber) {
        this.student = student;
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", student=" + student +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
