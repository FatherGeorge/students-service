package com.example.studentsservice;

import java.util.Optional;

public interface StudentsService {
    Iterable<Student> listStudents();

    Optional<Student> findStudentByFirstNameAndLastName(String firstName, String lastName);

    Student saveStudent(Student studentToAdd);

    void deleteStudentByFirstNameAndLastName(String firstName, String lastName);
}
