package ro.fasttrackit.studentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.model.CourseStudent;

import java.util.Optional;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
    Optional<CourseStudent> deleteAllByStudentEntityId(long studentId);
}
