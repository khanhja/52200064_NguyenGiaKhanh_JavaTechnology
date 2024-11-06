package lab07.ex04;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findByAgeGreaterThanEqual(Integer age);

    long countByIeltsScore(Double ieltsScore);

    List<Student> findByNameContainingIgnoreCase(String name);
}

