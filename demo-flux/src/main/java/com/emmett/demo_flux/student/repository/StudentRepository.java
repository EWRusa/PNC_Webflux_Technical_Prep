package com.emmett.demo_flux.student.repository;

import com.emmett.demo_flux.student.model.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {

    Flux<Student> findAllByGrade(int grade);
}
