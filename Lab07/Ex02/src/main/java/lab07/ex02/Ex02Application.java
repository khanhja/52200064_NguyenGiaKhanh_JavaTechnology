package lab07.ex02;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Ex02Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex02Application.class, args);
	}

	@Bean
	CommandLineRunner run(StudentRepository studentRepository) {
		return args -> {
			Student student1 = new Student(null, "Nguyen Gia Khanh", 20, "52200064@gmail.com", 6.5);
			Student student2 = new Student(null, "Dao Khanh Nam", 21, "khanhnam@gmail.com", 7.0);
			Student student3 = new Student(null, "Nguyen Van Teo", 22, "teovan@gmail.com", 6.0);

			studentRepository.save(student1);
			studentRepository.save(student2);
			studentRepository.save(student3);

			System.out.println("Student list:");
			studentRepository.findAll().forEach(System.out::println);

			student1.setName("Gia-Khanh Nguyen");
			student1.setIeltsScore(7.5);
			studentRepository.save(student1);

			System.out.println("\nStudent list after update:");
			studentRepository.findAll().forEach(System.out::println);

			studentRepository.delete(student2);

			System.out.println("\nStudent list after delete:");
			studentRepository.findAll().forEach(System.out::println);
		};
	}

}
