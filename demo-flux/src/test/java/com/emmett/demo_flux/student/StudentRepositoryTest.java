package com.emmett.demo_flux.student;

import com.emmett.demo_flux.containers.BaseContainer;
import com.emmett.demo_flux.student.model.Student;
import com.emmett.demo_flux.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@DataMongoTest // Scans only MongoDB components for faster context slicing
public class StudentRepositoryTest extends BaseContainer {

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

    @Test
    void shouldSaveAllStudentsReactively() {
        Student student1 = new Student();
        student1.setFirstName("a");
        student1.setLastName("a");
        student1.setGrade(1);
        Student student2 = new Student();
        student2.setFirstName("b");
        student2.setLastName("b");
        student2.setGrade(2);
        Student student3 = new Student();
        student3.setFirstName("c");
        student3.setLastName("c");
        student3.setGrade(3);

        Flux<Student> students = studentRepository.saveAll(Flux.just(student1, student2, student3))
                .flatMap(saved -> {
                    return studentRepository.findById(saved.getId());
                });

        StepVerifier.create(students)
                .expectNextMatches(saved -> {
                    return saved.getFirstName().equals(student1.getFirstName());
                }).expectNextMatches(saved -> {
                    return saved.getFirstName().equals(student2.getFirstName());
                }).expectNextMatches(saved -> {
                    return saved.getFirstName().equals(student3.getFirstName());
                }).verifyComplete();
    }
}
