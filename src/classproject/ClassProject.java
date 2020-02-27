package classproject;

import course.Course;
import course.CoursePerTrainer;
import course.StudentsPerCourse;
import database.ConnectionToDatabase;
import static database.ConnectionToDatabase.con;
import static database.ConnectionToDatabase.st;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import person.Student;
import person.Trainer;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import person.Person;

public class ClassProject {

    public static void main(String[] args) throws SQLException, ParseException {
        ConnectionToDatabase.createConnection("root", "admin");
        String input = null;
        String username, password;
        List<Student> listOfStudents = new ArrayList<>();
        List<Course> listOfCourses = new ArrayList<>();
        List<Trainer> listOfTrainers = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        System.out.println("Give your username:");
        username = sc.nextLine();
        System.out.println("Give your password:");
        password = sc.nextLine();

        ConnectionToDatabase.createConnection(username, password);


        System.out.println("If you want to add data type 'YES', or 'NO' to continue with default:");
        input = sc.nextLine();
        System.out.println(" ");
        while (!input.equalsIgnoreCase("YES") && !input.equalsIgnoreCase("NO")) {
            System.out.println("Give a valid answer:");
            input = sc.nextLine();
        }
        if (checkInput(input)) {

            while (!("exit").equalsIgnoreCase(input)) {

                printMenu();
                input = sc.nextLine();
                switch (input) {
                    case "1":
//                        Course.addCourseToList(sc, listOfCourses);
                        ConnectionToDatabase.addCourse(sc);
                        break;
                    case "2":
//                        Trainer.addTrainerToList(sc, listOfTrainers);
                        ConnectionToDatabase.addTrainer(sc);
                        break;
                    case "3":
                        CoursePerTrainer.addTrainerToCourse(sc);
                        break;
                    case "4":
//                        Student.addStudentToList(sc, listOfStudents);
                        ConnectionToDatabase.addStudent(sc);
                        break;
                    case "5":
                        StudentsPerCourse.addStudentToCourse(sc);
                        break;
                    case "6":
                        mainMenu(sc, listOfCourses, listOfTrainers, listOfStudents);
                        input = "exit";
                        break;
                    case "exit":
                        break;
                    default:
                        System.out.println("Invalid number");
                }

            }
        } else {
            ConnectionToDatabase.insertSyntheticCourse();
            ConnectionToDatabase.insertSyntheticStudent();
            ConnectionToDatabase.insertSyntheticTrainer();
            SyntheticValues.combineSyntheticValues();
            mainMenu(sc, listOfCourses, listOfTrainers, listOfStudents);

        }

    }

    public static boolean checkInput(String s) {

        return s.equalsIgnoreCase("YES");
    }

    public static void printMenu() {
        System.out.println("How do you want to proceed? Type number");
        System.out.println("1. Add Course.");
        System.out.println("2. Add Trainer.");
        System.out.println("3. Add Trainer to course.");
        System.out.println("4. Add Student.");
        System.out.println("5. Add Student to Course.");
        System.out.println("6. Enter main menu.");
        System.out.println("Type 'exit' to end program");

    }

    public static void mainMenu(Scanner sc, List<Course> listOfCourses, List<Trainer> listOfTrainers, List<Student> listOfStudents) throws SQLException {
        String input = null;


        while (!("exit").equalsIgnoreCase(input)) {
            System.out.println("1. Show all Courses");
            System.out.println("2. Show all Trainers");
            System.out.println("3. Show all Students");
            System.out.println("4. Show all Assigmnents");
            System.out.println("5. Show all Trainers per Courses");
            System.out.println("6. Show all Students per Courses");
            System.out.println("7. Show all Assignments per Course");
            System.out.println("8. Show all Assignments per Student");
            System.out.println("9. Show all students that belong to more than one courses");
            System.out.println("10. Show all Assignments per Students per Course.");
            System.out.println("Type 'exit' to end program");
            input = sc.nextLine();
        switch (input) {
            case "1":
                ShowElements.showCourses();
//                System.out.println(listOfCourses.isEmpty() ? "There are no courses available" : listOfCourses);
                break;
            case "2":
                ShowElements.showTrainers();
//                System.out.println(listOfTrainers.isEmpty() ? "There are no trainers available" : listOfTrainers);
                break;
            case "3":
                ShowElements.showStudents();
//                System.out.println(listOfStudents.isEmpty() ? "There are no students available" : listOfStudents);
                break;
            case "4":
                ShowElements.showAssignments();
//                List<Assignment> all = new ArrayList<>();
//                for (Course c : listOfCourses) {
//                    all.addAll(c.getAssignmentsPerCourseList());
//                }
//                System.out.println(all.isEmpty() ? "There are no assignments available" : all);
                break;
            case "5":
//                if (listOfCourses.isEmpty()) {
//                    System.out.println("There are no courses available");
//                }
//
//                for (Course c : listOfCourses) {
//                    System.out.println(c.getStream() + " " + c.getType());
//                    System.out.println(c.getTrainersPerCourseList());
//                }
                ShowElements.showTrainersPerCourse();
                break;
            case "6":
//                if (listOfCourses.isEmpty()) {
//                    System.out.println("There are no courses available");
//                }
//                for (Course c : listOfCourses) {
//                    System.out.println(c.getStream() + " " + c.getType());
//                    System.out.println(c.getStudentsPerCourseList());
//                }

                ShowElements.showStudentsPerCourse2();
                break;
            case "7":
//                if (listOfCourses.isEmpty()) {
//                    System.out.println("There are no courses available");
//                }
//                for (Course c : listOfCourses) {
//                    System.out.println(c.getStream() + " " + c.getType());
//                    System.out.println(c.getAssignmentsPerCourseList());
//                }
                ShowElements.showAssignmentsPerCourse();
                break;
            case "8":
                if (listOfStudents.isEmpty()) {
                    System.out.println("There are no students available");
                }
                for (Student s : listOfStudents) {
                    System.out.println(s.getLastName() + " " + s.getFirstName());
                    System.out.println(s.getAssignmentsPerStudentList());
                }
                break;
            case "9":
//                if (listOfStudents.isEmpty()) {
//                    System.out.println("There are no students available");
//                }
//                for (Student s : listOfStudents) {
//                    if (s.getCoursesPerStudentList().size() > 1) {
//                        System.out.println(s);
//                    }
//                }
                ShowElements.showStudentsToMoreThanOneCourse();
                break;
            case "10":
                ShowElements.showAssignmentsPerCoursePerStudent();
                break;
            case "exit":
                break;
            default:
                System.out.println("Invalid number");
                break;

            }

        }

    }

    public static void checkAssignmentDate(Date date) throws SQLException {
        st = con.createStatement();
        Date sub;
        List<Integer> assignmentL = new ArrayList<>();
        ResultSet result = st.executeQuery("select * from assignment a where a.assignment_id in "
                + "(select distinct sa.assignment_id from student_per_assignment sa);");
        while (result.next()) {

            assignmentL.add(result.getInt(1));
        }
        result.close();

//        List<Student> studentsWithAssignment = new ArrayList<>();
        for (Integer s : assignmentL) {
            result = st.executeQuery("select* from exercise where EXERCISE_ID=" + s + ";");

            while (result.next()) {
                sub = result.getDate("SUB_DATE_TIME");
                if (isDateInCurrentWeek(date, sub)) {
                    result = st.executeQuery("select* from person p where p.person_id in "
                            + "(select s.person_student_id from student s where s.person_student_id in "
                            + "(select sa.student_id from student_per_assignment sa where sa.ASSIGNMENT_ID=1));");
                    while (result.next()) {

                        Person p = new Person();

                        p.setId(result.getInt("PERSON_ID"));
                        p.setFirstName(result.getString("FIRST_NAME"));
                        p.setLastName(result.getString("LAST_NAME"));

                        System.out.println(p);
                    }
                    result.close();
//                    System.out.println("");

                }
            }
            result.close();
//            for (Student s : students) {
//                for (Assignment assignment : s.getAssignmentsPerStudentList()) {
//                    if (isDateInCurrentWeek(date, assignment.getSubDateTime())) {
//                        studentsWithAssignment.add(s);
//                    }
//                }
//            }
        }

    }

    public static boolean isDateInCurrentWeek(Date givenDate, Date submissionDate) {
        Calendar givenCalendar = Calendar.getInstance();
        givenCalendar.setTime(givenDate);
        int givenWeek = givenCalendar.get(Calendar.WEEK_OF_YEAR);
        int givenYear = givenCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(submissionDate);
        int submissionWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int submissionYear = targetCalendar.get(Calendar.YEAR);
        return givenWeek == submissionWeek && givenYear == submissionYear;
    }

}
