package lab07.ex06;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class Ex06Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex06Application.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(StudentRepository studentRepository) {
		return args -> {
			studentRepository.save(new Student(null, "John Doe", 22, "john.doe@example.com", 6.5));
			studentRepository.save(new Student(null, "Jane Doe", 21, "jane.doe@example.com", 7.5));
			studentRepository.save(new Student(null, "James Smith", 23, "james.smith@example.com", 8.0));
			studentRepository.save(new Student(null, "Michael Johnson", 24, "michael.johnson@example.com", 6.0));
			studentRepository.save(new Student(null, "Emily Davis", 25, "emily.davis@example.com", 7.0));
			studentRepository.save(new Student(null, "Daniel Brown", 22, "daniel.brown@example.com", 6.7));
			studentRepository.save(new Student(null, "Sophia Wilson", 21, "sophia.wilson@example.com", 8.5));
			studentRepository.save(new Student(null, "Chris Evans", 24, "chris.evans@example.com", 7.3));
			studentRepository.save(new Student(null, "Scarlett Johansson", 22, "scarlett.johansson@example.com", 7.8));
			studentRepository.save(new Student(null, "Robert Downey", 24, "robert.downey@example.com", 7.0));
			studentRepository.save(new Student(null, "Mark Ruffalo", 22, "mark.ruffalo@example.com", 8.1));
			studentRepository.save(new Student(null, "Jeremy Renner", 23, "jeremy.renner@example.com", 6.9));
			studentRepository.save(new Student(null, "Chris Hemsworth", 25, "chris.hemsworth@example.com", 7.4));
			studentRepository.save(new Student(null, "Tom Hiddleston", 26, "tom.hiddleston@example.com", 6.8));
			studentRepository.save(new Student(null, "Benedict Cumberbatch", 23, "benedict.cumberbatch@example.com", 7.6));

			List<Student> studentsSorted = studentRepository.findAllSortedByAgeAndIeltsScore(
					Sort.by(Sort.Order.desc("age"), Sort.Order.asc("ieltsScore"))
			);

			System.out.println("Students sorted by age (desc) and IELTS score (asc):");
			studentsSorted.forEach(System.out::println);

			var studentsPage = studentRepository.findAll(PageRequest.of(0, 10));
			studentsPage.getContent().subList(3, 6).forEach(System.out::println);
		};
	}
}
