package com.emmett.demo_flux.student;

import com.emmett.demo_flux.containers.MongoContainer;
import com.emmett.demo_flux.student.model.Student;
import com.emmett.demo_flux.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@DataMongoTest // Scans only MongoDB components for faster context slicing
public class StudentRepositoryTest extends MongoContainer {

    @Autowired
    private StudentRepository studentRepository;


    @Test
    void shouldSaveAndFetchStudentReactively() {
        Student student = new Student();
        student.setFirstName("Steve");
        student.setLastName("Minecraft");
        student.setGrade(12);
        Mono<Student> saveAndFindFlux = studentRepository.save(student)
                .flatMap(saved -> studentRepository.findById(saved.getId()));

        StepVerifier.create(saveAndFindFlux)
                .expectNextMatches(foundStudent ->
                        foundStudent.getFirstName().equals("Steve")
                )
                .verifyComplete(); // Assures the publisher terminates successfully
    }
}
