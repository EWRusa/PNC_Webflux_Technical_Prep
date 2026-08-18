package com.emmett.demo_flux.student.controller;

import com.emmett.demo_flux.student.model.Student;
import com.emmett.demo_flux.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public Mono<Student> getStudent(@PathVariable String id) {
        return studentService.getStudent(id);
    }

    @GetMapping
    public Flux<Student> getStudents() {
        return studentService.listStudents();
    }

    @PostMapping
    public Mono<Student> addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }
}
