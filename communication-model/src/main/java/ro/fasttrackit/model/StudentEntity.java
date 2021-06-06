package ro.fasttrackit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference(value = "student-coursestudent")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<CourseStudent> courseStudent;
}
