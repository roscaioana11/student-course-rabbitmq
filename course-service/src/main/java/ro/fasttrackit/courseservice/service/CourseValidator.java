package ro.fasttrackit.courseservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.courseservice.exception.ValidationException;
import ro.fasttrackit.courseservice.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class CourseValidator {
    private final CourseRepository repository;

    private Optional<ValidationException> exists(Long courseId) {
        return repository.existsById(courseId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Course with id " + courseId + " doesn't exist")));
    }

    public void validateExistsOrThrow(Long courseId) {
        exists(courseId).ifPresent(ex -> {
            throw ex;
        });
    }
}
