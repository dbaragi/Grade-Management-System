package com.database;

import com.database.model.Submission;
import com.database.model.Student;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class StudentManagement {
    /* adds new student to the class*/
    public void addStudent(Connection conn,Integer classId,String username,Integer student_id, String first_name, String last_name){
        try {
            Validator validator = new Validator();
            Student student = validator.checkIfUserExists(conn,username);
            Statement st = conn.createStatement();
            if (student == null) {
                String query = "Insert into Student (student_id,username, last_name, first_name) " +
                        "values ('" + student_id + "', '" + username + "','" + first_name + "','" + last_name + "')";
                st.executeUpdate(query);
                System.out.println("\n Student Created \n");
            }else {
                student_id = student.getStudentId();
                if (student.getFirstName() == first_name) {
                    System.out.println("Updating first name");
                }
                if (student.getLastName() == last_name) {
                    System.out.println("Updating last name");
                }
                String sql = "Update  Student set first_name='" + first_name +
                        "',last_name='" + last_name + "' WHERE username=" + username;
                System.out.println("\n Student Updated Successfully! \n");
                st.executeUpdate(sql);
            }
            enrollStudent(conn,username,classId);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /*Shows students names that are present in class by accepting a part of the name*/
    public void showStudentName(Connection conn, String string_data, Integer classId){
        try {
            Statement st = conn.createStatement();
            String data = "%" + string_data  + "%";
            String query = String.format("Select s.username , s.first_name \n" +
                    "from Student s\n" +
                    "join Enroll e\n" +
                    "on s.username = e.username \n" +
                    "where s.username LIKE '%s'  \n" +
                    "or s.first_name LIKE '%s'\n" +
                    "and e.class_id = 1", data, data, classId);

            ResultSet rs = st.executeQuery(query);
            System.out.println();
            while (rs.next()) {
                System.out.println(" Username: " + rs.getString("username") + " first_name: " + rs.getString("first_name"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /*Shows the name of students in the class*/
    public void showStudents(Connection conn, Integer classId){
        try {
            Statement st = conn.createStatement();
            String query = String.format("Select s.username\n" +
                    "from Student s\n" +
                    "join Enroll e \n" +
                    "on s.username = e.username \n" +
                    "where e.class_id = '%d'\n", classId);
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n All Students \n");

            while (rs.next()) {
                System.out.println(" Username: " + rs.getString("username"));

            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }


    }


    /*Enrolls the students that are already present to newly added class*/
    public void enrollStudent(Connection conn, String username, Integer class_id){
        try {
            Statement st = conn.createStatement();
            String query = "Insert into Enroll (username, class_id) " +
                    "values ('"+ username +"','"+ class_id +"')";
            st.executeUpdate(query);
            System.out.println("\n Student Enrolled Successfully! \n");
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void enrollByUsername(Connection conn,Integer classId,String username){
        try {
            Validator validator = new Validator();
            Student student = validator.checkIfUserExists(conn,username);
            if (student == null){
                System.out.println("Student does not exist");
            }else{
                enrollStudent(conn,username,classId);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void gradeAssignment(Connection conn,Integer classId, Integer assignment_name, String username, Float grade) {
        try {
            Statement st = conn.createStatement();
            Validator validator = new Validator();

            if (validator.isValidGrade(conn, assignment_name, grade)) {
                Submission sub = validator.getSubmission(conn, username, assignment_name);
                String query;
                if(sub != null ){
                    System.out.println("Updating Grade");
                    query = String.format("Update  Submission set grade= '%f' where username= '%s' and assignment_name= '%d",grade,username,assignment_name);
                } else{
                    System.out.println("Inserting grade");
                    query = "Insert into Submission (username, assignment_name, grade) " +
                            "values ('" + username + "','" + assignment_name + "'," + grade + ")";
                }
                st.executeUpdate(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}