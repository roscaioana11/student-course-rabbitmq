package ro.fasttrackit.studentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.studentservice.exception.ValidationException;
import ro.fasttrackit.studentservice.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class StudentValidator {
    private final StudentRepository repository;

    private Optional<ValidationException> exists(Long studentId) {
        return repository.existsById(studentId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Student with id " + studentId + " doesn't exist")));
    }

    public void validateExistsOrThrow(Long studentId) {
        exists(studentId).ifPresent(ex -> {
            throw ex;
        });
    }
}
