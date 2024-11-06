package lab07.ex03;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Ex03Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex03Application.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(StudentRepository studentRepository) {
		return args -> {
			studentRepository.save(new Student(null, "John Doe", 22, "john.doe@example.com", 6.5));
			studentRepository.save(new Student(null, "Jane Doe", 21, "jane.doe@example.com", 7.5));
			studentRepository.save(new Student(null, "James Smith", 23, "james.smith@example.com", 8.0));

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
		};
	}
}
