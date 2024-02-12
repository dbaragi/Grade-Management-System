package com.database;

import com.database.model.Submission;
import com.database.model.Student;
import com.database.model.Assignment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Validator {

    public Student checkIfUserExists(Connection conn, String username){
        try{
            Statement st = conn.createStatement();

            String QUERY = String.format("Select * from Student s" +
                    " where s.username = '%s'", username);
            ResultSet rs = st.executeQuery(QUERY);
            while (rs.next()) {
                Student student = new Student(rs.getString("username"), rs.getInt("student_id")
                        , rs.getString("first_name"), rs.getString("last_name"));
                return student;
            }

        } catch (SQLException error){
            error.printStackTrace();

        }
        return null;
    }

    public Submission getSubmission(Connection conn, String username, Integer assignment_name) {
        try {
            Statement st = conn.createStatement();
            String query = String.format("Select * from Submission where username= '%s' and assignment_name='%d'",username, assignment_name);
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Submission sub = new Submission(rs.getInt("id"), rs.getString("username"),
                        rs.getInt("assignment_name"), rs.getFloat("grade"));
                return sub;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean isValidGrade(Connection conn, Integer assignment_name, Float grade) {
        Float totalPoints = getTotalPoints(conn, assignment_name);
        if (totalPoints == 0.0f) {
            System.out.println("Assignment not found");
            return false;
        } else {
            if (grade > totalPoints) {
                System.out.println("Grade greater than total points");
                return false;
            }
        }
        return true;
    }

    public Float getTotalPoints(Connection conn, Integer assignment_name) {
        try {
            Statement st = conn.createStatement();
            String query = String.format("Select * from Assignment where id= '%d' ",assignment_name);
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Assignment assignment = new Assignment(rs.getInt("id"),rs.getString("name"), rs.getString("category"),rs.getFloat("points"));
                return assignment.getPoints();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }
}