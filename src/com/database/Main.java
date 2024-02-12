package com.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static Connection makeConnection() {
        try {
            // Onyx
            String database_url = "jdbc:mysql://localhost:51171/GradeManagement?verifyServerCertificate=false&useSSL=true";
            String username = "msandbox";
            String password = "*********99";

            Connection conn = DriverManager.getConnection(database_url, username, password);
            System.out.println("Database connected!");
            System.out.println();
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            // The newInstance() call is a workaround for some broken Java implementations
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println();
            System.out.println("JDBC driver loaded");
            System.out.println();

            // Create connections
            Connection conn = makeConnection();

            // Create table
            CreateTable createTable = new CreateTable();
            createTable.createTables(conn);

            // Create class management object
            ClassManagement classManagement = new ClassManagement();
            CategoryAndAssignmentManagement categoryAndassignmentManagement = new CategoryAndAssignmentManagement();
            StudentManagement studentManagement = new StudentManagement();
            GradeManagement gradeManagement = new GradeManagement();

            // Show menu
            Menu menu = new Menu();
            Integer activeClass_id = 0;
            try {
                Integer choice = 0;
                Scanner scan = new Scanner(System.in);
                while (choice != 5) {
                    menu.mainMenu();
                    choice = scan.nextInt();
                    if (choice != 1 && activeClass_id == 0) {
                        System.out.println("Please activate your class ");
                    } else {
                        System.out.println("\n Active Class Id: " + activeClass_id);
                        switch (choice) {
                            case 1:
                                activeClass_id = menu.ClassManagement(conn, classManagement, activeClass_id);
                                break;
                            case 2:
                                menu.CategoryAndAssignmentManagement(conn,categoryAndassignmentManagement,activeClass_id);
                                break;
                            case 3:
                                menu.StudentManagement(conn,studentManagement,activeClass_id);
                                break;
                            case 4:
                                menu.GradeManagement(conn, gradeManagement,activeClass_id);
                                break;
                            case 5:
                                System.out.println("Database Connection Closed");
                                conn.close();
                                break;
                            default:
                                System.out.println(" Invalid option. Exiting the shell. \n");
                                break;
                        }
                    }
                }
                scan.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Run Query
            conn.close();
            System.out.println();
            System.out.println("Database connection closed");
            System.out.println();
        } catch (Exception error) {
            // handle the error
            System.err.println(error);
        }
    }
}
