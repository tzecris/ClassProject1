package person;

import classproject.ClassProject;
import classproject.SyntheticValues;
import classproject.Validation;
import course.Course;
import exercise.Assignment;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student extends Person {

    Scanner sc = new Scanner(System.in);

    private double tuitionFees;

    private ArrayList<Course> coursesPerStudentList = new ArrayList<>();
    private ArrayList<Assignment> assignmentsPerStudentList = new ArrayList<>();
    private Date dateOfBirth;

    public Student(String firstName, String lastName, Date bd) {
        super(firstName, lastName);
        this.dateOfBirth = bd;
    }

    public Student() {
    }

    public static void addStudentToList(Scanner sc, List<Student> listOfStudents) {
        String input;
        System.out.println();
        System.out.println("1.Add new Student");
        System.out.println("2.Import from existing students");
        input = sc.nextLine();
        while (!input.equalsIgnoreCase("1") && !input.equalsIgnoreCase("2")) {
            System.out.println("Give a valid answer:");
            input = sc.nextLine();
        }
        if (input.equals("2")) {
            try {
                if (!listOfStudents.containsAll(SyntheticValues.syntheticStudents())) {
                    listOfStudents.addAll(SyntheticValues.syntheticStudents());
                }
            } catch (ParseException ex) {
                Logger.getLogger(ClassProject.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(listOfStudents);
            return;
        }
        Student s1 = new Student();
        System.out.println("Give first name:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid name:");
            input = sc.nextLine();
        }
        s1.setFirstName(input);

        System.out.println("Give last name:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid last name:");
            input = sc.nextLine();
        }
        s1.setLastName(input);

        System.out.println("Give the Birthday of student (the format must be dd/mm/yyyy)");
        input = sc.nextLine();
        while (!input.matches(Validation.DATE_PATTERN)) {
            System.out.println("Give valid Birthday:");
            input = sc.nextLine();
        }
        try {
            s1.setDateOfBirth(Validation.parseDate(input));
        } catch (ParseException ex) {
            System.out.println("Invalid date");
        }

        if (!listOfStudents.contains(s1)) {
            listOfStudents.add(s1);
        } else {
            System.out.println("Student already exist.");
        }

    }

    public void addCourse(Course course) {
        if (!this.coursesPerStudentList.contains(course)) {
            this.coursesPerStudentList.add(course);
            ArrayList<Assignment> assignments = new ArrayList<>();
            for (Assignment assignment : course.getAssignmentsPerCourseList()) {
                Assignment newAssignment = new Assignment(assignment.getTitle(), assignment.getDescription(), assignment.getSubDateTime());
                assignments.add(newAssignment);
            }
            this.assignmentsPerStudentList.addAll(assignments);

        } else {
            System.out.println("Student already has this course");
        }
    }


    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public ArrayList<Course> getCoursesPerStudentList() {
        return coursesPerStudentList;
    }

    public void setCoursesPerStudentList(ArrayList<Course> coursesPerStudentList) {
        this.coursesPerStudentList = coursesPerStudentList;
    }

    public ArrayList<Assignment> getAssignmentsPerStudentList() {
        return assignmentsPerStudentList;
    }

    public void setAssignmentsPerStudentList(ArrayList<Assignment> assignmentsPerStudentList) {
        this.assignmentsPerStudentList = assignmentsPerStudentList;
    }


    @Override
    public String toString() {
        return super.toString() + " ,date of birth " + Validation.formatDate(this.dateOfBirth) + "\n";
    }

}
