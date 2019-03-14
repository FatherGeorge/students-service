package com.example.studentsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public final class StudentsControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mock
    private StudentsService studentsService;

    @InjectMocks
    private StudentsController studentsController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentsController).build();
    }

    @Test
    public void smokeTest() throws Exception {
        // Execute
        String actual = mockMvc.perform(get("/appInfo"))
                .andExpect(status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Assert
        assertThat(actual, is("App is running"));
    }

    @Test
    public void getReturnsListOfStudentsStatus0() throws Exception {
        // Setup
        Student student = new Student("Ivan", "Ivanov");
        List<Student> students = new ArrayList<>();
        students.add(student);

        when(studentsService.listStudents()).thenReturn(students);
        // Execute
        String actualString = mockMvc.perform(get("/"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response actual = OBJECT_MAPPER.readValue(actualString, Response.class);

        // Assert
        assertThat(actual.getStudents().get(0), is(student));
        assertThat(actual.getStatusCode(), is("0"));
        assertThat(actual.getStatusDesc(), is("Ok"));
    }

    @Test
    public void addStudentReturnsCode2IfStudentAlreadyExists() throws Exception {
        // Setup
        Student student = new Student("Ivan", "Ivanov");

        when(studentsService.findStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(student));
        // Exercise and assert
        String actualString = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(student)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response actual = OBJECT_MAPPER.readValue(actualString, Response.class);

        // Assert
        assertThat(actual.getStatusCode(), is("2"));
        assertThat(actual.getStatusDesc(), is("Student already exists"));
    }

    @Test
    public void addStudentReturnsStatus0() throws Exception {
        // Setup
        Student student = new Student("Ivan", "Ivanov");

        when(studentsService.findStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        when(studentsService.saveStudent(student)).thenReturn(student);

        // Exercise and assert
        String actualString = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(student)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response actual = OBJECT_MAPPER.readValue(actualString, Response.class);

        // Assert
        assertThat(actual.getStatusCode(), is("0"));
        assertThat(actual.getStatusDesc(), is("Ok"));
    }

    @Test
    public void deleteStudentReturnsStatus0() throws Exception {
        // Setup
        Student student = new Student("Ivan", "Ivanov");

        when(studentsService.findStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(student));
        // Exercise and assert
        String actualString = mockMvc.perform(delete("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(student)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response actual = OBJECT_MAPPER.readValue(actualString, Response.class);

        // Assert
        assertThat(actual.getStatusCode(), is("0"));
        assertThat(actual.getStatusDesc(), is("Ok"));
    }

    @Test
    public void changeStudentCoursesReturnsStatus0() throws Exception {
        // Setup
        Student student = new Student("Ivan", "Ivanov", new Integer[]{1, 2});

        when(studentsService.findStudentByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(student));
        // Exercise and assert
        String actualString = mockMvc.perform(put("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(student)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response actual = OBJECT_MAPPER.readValue(actualString, Response.class);

        // Assert
        assertThat(actual.getStatusCode(), is("0"));
        assertThat(actual.getStatusDesc(), is("Ok"));
    }
}
