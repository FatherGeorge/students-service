package com.example.studentsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentsServiceImpl implements StudentsService {

    @Autowired
    private StudentsRepository studentsRepository;

    @Override
    public Iterable<Student> listStudents() {
        return studentsRepository.findAll();
    }

    @Override
    public Optional<Student> findStudentByFirstNameAndLastName(String firstName, String lastName) {
        return studentsRepository.findStudentByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Student saveStudent(Student studentToAdd) {
        return studentsRepository.save(studentToAdd);
    }

    @Override
    public void deleteStudentByFirstNameAndLastName(String firstName, String lastName) {
        studentsRepository.deleteStudentByFirstNameAndLastName(firstName, lastName);
    }
}
