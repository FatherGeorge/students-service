package com.example.studentsservice;

import java.util.List;

public class Response {
    private String statusCode;
    private String statusDesc;
    private List<Student> students;
    private Student student;

    public Response setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Response setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
        return this;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public Response setStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Response setStudent(Student student) {
        this.student = student;
        return this;
    }

    public Student getStudent() {
        return student;
    }
}
