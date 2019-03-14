package com.example.studentsservice;

import java.util.Optional;

public interface StudentsService {
    Iterable<Student> listStudents();

    Optional<Student> findStudentByFirstNameAndLastName(String name, String lastName);

    Student saveStudent(Student studentToAdd);

    void deleteStudentByName(String name);
}
