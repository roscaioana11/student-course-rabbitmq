package ro.fasttrackit.studentservice.repository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.model.StudentEntity;
import ro.fasttrackit.studentservice.model.StudentFilter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Repository
public class StudentDao {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public StudentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = this.entityManager.getCriteriaBuilder();
    }

    public List<StudentEntity> getAll(StudentFilter filters){
        CriteriaQuery<StudentEntity> criteria = criteriaBuilder.createQuery(StudentEntity.class);
        Root<StudentEntity> rootStudent = criteria.from(StudentEntity.class);

        List<Predicate> whereClause = new ArrayList<>();
        ofNullable(filters.getName())
                .ifPresent(name -> whereClause.add(criteriaBuilder.equal(rootStudent.get("name"), name)));
        ofNullable(filters.getMinAge())
                .ifPresent(minAge -> whereClause.add(criteriaBuilder.gt(rootStudent.get("age"), minAge)));
        ofNullable(filters.getMaxAge())
                .ifPresent(maxAge -> whereClause.add(criteriaBuilder.lt(rootStudent.get("age"), maxAge)));
        ofNullable(filters.getStudentId())
                .ifPresent(student -> whereClause.add(criteriaBuilder.equal(rootStudent.get("studentId"), student)));

        CriteriaQuery<StudentEntity> query = criteria.select(rootStudent).where(whereClause.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
