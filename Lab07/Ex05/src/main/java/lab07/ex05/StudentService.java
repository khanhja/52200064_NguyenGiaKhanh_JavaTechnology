package lab07.ex05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getWithAgeGreaterThanEqual(int age) {
        return studentRepository.findByAge(age);
    }

    public long countByIelts(double ieltsScore) {
        return studentRepository.countByIelts(ieltsScore);
    }

    public List<Student> getByNameContaining(String name) {
        return studentRepository.findByNameContaining(name);
    }
}

