package ro.fasttrackit.studentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.model.StudentEntity;
import ro.fasttrackit.studentservice.model.StudentFilter;
import ro.fasttrackit.studentservice.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    List<StudentEntity> getFilteredStudents(StudentFilter filter){
        return studentService.getFilteredStudents(filter);
    }

    @GetMapping("/{studentId}")
    StudentEntity getStudentById(@PathVariable Long studentId){
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    StudentEntity addStudent(@RequestBody StudentEntity studentEntity){
        return studentService.addStudent(studentEntity);
    }

    @DeleteMapping("/{studentId}")
    void deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
    }
}
