package com.example.studentsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentsController {

    @Autowired
    StudentsService studentsService;

    @GetMapping("/appInfo")
    String smokeTest() {
        return "App is running";
    }

    @GetMapping("/")
    Response listStudents() {
        Iterable<Student> students = studentsService.listStudents();
        Response response = new Response();
        response.setStatusCode("0");
        response.setStatusDesc("Ok");
        List<Student> listStudents = new ArrayList<>();
        students.forEach(listStudents::add);
        response.setStudents(listStudents);

        return response;
    }

    @PostMapping("/")
    Response addStudent(@RequestBody Student studentToAdd) {
        final Optional<Student> student = studentsService.findStudentByFirstNameAndLastName(studentToAdd.getFirstName(), studentToAdd.getLastName());
        if (student.isPresent())
            return new Response().setStatusCode("2").setStatusDesc("Student already exists");

        Student savedStudent = studentsService.saveStudent(studentToAdd);
        return new Response().setStatusCode("0").setStatusDesc("Ok").setStudent(savedStudent);
    }

    @DeleteMapping("/")
    Response deketeStudent(@RequestBody Student studentToDelete) {
        final Optional<Student> student = studentsService.findStudentByFirstNameAndLastName(studentToDelete.getFirstName(), studentToDelete.getLastName());
        if (!student.isPresent())
            return new Response().setStatusCode("3").setStatusDesc("Student doesn't exist");

        studentsService.deleteStudentByName(studentToDelete.getFirstName());

        return new Response().setStatusCode("0").setStatusDesc("Ok");
    }

    @PutMapping("/")
    Response changeStudentCourses(@RequestBody Student studentToChange) {
        final Optional<Student> student = studentsService.findStudentByFirstNameAndLastName(studentToChange.getFirstName(), studentToChange.getLastName());
        if (!student.isPresent())
            return new Response().setStatusCode("3").setStatusDesc("Student doesn't exist");

        studentsService.saveStudent(studentToChange);

        return new Response().setStatusCode("0").setStatusDesc("Ok");
    }
}
