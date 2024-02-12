package com.database;

import java.sql.Connection;
import java.util.Scanner;

public class Menu {

    public static void mainMenu() {
        /*Show main menu*/
        System.out.println(" What do you want to do? \n");
        System.out.println("1. Class Management \n");
        System.out.println("2. Category and Assignment Management \n");
        System.out.println("3. Student Management \n");
        System.out.println("4. Grade Management \n");
        System.out.println("5. Exit \n");
    }

    public static Integer ClassManagement(Connection conn, ClassManagement classManagement, Integer activeClass_id) {
        /* accept commands for class management */

        System.out.println("""
                Class Management Command lists\s
                ● Create a class: new-class CS410 Sp20 1 "Databases"
                ● List classes, with the # of students in each: list-classes
                ● Activate a class:
                   o select-class CS410 selects the only section of CS410 in the most recent term, if there is only one such section; if there are multiple sections it fails.
                   o select-class CS410 Sp20 selects the only section of CS410 in Fall 2018; if there are multiple such sections, it fails.
                   o select-class CS410 Sp20 1 selects a specific section
                ● show-class shows the currently-active class""");
        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        command = command.trim();
        String[] parameters = command.split(" ");
        if (parameters.length == 0) {
            return 0;
        }

        switch (parameters[0]) {
            case "new-class" -> {
                parameters = command.split(" ", 5);
                classManagement.createClass(conn, parameters[1], parameters[2], Integer.valueOf(parameters[3]), parameters[4]);
                return 0;
            }
            case "list-classes" -> {
                classManagement.listClasses(conn);
                return 0;
            }
            case "select-class" -> {
                parameters = command.split(" ", 4);
                if (parameters.length == 2) {
                    return classManagement.selectClass(conn, parameters[1], "", "");
                } else if (parameters.length == 3) {
                    return classManagement.selectClass(conn, parameters[1], parameters[2], "");
                } else if (parameters.length == 4) {
                    return classManagement.selectClass(conn, parameters[1], parameters[2], parameters[3]);
                }
                return 0;
            }
            case "show-class" -> {
                classManagement.showClass(conn, activeClass_id);
                return activeClass_id;
            }
            default -> {
                System.out.println(" Invalid option. Exiting the shell. \n");
                return 0;
            }
        }


    }

    public static void CategoryAndAssignmentManagement(Connection conn, CategoryAndAssignmentManagement categoryAndassignmentManagement, Integer activeClass_id) {
        /* accept commands for category management */
        if (activeClass_id !=0) {
            System.out.println("""
                     Category and Assignment Management Command List
                    ● show-categories – list the categories with their weights
                    ● add-category Name weight – add a new category
                    ● show-assignment – list the assignments with their point values, grouped by category
                    ● add-assignment name Category points – add a new assignment""");
            Scanner scan = new Scanner(System.in);
            String command = scan.nextLine();
            command = command.trim();
            String[] parameters = command.split(" ");
            if (parameters.length == 0) {
                System.out.println("Invalid command!");
            }

            switch (parameters[0]) {
                case "add-category" -> {
                    parameters = command.split(" ", 3);
                    categoryAndassignmentManagement.createCategory(conn, activeClass_id, parameters[1], Integer.valueOf(parameters[2]));
                }
                case "show-categories" -> categoryAndassignmentManagement.showCategory(conn, activeClass_id);
                case "add-assignment" -> {
                    parameters = command.split(" ", 5);
                    categoryAndassignmentManagement.createAssignment(conn, activeClass_id, parameters[1], Integer.valueOf(parameters[2]), Float.valueOf(parameters[3]));
                }
                case "show-assignment" -> categoryAndassignmentManagement.showAssignment(conn, activeClass_id);
                default -> System.out.println(" Invalid option. Exiting the shell. \n");
            }
        } else {
            System.out.println("Please activate the class first");
        }

    }


    public static void StudentManagement(Connection conn, StudentManagement studentManagement, Integer activeClass_id) {
        /* accept commands for student management */

        System.out.println("""
                Student Management Commands List
                ● add-student username studentid Last First — adds a student and enrolls
                them in the current class. If the student already exists, enroll them in the class; if the
                name provided does not match their stored name, update the name but print a warning
                that the name is being changed.
                ● add-student username — enrolls an already-existing student in the current class. If
                the specified student does not exist, report an error.
                ● show-students – show all students in the current class
                ● show-students string – show all students with ‘string’ in their name or username
                (case-insensitive)
                ● grade assignmentname username grade – assign the grade ‘grade’ for student
                with user name ‘username’ for assignment ‘assignmentname’. If the student already has a
                grade for that assignment, replace it. If the number of points exceeds the number of
                points configured for the assignment, print a warning (showing the number of points
                configured)""");
        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        command = command.trim();
        String[] parameters = command.split(" ");
        if (parameters.length == 0) {
            System.out.println("Invalid command!");
        }

        switch (parameters[0]) {
            case "show-students" -> {
                parameters = command.split(" ", 2);
                if (parameters.length == 1) {
                    studentManagement.showStudents(conn, activeClass_id);

                } else if (parameters.length == 2) {
                    studentManagement.showStudentName(conn, parameters[1], activeClass_id);
                }
            }
            case "grade" -> {
                parameters = command.split(" ", 4);
                studentManagement.gradeAssignment(conn, activeClass_id, Integer.valueOf(parameters[1]), parameters[2], Float.valueOf(parameters[3]));
            }
            case "add-student" -> {
                parameters = command.split(" ", 5);
                if (parameters.length == 2) {
                    studentManagement.enrollByUsername(conn, activeClass_id, parameters[1]);
                } else if (parameters.length == 5) {
                    studentManagement.addStudent(conn, activeClass_id, parameters[1], Integer.valueOf(parameters[2]), parameters[3], parameters[4]);
                }
            }
            default -> System.out.println(" Invalid option. Exiting the shell. \n");
        }


    }

    public static void GradeManagement(Connection conn, GradeManagement gradeManagement, Integer activeClass_id) {
        /* accept commands for grade management */

        System.out.println("""
                ● student-grades username – show student’s current grade: all assignments, visually
                grouped by category, with the student’s grade (if they have one). Show subtotals for each
                category, along with the overall grade in the class.
                ● gradebook – show the current class’s gradebook: students (username, student ID, and
                name), along with their total grades in the class.""");
        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        command = command.trim();
        String[] parameters = command.split(" ");
        if (parameters.length == 0) {
            System.out.println("Invalid command!");
        }

        switch (parameters[0]) {
            case "student-grades" -> {
                parameters = command.split(" ", 2);
                gradeManagement.showGradesByUsername(conn, activeClass_id, parameters[1], true);
            }
            case "gradebook" -> gradeManagement.Gradebook(conn, activeClass_id);
            default -> System.out.println(" Invalid option. Exiting the shell. \n");
        }


    }
}