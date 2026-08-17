package com.emmett.demo_flux.student.service.impl;

import com.emmett.demo_flux.student.model.Student;
import com.emmett.demo_flux.student.repository.StudentRepository;
import com.emmett.demo_flux.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MongoStudentService implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Mono<Student> addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Mono<Student> updateStudent(Student student) {
        return studentRepository.findById(student.getId()).flatMap(existing -> {
            existing.setFirstName(student.getFirstName());
            existing.setLastName(student.getLastName());
            existing.setGrade(student.getGrade());

            return studentRepository.save(existing);
        }).switchIfEmpty(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No student of ID exists to update."))
        );
    }

    @Override
    public Mono<Void> increaseGradeOfAllStudents() {
        return studentRepository.findAll().map(student -> {
            student.setGrade(student.getGrade() + 1);
            return student;
        }).as(studentRepository::saveAll).then();
    }

    @Override
    public Mono<Student> getStudent(String id) {
        return studentRepository.findById(id);
    }

    @Override
    public Flux<Student> listStudents() {

        return studentRepository.findAll();
    }

    @Override
    public Flux<Student> listStudentsByGrade(int grade) {
        return studentRepository.findAllByGrade(grade);
    }
}
