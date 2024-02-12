package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GradeManagement {
    /* shows the grade of student in selected class by inserting username*/
    public Float showGradesByUsername(Connection conn, Integer class_id, String username, Boolean print) {
        try {
            Statement st = conn.createStatement();
            String query = String.format("""
                    Select c.category_name,c.weight,
                    Sum(a.points) as total_points,\s
                    Sum(s.grade) as attempted_grade,
                    round((SUM(s.grade)/SUM(a.points)),2) as Grade
                    from Submission s\s
                    join Assignment a\s
                    on a.id = s.assignment_name\s
                    join Category c\s
                    on a.category = c.id\s
                    where c.class_id = '%d' AND\s
                    s.username = '%s'\s
                    group by c.category_name,c.weight\s""",class_id,username);

            ResultSet rs = st.executeQuery(query);
            float total_grade = 0.0F;
            while (rs.next()) {
                if (print){
                    System.out.println("Category: " + rs.getString("category_name") + " Total points: "+ rs.getInt("total_points") +
                            " Attempted Grade: " + rs.getInt("attempted_grade") + " Grade: " + rs.getFloat("Grade"));

                }

                total_grade += rs.getInt("weight") * rs.getFloat("Grade");
            }

            if (print){
                System.out.println("Total grade: " + total_grade);
            }

            return total_grade;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0F;
    }

    /*Shows the grades of all students in the selected class*/

    public void Gradebook(Connection conn,Integer class_id){
        try {
            Statement st = conn.createStatement();
            String query = String.format("""
                    Select s.username , s.student_id , s.first_name ,s.last_name\s
                    from Student s\s
                    join Enroll e\s
                    on e.username = s.username\s
                    where e.class_id = '%d'""", class_id);

            ResultSet rs = st.executeQuery(query);


            System.out.println("Grade book");

            while (rs.next()) {
                Float grade = showGradesByUsername(conn,class_id,rs.getString("username"),false);
                System.out.println("Username: " + rs.getString("username") + " Student_id: "+ rs.getInt("student_id") +
                        " First name: " + rs.getString("first_name") + " Last name: " + rs.getString("last_name") + " Grade: " + grade);

            }


        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}