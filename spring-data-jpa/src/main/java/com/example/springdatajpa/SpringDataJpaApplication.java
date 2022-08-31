package com.example.springdatajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {

		return args -> {

			Faker faker = new Faker();

			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = firstName + "." + lastName + "@gmail.com";

			Student student = new Student(
					firstName,
					lastName,
					email,
					faker.number().numberBetween(17, 55)
			);

            StudentIdCard studentIdCard = new StudentIdCard(student, "123456789");
            student.setStudentIdCard(studentIdCard);

            student.addBook(
                    new Book("Clean Code", LocalDateTime.now().minusDays(4))
            );

            student.addBook(
                    new Book("Think and Grow Rich", LocalDateTime.now())
            );

            student.addBook(
                    new Book("Spring Data JPA", LocalDateTime.now().minusYears(1))
            );



            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 1L),
                    student, new Course("Computer Science", "IT"),
                    LocalDateTime.now()
            ));

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 2L),
                    student,
                    new Course("Spring Boot", "IT"),
                    LocalDateTime.now().minusDays(18)
            ));

            studentRepository.save(student); //Student also gets saved because we defined
            //cascade = CascadeType.ALL

            studentRepository.findById(1L).ifPresent(
                    s -> {
                        System.out.println("Fetch book lazy...");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> {
                            System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
                        });
                    }
            );

		};
	}

	private void sorting(StudentRepository studentRepository) {

		//Sorting students by their first names
		Sort sort1 = Sort.by(Sort.Direction.ASC, "firstName");
		studentRepository.findAll(sort1)
				.forEach(student -> System.out.println(student.getFirstName()));

		//Sorting students by their first name in ascending order and age in descending order
		Sort sort2 = Sort.by("firstName").ascending().and(Sort.by("age").descending());
		studentRepository.findAll(sort2)
				.forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()) );
	}

	private void generateRandomStudents(StudentRepository studentRepository) {
		//Creating fake students using Faker
		Faker faker = new Faker();
		for (int i = 0; i < 20; ++i) {

			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = firstName + "." + lastName + "@gmail.com";

			Student student = new Student(
					firstName,
					lastName,
					email,
					faker.number().numberBetween(17, 55)
			);

			studentRepository.save(student);

		}
	}

}
