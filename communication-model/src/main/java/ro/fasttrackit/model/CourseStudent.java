package ro.fasttrackit.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity(name = "course_student")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float grade;

    @ManyToOne//(cascade = CascadeType.ALL)
    private StudentEntity studentEntity;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JsonBackReference(value = "course-coursestudent")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CourseEntity courseEntity;
}
