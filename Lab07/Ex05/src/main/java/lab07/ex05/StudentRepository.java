package lab07.ex05;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.age >= :age")
    List<Student> findByAge(int age);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.ieltsScore = :ieltsScore")
    long countByIelts(double ieltsScore);

    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByNameContaining(String name);
}

