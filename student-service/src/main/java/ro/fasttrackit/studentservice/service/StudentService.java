package ro.fasttrackit.studentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ro.fasttrackit.model.StudentEntity;
import ro.fasttrackit.studentservice.exception.ResourceNotFoundException;
import ro.fasttrackit.studentservice.model.StudentFilter;
import ro.fasttrackit.studentservice.repository.StudentDao;
import ro.fasttrackit.studentservice.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final RabbitTemplate rabbitTemplate;
    private final StudentRepository studentRepository;
    private final StudentValidator validator;
    private final StudentDao dao;
    private final FanoutExchange fanoutExchange;

    public List<StudentEntity> getFilteredStudents(StudentFilter filter){
        return dao.getAll(filter);
    }

    public StudentEntity getStudentById(Long studentId){
        validator.validateExistsOrThrow(studentId);
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find student with id " + studentId));
    }

    public StudentEntity addStudent(StudentEntity studentEntity){
        return studentRepository.save(studentEntity);
    }

    public void deleteStudent(Long studentId){
        fanout(studentId);
        studentRepository.deleteById(studentId);
    }

    private void fanout(Long studentId) {
        rabbitTemplate.convertAndSend(
                fanoutExchange.getName(),
                studentId == null ? 0L : studentId);
    }
}
