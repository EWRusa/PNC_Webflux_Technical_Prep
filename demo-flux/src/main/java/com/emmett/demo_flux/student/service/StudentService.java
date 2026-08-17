package com.emmett.demo_flux.student.service;

import com.emmett.demo_flux.student.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

    public Mono<Student> addStudent(Student student);
    public Mono<Student> updateStudent(Student student);
    public Mono<Void> increaseGradeOfAllStudents();
    public Mono<Student> getStudent(int id);
    public Flux<Student> listStudents();
    public Flux<Student> listStudentsByGrade(int grade);

}
