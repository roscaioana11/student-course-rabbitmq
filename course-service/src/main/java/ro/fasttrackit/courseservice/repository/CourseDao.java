package ro.fasttrackit.courseservice.repository;

import org.springframework.stereotype.Repository;
import ro.fasttrackit.model.CourseEntity;
import ro.fasttrackit.model.CourseStudent;
import ro.fasttrackit.model.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Repository
public class CourseDao {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CourseDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = this.entityManager.getCriteriaBuilder();
    }

    public List<CourseEntity> getAll(Long studentId){
        CriteriaQuery<CourseEntity> criteria = criteriaBuilder.createQuery(CourseEntity.class);
        Root<CourseEntity> rootCourse = criteria.from(CourseEntity.class);
        Root<CourseStudent> rootCourseStudent = criteria.from(CourseStudent.class);

        List<Predicate> whereClause = new ArrayList<>();
        ofNullable(studentId)
                .ifPresent(id -> checkForMatches(id, whereClause, rootCourse, rootCourseStudent));

        CriteriaQuery<CourseEntity> query = criteria.select(rootCourse).where(whereClause.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    public List<StudentEntity> getStudents(Long courseId) {

        CriteriaQuery<StudentEntity> criteria = criteriaBuilder.createQuery(StudentEntity.class);
        Root<StudentEntity> rootStudent = criteria.from(StudentEntity.class);
        Root<CourseStudent> rootCourseStudent = criteria.from(CourseStudent.class);

        List<Predicate> whereClause = new ArrayList<>();
        ofNullable(courseId)
                .ifPresent(id -> checkForStudentCourseMatches(id, whereClause, rootStudent, rootCourseStudent));

        CriteriaQuery<StudentEntity> query = criteria.select(rootStudent).where(whereClause.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    public StudentEntity getStudentById(Long studentId){
        CriteriaQuery<StudentEntity> criteria = criteriaBuilder.createQuery(StudentEntity.class);
        Root<StudentEntity> rootStudent = criteria.from(StudentEntity.class);

        List<Predicate> whereClause = new ArrayList<>();
        ofNullable(studentId)
                .ifPresent(id -> whereClause.add(criteriaBuilder.equal(rootStudent.get("id"), id)));

        CriteriaQuery<StudentEntity> query = criteria.select(rootStudent).where(whereClause.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    private void checkForStudentCourseMatches(Long id, List<Predicate> whereClause, Root<StudentEntity> rootStudent, Root<CourseStudent> rootCourseStudent){
        whereClause.add(criteriaBuilder.equal(rootCourseStudent.get("courseEntity").get("id"), id));
        whereClause.add(criteriaBuilder.equal(rootCourseStudent.get("studentEntity").get("id"), rootStudent.get("id")));
    }

    private void checkForMatches(Long id, List<Predicate> whereClause, Root<CourseEntity> rootCourse, Root<CourseStudent> rootCourseStudent){
        whereClause.add(criteriaBuilder.equal(rootCourseStudent.get("studentEntity").get("id"), id));
        whereClause.add(criteriaBuilder.equal(rootCourseStudent.get("courseEntity").get("id"), rootCourse.get("id")));
    }
}
