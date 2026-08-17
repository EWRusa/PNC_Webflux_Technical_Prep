package com.emmett.demo_flux.student;

import com.emmett.demo_flux.containers.BaseContainer;
import com.emmett.demo_flux.student.model.Student;
import com.emmett.demo_flux.student.repository.StudentRepository;
import com.emmett.demo_flux.student.service.StudentService;
import com.emmett.demo_flux.student.service.impl.MongoStudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
@DataMongoTest
@Import(MongoStudentService.class)
public class StudentServiceIntegrationTest extends BaseContainer {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    private String id;

    @BeforeEach
    void beforeEach() {
        studentRepository.deleteAll().subscribe();

        Student testStudent = new Student();
        testStudent.setFirstName("bob");
        testStudent.setLastName("smithy");
        testStudent.setGrade(1);

        this.id = studentRepository.save(testStudent).map(student -> student.getId()).block();
    }

    @Test
    void getStudentsByGradeReturnsStudent() {
        Flux<Student> studentByGrade1 = studentService.listStudentsByGrade(1);

        StepVerifier.create(studentByGrade1)
                .expectNextMatches(student -> student.getGrade() == 1)
                .verifyComplete();
    }

    @Test
    void getStudentByIdReturnsStudent() {
        Mono<Student> getStudent = studentService.getStudent(this.id);

        StepVerifier.create(getStudent)
                .expectNextMatches(student -> student.getId().equals(this.id))
                .verifyComplete();
    }
}
