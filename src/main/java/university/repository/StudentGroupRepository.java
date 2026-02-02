package university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import university.entity.StudentGroup;
import university.web.dto.GroupRowView;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
  @Query(
      value =
          """
                SELECT g.id AS id,
                       g.number AS number,
                       g.created_at AS createdAt,
                       COUNT(s.id) AS studentCount
                FROM student_groups g
                LEFT JOIN students s ON s.group_id = g.id
                GROUP BY g.id, g.number, g.created_at
                ORDER BY g.created_at DESC
            """,
      countQuery = "SELECT COUNT(*) FROM student_groups",
      nativeQuery = true)
  Page<GroupRowView> findGroupRowsOrdered(Pageable pageable);

  boolean existsByNumber(String number);
}
