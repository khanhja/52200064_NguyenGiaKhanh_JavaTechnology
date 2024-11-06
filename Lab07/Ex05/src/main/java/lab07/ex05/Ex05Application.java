package lab07.ex05;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Ex05Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex05Application.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(StudentRepository studentRepository, StudentService studentService) {
		return args -> {
			studentRepository.save(new Student(null, "John Doe", 22, "john.doe@example.com", 6.5));
			studentRepository.save(new Student(null, "Jane Doe", 21, "jane.doe@example.com", 7.5));
			studentRepository.save(new Student(null, "James Smith", 23, "james.smith@example.com", 8.0));
			studentRepository.save(new Student(null, "Michael Johnson", 24, "michael.johnson@example.com", 6.0));
			studentRepository.save(new Student(null, "Emily Davis", 25, "emily.davis@example.com", 7.0));
			studentRepository.save(new Student(null, "Daniel Brown", 22, "daniel.brown@example.com", 6.7));
			studentRepository.save(new Student(null, "Sophia Wilson", 21, "sophia.wilson@example.com", 8.5));

			List<Student> students = (List<Student>) studentRepository.findAll();
			System.out.println("Student list:");
			students.forEach(System.out::println);

			if (!students.isEmpty()) {
				Student studentToUpdate = students.get(0);
				studentToUpdate.setAge(23);
				studentRepository.save(studentToUpdate);

				System.out.println("After update:");
				System.out.println(studentRepository.findById(studentToUpdate.getId()).orElse(null));
			}

			if (!students.isEmpty()) {
				Student studentToDelete = students.get(1);
				studentRepository.delete(studentToDelete);

				students = (List<Student>) studentRepository.findAll();
				System.out.println("After deletion Jane Doe:");
				students.forEach(System.out::println);
			}

			List<Student> greaterEqual22 = studentService.getWithAgeGreaterThanEqual(22);
			System.out.println("Students with age >= 22:");
			greaterEqual22.forEach(System.out::println);

			long count = studentService.countByIelts(7.5);
			System.out.println("Number of students with IELTS score 7.5: " + count);

			List<Student> nameSearchResults = studentService.getByNameContaining("Doe");
			System.out.println("Students whose name contains 'Doe':");
			nameSearchResults.forEach(System.out::println);
		};
	}
}
