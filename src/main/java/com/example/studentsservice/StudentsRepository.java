package com.example.studentsservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StudentsRepository extends CrudRepository<Student, Long> {
    Optional<Student> findStudentByFirstName(String name);

    @Transactional
    void deleteStudentByFirstName(String firstName);
}
