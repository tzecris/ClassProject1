package classproject;

import course.Course;
import static database.ConnectionToDatabase.con;
import exercise.Assignment;
import exercise.Exercise;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import person.Person;
import person.Student;
import person.Trainer;

public class ShowElements {

    public static void showStudents() throws SQLException {
        Statement st = con.createStatement();
        ResultSet result = st.executeQuery("select* from student inner join person on "
                + "student.PERSON_STUDENT_ID = person.PERSON_ID;");

        while (result.next()) {

            Student s = new Student();

            s.setId(result.getInt("PERSON_ID"));
            s.setFirstName(result.getString("FIRST_NAME"));
            s.setLastName(result.getString("LAST_NAME"));
            s.setDateOfBirth(result.getDate("DATE_OF_BIRTH"));
            s.setTuitionFees((int) result.getDouble("TUITION_FEES"));

            System.out.println(s);
        }
        result.close();
    }

    public static void showCourses() throws SQLException {

        Statement st = con.createStatement();

        ResultSet result = st.executeQuery("select * from course;");

        while (result.next()) {
            Course c = new Course();

            c.setId(result.getInt(1));
            c.setTitle(result.getString(2));
            c.setStream(result.getString(3));
            c.setType(result.getString(4));
            c.setStartDate(result.getDate(5));
            c.setEndDate(result.getDate(6));
            System.out.println(c);
        }
        result.close();
    }

    public static void showTrainers() throws SQLException {
        Statement st = con.createStatement();

        ResultSet result = st.executeQuery("select* from trainer inner join person on "
                + "trainer.PERSON_trainer_ID = person.PERSON_ID;");

        while (result.next()) {

            Trainer t = new Trainer();

            t.setId(result.getInt("PERSON_ID"));
            t.setFirstName(result.getString("FIRST_NAME"));
            t.setLastName(result.getString("LAST_NAME"));
            t.setSubject(result.getString("SUBJECT"));

            System.out.println(t);
        }
        result.close();
    }
    public static void showAssignments() throws SQLException {
        Statement st = con.createStatement();

        ResultSet result = st.executeQuery("select* from assignment inner join exercise on "
                + "assignment.ASSIGNMENT_EXERCISE_ID = exercise.exercise_ID;");

        while (result.next()) {

            Assignment a = new Assignment();

            a.setId(result.getInt("ASSIGNMENT_EXERCISE_ID"));
            a.setDescription(result.getString("DESCRIPTION"));
            a.setSubDateTime(result.getDate("SUB_DATE_TIME"));

            System.out.println(a);
        }
        result.close();
    }

    public static void showStudentsPerCourse2() throws SQLException {
        Statement st = con.createStatement();
        ArrayList<Person> arrSt = new ArrayList<>();
        ArrayList<Course> arrCour = new ArrayList<>();
        ResultSet result = st.executeQuery("select count(course_id) from course;");
        result.next();
        int numOfCourses = result.getInt(1);
        result = st.executeQuery("select* from course;");
        while (result.next()) {
            Course c = new Course();
            c.setId(result.getInt(1));
            c.setTitle(result.getString(2));
            c.setStream(result.getString(3));
            c.setType(result.getString(4));
            c.setStartDate(result.getDate(5));
            c.setEndDate(result.getDate(6));
            arrCour.add(c);
        }
        for (int i = 0; i < numOfCourses; i++) {

            result = st.executeQuery("SELECT person_id,first_name,last_name "
                    + "FROM person INNER JOIN course_per_student ON person.PERSON_ID = course_per_student.STUDENT_ID "
                    + "INNER JOIN course ON course.COURSE_ID = course_per_student.COURSE_ID where course.course_ID = " + arrCour.get(i).getId() + ";");
            arrSt.clear();
        while (result.next()) {

            Person s = new Person();
            s.setId(result.getInt(1));
            s.setFirstName(result.getString(2));
            s.setLastName(result.getString(3));

            arrSt.add(s);
            }
            System.out.println(arrCour.get(i));
            System.out.println(arrSt.toString());
        }

    }

    public static void showTrainersPerCourse() throws SQLException {
        Statement st = con.createStatement();


        ArrayList<Person> arrSt = new ArrayList<>();
        ArrayList<Course> arrCour = new ArrayList<>();
        ResultSet result = st.executeQuery("select count(course_id) from course;");
        result.next();
        int numOfCourses = result.getInt(1);
        result = st.executeQuery("select* from course;");
        while (result.next()) {
            Course c = new Course();
            c.setId(result.getInt(1));
            c.setTitle(result.getString(2));
            c.setStream(result.getString(3));
            c.setType(result.getString(4));
            c.setStartDate(result.getDate(5));
            c.setEndDate(result.getDate(6));
            arrCour.add(c);
        }
        for (int i = 0; i < numOfCourses; i++) {

            result = st.executeQuery("SELECT person_id,first_name,last_name FROM person "
                    + "INNER JOIN course_per_trainer ON person.PERSON_ID = course_per_trainer.trainer_ID "
                    + "INNER JOIN course ON "
                    + "course.COURSE_ID = course_per_trainer.COURSE_ID where course.course_ID = " + arrCour.get(i).getId() + ";");
            arrSt.clear();
            while (result.next()) {

                Person s = new Person();
                s.setId(result.getInt(1));
                s.setFirstName(result.getString(2));
                s.setLastName(result.getString(3));

                arrSt.add(s);
            }
            System.out.println(arrCour.get(i));
            System.out.println(arrSt.toString());
        }
    }

    public static void showAssignmentsPerCourse() throws SQLException {
        Statement st = con.createStatement();
        ArrayList<Exercise> arrEx = new ArrayList<>();

        ArrayList<Course> arrCour = new ArrayList<>();
        ResultSet result = st.executeQuery("select count(course_id) from course;");
        result.next();
        int numOfCourses = result.getInt(1);
        result = st.executeQuery("select* from course;");
        while (result.next()) {
            Course c = new Course();
            c.setId(result.getInt(1));
            c.setTitle(result.getString(2));
            c.setStream(result.getString(3));
            c.setType(result.getString(4));
            c.setStartDate(result.getDate(5));
            c.setEndDate(result.getDate(6));
            arrCour.add(c);
        }

        for (int i = 0; i < numOfCourses; i++) {

            result = st.executeQuery("select EXERCISE_ID,TITLE,DESCRIPTION,SUB_DATE_TIME from assignment "
                    + "inner join exercise on assignment.ASSIGNMENT_EXERCISE_ID = exercise_id "
                    + "where assignment.ASSIGNMENT_EXERCISE_ID = " + arrCour.get(i).getId() + ";");
            arrEx.clear();
            while (result.next()) {

                Exercise e = new Exercise();
                e.setId(result.getInt(1));
                e.setTitle(result.getString(2));
                e.setDescription(result.getString(3));
                e.setSubDateTime(result.getDate(4));

                arrEx.add(e);
            }
            System.out.println(arrCour.get(i));
            System.out.println(arrEx.toString());
        }
    }

    public static void showAssignmentsPerCoursePerStudent() throws SQLException {
        ArrayList<Person> arrSt = new ArrayList<>();

        Statement st = con.createStatement();
        ArrayList<Exercise> arrEx = new ArrayList<>();

        ArrayList<Course> arrCour = new ArrayList<>();
        ResultSet result = st.executeQuery("select count(course_id) from course;");
        result.next();
        int numOfCourses = result.getInt(1);
        result = st.executeQuery("select* from course;");
        while (result.next()) {
            Course c = new Course();
            c.setId(result.getInt(1));
            c.setTitle(result.getString(2));
            c.setStream(result.getString(3));
            c.setType(result.getString(4));
            c.setStartDate(result.getDate(5));
            c.setEndDate(result.getDate(6));
            arrCour.add(c);
        }

        for (int i = 0; i < numOfCourses; i++) {

            result = st.executeQuery("select EXERCISE_ID,TITLE,DESCRIPTION,SUB_DATE_TIME from assignment "
                    + "inner join exercise on assignment.ASSIGNMENT_EXERCISE_ID = exercise_id "
                    + "where assignment.ASSIGNMENT_EXERCISE_ID = " + arrCour.get(i).getId() + ";");
            arrEx.clear();
            while (result.next()) {

                Exercise e = new Exercise();
                e.setId(result.getInt(1));
                e.setTitle(result.getString(2));
                e.setDescription(result.getString(3));
                e.setSubDateTime(result.getDate(4));

                arrEx.add(e);
            }

            result = st.executeQuery("SELECT person_id,first_name,last_name "
                    + "FROM person INNER JOIN course_per_student ON person.PERSON_ID = course_per_student.STUDENT_ID "
                    + "INNER JOIN course ON course.COURSE_ID = course_per_student.COURSE_ID where course.course_ID = " + arrCour.get(i).getId() + ";");
            arrSt.clear();
            while (result.next()) {

                Person s = new Person();
                s.setId(result.getInt(1));
                s.setFirstName(result.getString(2));
                s.setLastName(result.getString(3));

                arrSt.add(s);
            }
            System.out.println(arrCour.get(i));
            System.out.println(arrSt.toString());
            System.out.println(arrEx.toString());
        }
    }
    public static void showStudentsToMoreThanOneCourse() throws SQLException {
        Statement st = con.createStatement();
        ArrayList<Student> arrSt = new ArrayList<>();
        ArrayList<Course> arrCour = new ArrayList<>();
        ResultSet result = st.executeQuery("select count(person_student_id) from student;");
        result.next();
        int numOfStudents = result.getInt(1);
        result = st.executeQuery("select* from student inner join person on "
                + "student.PERSON_STUDENT_ID = person.PERSON_ID;");
        while (result.next()) {
            Student s = new Student();

            s.setId(result.getInt("PERSON_ID"));
            s.setFirstName(result.getString("FIRST_NAME"));
            s.setLastName(result.getString("LAST_NAME"));
            s.setDateOfBirth(result.getDate("DATE_OF_BIRTH"));
            s.setTuitionFees((int) result.getDouble("TUITION_FEES"));

            arrSt.add(s);
        }

        for (int i = 0; i < numOfStudents; i++) {
            result = st.executeQuery("SELECT course.course_id,course.title,course.stream,course.type,course.end_date,course.start_date "
                    + "FROM person "
                    + "INNER JOIN course_per_student ON person.PERSON_ID = course_per_student.STUDENT_ID "
                    + "INNER JOIN course ON course.COURSE_ID = course_per_student.COURSE_ID "
                    + "where person.person_id = " + arrSt.get(i).getId() + ";");
            arrCour.clear();
            while (result.next()) {

                Course c = new Course();
                c.setId(result.getInt(1));
                c.setTitle(result.getString(2));
                c.setStream(result.getString(3));
                c.setType(result.getString(4));
                c.setStartDate(result.getDate(5));
                c.setEndDate(result.getDate(6));
                arrCour.add(c);
            }
            if (arrCour.size() > 1) {
                System.out.println(arrCour.toString());
                System.out.println(arrSt.get(i));
            }

        }

    }
}
