package com.database.model;

public class Submission {
    public Integer id;
    public String username;
    public Integer assignment_name;
    public Float grade;

    public Submission(Integer id, String username, Integer assignment_name, Float grade) {
        this.id = id;
        this.username = username;
        this.assignment_name = assignment_name;
        this.grade = grade;
    }

}