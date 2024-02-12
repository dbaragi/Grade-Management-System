package com.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTable {


    private static final String CLASS_TABLE = """
            CREATE TABLE IF NOT EXISTS Class (
                id INT AUTO_INCREMENT PRIMARY KEY,
                course_number VARCHAR(255),
                term VARCHAR(255) NOT NULL,
                section_number INT NOT NULL,
                description TEXT
            )""";


    private static final String CATEGORY_TABLE = """
            CREATE TABLE IF NOT EXISTS Category (
                id INT AUTO_INCREMENT PRIMARY KEY,
                category_name VARCHAR(255) NOT NULL,
                class_id INT NOT NULL REFERENCES Class(id),
                weight INT NOT NULL   \s
            )""";

    private static final String ASSIGNMENT_TABLE = """
            CREATE TABLE IF NOT EXISTS Assignment (
            \tid INT AUTO_INCREMENT PRIMARY KEY,
                class_id INT NOT NULL REFERENCES Class(id),
                name VARCHAR(255) NOT NULL,
            \tcategory INT NOT NULL REFERENCES Category(id),
                description TEXT,
                points FLOAT NOT NULL
            )""";

    private static final String STUDENT_TABLE = """
            CREATE TABLE IF NOT EXISTS Student (
            student_id INT UNIQUE PRIMARY KEY,
            username VARCHAR(255) NOT NULL UNIQUE,
            first_name VARCHAR(255) NOT NULL,
            last_name VARCHAR(255) NOT NULL
            )""";

    private static final String ENROLL_TABLE = """
            CREATE TABLE IF NOT EXISTS Enroll (
            \tusername VARCHAR(255) NOT NULL REFERENCES Student(username),
            \tclass_id INT NOT NULL REFERENCES Class(id)
            )""";

    private static final String SUBMISSION_TABLE = """
            CREATE TABLE IF NOT EXISTS Submission (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(255) NOT NULL REFERENCES Student(username),
                assignment_name INT NOT NULL REFERENCES Assignment(id),
                grade FLOAT
            )""";


    public static void createTables(Connection conn) {
        Statement s = null;

        try {

            s = conn.createStatement();
            s.executeUpdate(CLASS_TABLE);
            s.executeUpdate(CATEGORY_TABLE);
            s.executeUpdate(ASSIGNMENT_TABLE);
            s.executeUpdate(STUDENT_TABLE);
            s.executeUpdate(ENROLL_TABLE);
            s.executeUpdate(SUBMISSION_TABLE);

        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
    }
}
