package com.database;

import com.database.model.Assignment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CategoryAndAssignmentManagement {
    /* Create a new category*/
    public void createCategory(Connection conn, Integer class_id, String category_name, Integer weight) {
        try {

            Statement st = conn.createStatement();
            String Create_category = "Insert into Category (class_id,category_name, weight) " +
                    "Values ('" + class_id + "', '" + category_name + "', '" + weight + "')";
            st.executeUpdate(Create_category);
            System.out.println("\n Category Created \n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*Shows categories list that are present*/
    public void showCategory(Connection conn, Integer class_id) {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from Category where class_id=" + class_id;
            ResultSet rs = st.executeQuery(sql);
            System.out.println("\n Categories");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " Name: " + rs.getString("category_name") + " Weight: " + rs.getString("weight"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*Creates a new assignment in particular category*/
    public void createAssignment(Connection conn, Integer class_id, String name, Integer category, Float points) {
        try {
            if (class_id != 0) {
                Statement st = conn.createStatement();
                String Create_assignment = "Insert into Assignment ( class_id,name,category,points) " +
                        "values (" + class_id + ",'" + name + "','" + category + "','" + points + "')";
                st.executeUpdate(Create_assignment);
                System.out.println("\n Assignment Created \n");
            } else {
                System.out.println("Please activate a class");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*shows list of assignments present*/
    public void showAssignment(Connection conn, Integer class_id) {
        try {

            Statement st = conn.createStatement();
            String query = String.format("""

                    Select a.id, a.name,a.points, c.category_name from Assignment a
                    join Category c\s
                    on c.id = a.category\s
                    where c.class_id = '%d'""", class_id);

            ResultSet rs = st.executeQuery(query);

            System.out.println("Assignments");

            List<Assignment> assignmentList = new ArrayList<>();
            while (rs.next()) {
                Assignment assignment = new Assignment(rs.getInt("id"), rs.getString("name"), rs.getString("category_name"), rs.getFloat("points"));
                assignmentList.add(assignment);
            }
            Map<String, List<Assignment>> groupedByCategory = assignmentList.stream()
                    .collect(Collectors.groupingBy(Assignment::getCategory));
            for (Map.Entry<String, List<Assignment>> entry : groupedByCategory.entrySet()) {
                System.out.println("Category: " + entry.getKey());
                entry.getValue().forEach(assignment -> System.out.println("id: " + assignment.getId() + " Name: " + assignment.getName() + " Points:" + assignment.getPoints()));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


