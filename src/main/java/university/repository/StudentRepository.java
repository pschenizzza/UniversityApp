package university.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import university.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
  List<Student> findByGroupIdOrderByFullNameAsc(Long groupId);
}
