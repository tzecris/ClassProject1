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
import person.Trainer;

/**
 *
 * @author Admin
 */
public class CoursePerTrainer {

    public static void addTrainerToCourse(Scanner sc, List<Course> listOfCourses, List<Trainer> listOfTrainers) {
        String input = null;
        Trainer trainer;

        System.out.println("Type a number to select the trainer you want");
        if (listOfTrainers.isEmpty()) {
            listOfTrainers.addAll(SyntheticValues.syntheticTrainers());
        }


        for (int i = 0; i < listOfTrainers.size(); i++) {
            System.out.println(i + 1 + "." + listOfTrainers.get(i));
        }

        input = sc.nextLine();
        int numberOfTrainers = listOfTrainers.size();
        while (!input.matches(Validation.NUMBERS_ONLY) || Integer.parseInt(input) > numberOfTrainers) {
            System.out.println("Give valid number.");
            input = sc.nextLine();

        }

        trainer = listOfTrainers.get(Integer.parseInt(input) - 1);

        System.out.println("");

        System.out.println("Type a number to select the course to add the trainer");

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
        listOfCourses.get(Integer.parseInt(input) - 1).addTrainer(trainer);

    }
    public static void addTrainerToCourse(Scanner sc) throws SQLException {
        st = con.createStatement();
        String input = null;

        System.out.println("Type a number to select the trainer you want");
        ResultSet result = st.executeQuery("select count(PERSON_TRAINER_ID) from trainer;");
        result.next();
        if (result.getInt(1) == 0) {
            ConnectionToDatabase.insertSyntheticTrainer();
        }
        result = st.executeQuery("select* from trainer inner join person on trainer.PERSON_trainer_ID = person.PERSON_ID;");

        List<Integer> trainerIds = new ArrayList<>();
        while (result.next()) {

            Trainer t = new Trainer();

            t.setId(result.getInt("PERSON_ID"));
            t.setFirstName(result.getString("FIRST_NAME"));
            t.setLastName(result.getString("LAST_NAME"));
            t.setSubject(result.getString("SUBJECT"));
            trainerIds.add(result.getInt("PERSON_ID"));

            System.out.println(t);
        }

        input = sc.nextLine();

        while (!input.matches(Validation.NUMBERS_ONLY) || !trainerIds.contains(Integer.parseInt(input))) {
            System.out.println("Give valid number.");
            input = sc.nextLine();
        }

        int trainer_id = Integer.parseInt(input);


        System.out.println("");

        System.out.println("Type a number to select the course to add the trainer");


        result = st.executeQuery("select count(course_ID) from course;");
        result.next();

        if (result.getInt(1) == 0) {
            ConnectionToDatabase.insertSyntheticCourse();
        }
        result = st.executeQuery("select * from course c\n"
                + "                where course_id not in(select cp.course_id "
                + "from course_per_trainer cp where cp.trainer_id=" + trainer_id + " );");

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
            System.out.println("There are no available courses for this trainer.");
            return;
        }

        input = sc.nextLine();

        while (!input.matches(Validation.NUMBERS_ONLY) || !courseIds.contains(Integer.parseInt(input))) {
            System.out.println("Give valid number.");
            input = sc.nextLine();

        }
        int course_id = Integer.parseInt(input);

        int rowsAffected = st.executeUpdate("INSERT INTO `COURSE_per_trainer` (`COURSE_ID`, `TRAINER_ID`) VALUES ('" + course_id + "', '" + trainer_id + "');");

        if (rowsAffected == 2) {
            System.out.println("Insert has not been Succeed");

        } else {
            System.out.println("Course has been inserted");

        }

    }

}
