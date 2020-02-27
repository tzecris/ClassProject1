package classproject;

import course.AssignmentsPerCourse;
import course.Course;
import course.ProjectPerCourse;
import static database.ConnectionToDatabase.con;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import person.Student;
import person.Trainer;

public class SyntheticValues {

    public static List<Course> syntheticCourses() throws ParseException {
        List<Course> listOfCourses = new ArrayList<>();

        Course course1 = new Course("CB9", "Java", "full time", Validation.parseDate("11/11/2019"), Validation.parseDate("11/05/2020"));
        Course course2 = new Course("CB9", "C#", "full time", Validation.parseDate("14/10/2019"), Validation.parseDate("14/04/2020"));
        Course course3 = new Course("CB9", "Java", "part time", Validation.parseDate("14/10/2019"), Validation.parseDate("14/01/2020"));
        Course course4 = new Course("CB9", "C#", "part time", Validation.parseDate("11/11/2019"), Validation.parseDate("10/02/2020"));

        AssignmentsPerCourse.addAssignmentToList(course1);
        AssignmentsPerCourse.addAssignmentToList(course2);
        AssignmentsPerCourse.addAssignmentToList(course3);
        AssignmentsPerCourse.addAssignmentToList(course4);

        ProjectPerCourse.addProjectToList(course1);
        ProjectPerCourse.addProjectToList(course2);
        ProjectPerCourse.addProjectToList(course3);
        ProjectPerCourse.addProjectToList(course4);

        listOfCourses.add(course1);
        listOfCourses.add(course2);
        listOfCourses.add(course3);
        listOfCourses.add(course4);

        return listOfCourses;
    }

    public static List<Student> syntheticStudents() throws ParseException {
        List<Student> listOfStudents = new ArrayList<>();

        Student s1 = new Student("Nikos", "Stam", Validation.parseDate("01/06/1990"));
        Student s2 = new Student("Christos", "Tze", Validation.parseDate("07/07/1991"));
        Student s3 = new Student("Sony", "Anderson", Validation.parseDate("11/06/1995"));
        Student s4 = new Student("Harry", "Kane", Validation.parseDate("31/09/1988"));
        Student s5 = new Student("Tom", "Kins", Validation.parseDate("01/02/1987"));
        Student s6 = new Student("Sam", "Wise", Validation.parseDate("17/06/1990"));
        Student s7 = new Student("Kate", "Anderson", Validation.parseDate("02/10/1990"));
        Student s8 = new Student("Kostas", "Chormouzis", Validation.parseDate("21/08/1990"));

        listOfStudents.add(s1);
        listOfStudents.add(s2);
        listOfStudents.add(s3);
        listOfStudents.add(s4);
        listOfStudents.add(s5);
        listOfStudents.add(s6);
        listOfStudents.add(s7);
        listOfStudents.add(s8);

        return listOfStudents;
    }

    public static List<Trainer> syntheticTrainers() {
        List<Trainer> listOfTrainers = new ArrayList<>();

        Trainer trainer1 = new Trainer("Nick", "Stam", "Java");
        Trainer trainer2 = new Trainer("Nakos", "Samos", "C#");
        Trainer trainer3 = new Trainer("Lakis", "Stamos", "SQL");
        Trainer trainer4 = new Trainer("Chris", "Tsak", "Javascript,Java");
        
        listOfTrainers.add(trainer1);
        listOfTrainers.add(trainer2);
        listOfTrainers.add(trainer3);
        listOfTrainers.add(trainer4);

        return listOfTrainers;
    }

    public static void combineSyntheticValues() throws SQLException {
        Statement st = con.createStatement();
        List<Integer> studentListID = new ArrayList<>();
        List<Integer> courseListID = new ArrayList<>();
        List<Integer> trainerListID = new ArrayList<>();

        ResultSet result = st.executeQuery("select* from student;");
        while (result.next()) {

            studentListID.add(result.getInt("PERSON_STUDENT_ID"));

        }

        result = st.executeQuery("select* from course;");
        while (result.next()) {

            courseListID.add(result.getInt("course_ID"));

        }

        result = st.executeQuery("select* from trainer;");
        while (result.next()) {

            trainerListID.add(result.getInt("PERSON_TRAINER_ID"));

        }

        for (int i = 0; i < courseListID.size(); i++) {
            String insertS = "insert into course_per_student values (" + courseListID.get(i) + ",(SELECT s.person_student_id FROM student s\n"
                    + "where s.person_student_id not in(select cp.student_id from course_per_student cp where cp.course_id=" + courseListID.get(i) + ")\n"
                    + "ORDER BY RAND()\n"
                    + "LIMIT 1 ));";
            if (!studentListID.isEmpty()) {
                int rowsAffected = st.executeUpdate(insertS);
                if (rowsAffected == 1) {
                    st.executeUpdate(insertS);
                }
                String insert = "insert into course_per_trainer values (" + courseListID.get(i) + ",(SELECT s.person_trainer_id FROM trainer s\n"
                        + "where s.person_trainer_id not in(select cp.trainer_id from course_per_trainer cp where cp.course_id=" + courseListID.get(i) + ")\n"
                        + "ORDER BY RAND()\n"
                        + "LIMIT 1 ));";
                if (!trainerListID.isEmpty()) {
                    rowsAffected = st.executeUpdate(insert);
                    if (rowsAffected == 1) {
                        st.executeUpdate(insert);
                    }


            }
        }

        }
    }
}
