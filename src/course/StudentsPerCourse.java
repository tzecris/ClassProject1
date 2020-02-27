package course;

import classproject.ClassProject;
import classproject.SyntheticValues;
import classproject.Validation;
import database.ConnectionToDatabase;
import static database.ConnectionToDatabase.con;
import static database.ConnectionToDatabase.st;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import person.Student;
import person.Trainer;

public class StudentsPerCourse {

    private List<Student> studentsPerCourseList = new ArrayList<>(20);

    public static void addStudentToCourse(Scanner sc, List<Student> listOfStudents, List<Course> listOfCourses) {
        String input;
        Student tempStudent;

        System.out.println("Type a number to select the student");
        try {
            if (listOfStudents.isEmpty()) {
                listOfStudents.addAll(SyntheticValues.syntheticStudents());

            }

        } catch (ParseException ex) {
            Logger.getLogger(ClassProject.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < listOfStudents.size(); i++) {
            System.out.println(i + 1 + "." + listOfStudents.get(i));
        }

        input = sc.nextLine();
        int numberOfStudents = listOfStudents.size();
        while (!input.matches(Validation.NUMBERS_ONLY) || Integer.parseInt(input) > numberOfStudents) {
            System.out.println("Give valid number");
            input = sc.nextLine();

        }

        tempStudent = listOfStudents.get(Integer.parseInt(input) - 1);

        System.out.println("");

        System.out.println("Type a number to select the course to add the student");

        try {
            if (listOfCourses.isEmpty()) {
                listOfCourses.addAll(SyntheticValues.syntheticCourses());
            }
        } catch (ParseException ex) {
            Logger.getLogger(ClassProject.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < listOfCourses.size(); i++) {
            System.out.println(i + 1 + "." + listOfCourses.get(i));
        }
        input = sc.nextLine();
        int numberOfCourses = listOfCourses.size();
        while (!input.matches(Validation.NUMBERS_ONLY) || Integer.parseInt(input) > numberOfCourses) {
            System.out.println("Give valid number.");
            input = sc.nextLine();
        }
        listOfCourses.get(Integer.parseInt(input) - 1).addStudent(tempStudent);
        tempStudent.addCourse(listOfCourses.get(Integer.parseInt(input) - 1));

    }

    public static void addStudentToCourse(Scanner sc) throws SQLException {
        st = con.createStatement();
        String input = null;

        System.out.println("Type a number to select the student you want");
        ResultSet result = st.executeQuery("select count(PERSON_STUDENT_ID) from student;");
        result.next();
        if (result.getInt(1) == 0) {
            ConnectionToDatabase.insertSyntheticStudent();
        }
        result = st.executeQuery("select* from student inner join person on student.PERSON_STUDENT_ID = person.PERSON_ID;");

        List<Integer> studnetIds = new ArrayList<>();
        while (result.next()) {

            Student s = new Student();

            s.setId(result.getInt("PERSON_ID"));
            s.setFirstName(result.getString("FIRST_NAME"));
            s.setLastName(result.getString("LAST_NAME"));
            s.setTuitionFees(result.getInt("TUITION_FEES"));
            s.setDateOfBirth(result.getDate("DATE_OF_BIRTH"));
            studnetIds.add(result.getInt("PERSON_ID"));

            System.out.println(s);
        }

        input = sc.nextLine();

        while (!input.matches(Validation.NUMBERS_ONLY) || !studnetIds.contains(Integer.parseInt(input))) {
            System.out.println("Give valid number.");
            input = sc.nextLine();
        }

        int student_id = Integer.parseInt(input);

        System.out.println("");

        System.out.println("Type a number to select the course to add the student");

        result = st.executeQuery("select count(course_ID) from course;");
        result.next();

        if (result.getInt(1) == 0) {
            ConnectionToDatabase.insertSyntheticCourse();
        }
        result = st.executeQuery("select * from course c\n"
                + "                where course_id not in(select cp.course_id "
                + "from course_per_student cp where cp.student_id=" + student_id + " );");

        List<Integer> courseIds = new ArrayList<>();
        while (result.next()) {
            Course c = new Course();

            c.setId(result.getInt(1));
            c.setTitle(result.getString(2));
            c.setStream(result.getString(3));
            c.setType(result.getString(4));
            c.setStartDate(result.getDate(5));
            c.setEndDate(result.getDate(6));
            courseIds.add(result.getInt(1));
            System.out.println(c);
        }

        if (courseIds.isEmpty()) {
            System.out.println("There are no available courses for this student.");
            return;
        }

        input = sc.nextLine();

        while (!input.matches(Validation.NUMBERS_ONLY) || !courseIds.contains(Integer.parseInt(input))) {
            System.out.println("Give valid number.");
            input = sc.nextLine();

        }
        int course_id = Integer.parseInt(input);

        int rowsAffected = st.executeUpdate("INSERT INTO `COURSE_per_student` (`COURSE_ID`, `STUDENT_ID`) VALUES ('" + course_id + "', '" + student_id + "');");

        if (rowsAffected == 2) {
            System.out.println("Insert has not been Succeed");

        } else {
            System.out.println("Course has been inserted");

        }

    }




    public List<Student> getStudentsPerCourseList() {
        return studentsPerCourseList;
    }

    public void setStudentsPerCourseList(List<Student> studentsPerCourseList) {
        this.studentsPerCourseList = studentsPerCourseList;
    }

    public void addStudent(Student student) {
        if (!studentsPerCourseList.contains(student) && studentsPerCourseList.size() < 20) {
            this.studentsPerCourseList.add(student);
            System.out.println("Add succesfull");
        } else {
            System.out.println("Unable to add student.");
        }
    }



}
