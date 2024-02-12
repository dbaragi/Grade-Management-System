package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ClassManagement {
     /*Creates a new class*/
    public void createClass(Connection conn, String course_number, String term, Integer section_number, String description) {
        try {
            Statement st = conn.createStatement();
            String Create_class = "Insert into Class (course_number, term, section_number, description) " +
                    "values ('" + course_number + "','" + term + "','" + section_number + "','" + description + "')";
            st.executeUpdate(Create_class);
            System.out.println("Class created");

        } catch (SQLException error) {

            System.out.println("Class already exists");

        }
    }
    /*Show details of active class*/
    public void showClass(Connection conn, Integer class_id) {
        /*Show details of active class*/
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from Class where id=" + class_id;
            ResultSet rs = st.executeQuery(sql);
            System.out.println("\n Active Courses");
            while (rs.next()) {
                System.out.println("Course Number: " + rs.getString("course_number") + " Term: " + rs.getString("term")
                        + " Section Number: " + rs.getInt("section_number") + " Description: " + rs.getString("description"));
            }
            System.out.println("Currently Active class");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     /*Lists all the available classes and shows the number of students present in them*/
    public void listClasses(Connection conn) {
        try {
            Statement st = conn.createStatement();
            String List_classes = "Select count(username) as Number_of_students, class_id from Enroll group by class_id";
            ResultSet rs = st.executeQuery(List_classes);
            while (rs.next()) {
                System.out.println("Course Id: " + rs.getInt("class_id") + " has total " + rs.getInt("Number_of_students") + " students");
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }

    }

    /*Selects a particular class and allows to perform operations on them(adding category, adding assignment, grading assignment)*/
    public Integer selectClass(Connection conn, String course_number, String term, String section_number) {
        try {
            Statement st = conn.createStatement();
            List<String> query = new ArrayList<>();
            query.add(String.format("Select * from Class where course_number = '%s'", course_number));
            if (!Objects.equals(term, "")) {
                query.add(String.format("term = '%s'", term));
            }
            if (!section_number.equals("")) {
                query.add(String.format("section_number = '%d'", Integer.valueOf(section_number)));
            }
            String Select_class = String.join(" AND ", query);
            ResultSet rs = st.executeQuery(Select_class);
            System.out.println("\n Activated Course ");
            int course_id = 0;
            int counter = 0;
            while (rs.next()) {
                counter += 1;
                System.out.println("Course Number: " + rs.getString("course_number") + " Term: " + rs.getString("term")
                        + " Section Number: " + rs.getInt("section_number") + " Description: " + rs.getString("description"));
                course_id = rs.getInt("id");
            }
            if (counter != 1) {
                System.out.println("\n More than one class selected");
                return 0;
            }
            return course_id;

        } catch (SQLException error) {
            error.printStackTrace();
        }
        return 0;
    }
}



