package com.database.model;

public class Student {
    public String username;
    public Integer student_id;
    public String first_name;
    public String last_name;

    public Student(String username, Integer student_id, String first_name, String last_name) {
        this.username = username;
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Integer getStudentId() {
        return student_id;
    }
    public String getFirstName() {
        return first_name;
    }
    public String getLastName() {

        return last_name;
    }
}