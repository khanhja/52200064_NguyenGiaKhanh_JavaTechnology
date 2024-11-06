package lab07.ex04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudentsWithAgeGreaterThanEqual(int age) {
        return studentRepository.findByAgeGreaterThanEqual(age);
    }

    public long countStudentsByIeltsScore(double ieltsScore) {
        return studentRepository.countByIeltsScore(ieltsScore);
    }

    public List<Student> getStudentsByNameContaining(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
}

