package ro.fasttrackit.courseservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.fasttrackit.courseservice.exception.ResourceNotFoundException;
import ro.fasttrackit.courseservice.repository.CourseDao;
import ro.fasttrackit.courseservice.repository.CourseRepository;
import ro.fasttrackit.courseservice.repository.CourseStudentRepository;
import ro.fasttrackit.model.CourseEntity;
import ro.fasttrackit.model.CourseStudent;
import ro.fasttrackit.model.StudentEntity;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseValidator courseValidator;
    private final CourseDao courseDao;
    private final CourseStudentRepository courseStudentRepository;

    public List<CourseEntity> getAllCourses(Long studentId){
        if(studentId != null){
            return courseDao.getAll(studentId);
        }else {
            return courseRepository.findAll();
        }
    }

    public CourseEntity getCourseById(Long courseId){
        courseValidator.validateExistsOrThrow(courseId);
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find course with id " + courseId));
    }

    public CourseEntity addCourse(CourseEntity courseEntity){
        return courseRepository.save(courseEntity);
    }

    public List<StudentEntity> getAllStudentsForCourse(Long courseId){
        return courseDao.getStudents(courseId);
    }

    public CourseStudent addStudentToCourse(Long courseId, Long studentId){
        courseValidator.validateExistsOrThrow(courseId);
        CourseEntity foundCourse = courseRepository.getById(courseId);
        StudentEntity foundStudent = courseDao.getStudentById(studentId);

        CourseStudent newCourseStudent = new CourseStudent(0L, 0, foundStudent, foundCourse);
        return courseStudentRepository.save(newCourseStudent);
    }

    @RabbitListener(queues = "#{fanoutQueue.name}")
    void fanoutListener(Long studentId) {
        log.info("Deleting student " + studentId);
        courseStudentRepository.deleteByStudentEntityId(studentId);
    }
}
