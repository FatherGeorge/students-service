package com.example.studentsservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public final class StudentsServiceImplTest {

    @Autowired
    StudentsRepository studentsRepository;

    @Autowired
    StudentsService studentsService;

    @Before
    public void beforeEach() {
        studentsRepository.deleteAll();
    }

    @After
    public void afterEach() {
        studentsRepository.deleteAll();
    }

    @Test
    public void listStudentsReturnsEmptyList() {
        // Setup

        // Execute
        Iterable<Student> actual = studentsService.listStudents();

        // Assert
        assertThat(actual, is(Collections.EMPTY_LIST));
    }

    @Test
    public void listStudentsReturnsList() {
        // Setup
        Student expected = new Student("Ivan", "Ivanov");
        studentsRepository.save(expected);

        // Execute
        Iterable<Student> actual = studentsService.listStudents();

        // Assert
        assertThat(actual.iterator().next(), is(expected));
    }

    @Test
    public void findStudentByNameReturnsEmptyOptional() {
        // Setup
        Student expected = new Student("Ivan", "Ivanov");
        studentsRepository.save(expected);

        // Execute
        Optional<Student> actual = studentsService.findStudentByFirstNameAndLastName("Ivan", "Petrov");

        // Assert
        assertFalse(actual.isPresent());
    }

    @Test
    public void findStudentByNameReturnsStudent() {
        // Setup
        Student expected = new Student("Ivan", "Ivanov");
        studentsRepository.save(expected);

        // Execute
        Optional<Student> actual = studentsService.findStudentByFirstNameAndLastName("Ivan", "Petrov");

        // Assert
        assertThat(actual.get(), is(expected));
    }

    @Test
    public void saveStudentSavesStudent() {
        // Setup
        Student expected = new Student("Ivan", "Ivanov");
        studentsService.saveStudent(expected);

        // Execute
        Optional<Student> actual = studentsService.findStudentByFirstNameAndLastName("Ivan", "Petrov");

        Iterable<Student> listStudents = studentsRepository.findAll();

        // Assert
        assertThat(actual.get(), is(expected));
        assertThat(listStudents.spliterator().getExactSizeIfKnown(), is(1L));
    }

    @Test
    public void deleteStudent() {
        // Setup
        Student expected = new Student("Ivan", "Ivanov");
        studentsRepository.save(expected);

        // Execute
        studentsService.deleteStudentByName("react");

        Iterable<Student> listStudents = studentsRepository.findAll();

        // Assert
        assertThat(listStudents.spliterator().getExactSizeIfKnown(), is(0L));
    }
}
