package lab07.ex06;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {
    @Query("SELECT s FROM Student s ORDER BY s.age DESC, s.ieltsScore ASC")
    List<Student> findAllSortedByAgeAndIeltsScore(Sort sort);

    Page<Student> findAll(Pageable pageable);
}