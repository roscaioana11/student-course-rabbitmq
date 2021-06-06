package ro.fasttrackit.courseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.courseservice.service.CourseService;
import ro.fasttrackit.model.CourseEntity;
import ro.fasttrackit.model.CourseStudent;
import ro.fasttrackit.model.StudentEntity;

import java.util.List;

@RestController
@RequestMapping("courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    List<CourseEntity> getCourses(@RequestParam(required = false) Long studentId){
        return courseService.getAllCourses(studentId);
    }

    @GetMapping("{courseId}")
    CourseEntity getCourseById(@PathVariable Long courseId){
        return courseService.getCourseById(courseId);
    }

    @PostMapping
    CourseEntity addCourse(@RequestBody CourseEntity courseEntity){
        return courseService.addCourse(courseEntity);
    }

    @GetMapping("/{courseId}/students")
    List<StudentEntity> getAllStudentsForCourse(@PathVariable Long courseId){
        return courseService.getAllStudentsForCourse(courseId);
    }

    @PostMapping("/{courseId}/students/{studentId}")
    CourseStudent addStudentToCourse(@PathVariable Long courseId,
                                     @PathVariable Long studentId){
        return courseService.addStudentToCourse(courseId,studentId);
    }
}
