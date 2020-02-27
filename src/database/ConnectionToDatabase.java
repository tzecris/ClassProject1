package database;

import classproject.Validation;
import course.AssignmentsPerCourse;
import course.Course;
import course.ProjectPerCourse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import person.Student;
import person.Trainer;

public class ConnectionToDatabase {

    public static Connection con = null;
    public static ResultSet rs;
    public static Statement st;
    static String username, password;

    public static void createConnection(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/class_project?zeroDateTimeBehavior=CONVERT_TO_NULL&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";

//        username = "root";
//        password = "admin";

        try {
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public static void addCourse(Scanner sc) throws SQLException, ParseException {
        String input;
        st = con.createStatement();


        System.out.println("1.Add new Course");
        System.out.println("2.Import from existing Courses");
        input = sc.nextLine();
        while (!input.equalsIgnoreCase("1") && !input.equalsIgnoreCase("2")) {
            System.out.println("Give a valid answer:");
            input = sc.nextLine();
        }
        if (input.equals("2")) {

            insertSyntheticCourse();
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
            return;
        }
        Course c1 = new Course();

        System.out.println("Give title:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS_NO_NUMBER)) {
            System.out.println("Give valid title:");
            input = sc.nextLine();
        }
        c1.setTitle(input);

        System.out.println("Give stream:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS_NO_SHARP)) {
            System.out.println("Give valid stream:");
            input = sc.nextLine();
        }
        c1.setStream(input);

        System.out.println("Give type:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid type:");
            input = sc.nextLine();
        }
        c1.setType(input);

        System.out.println("Give start date (the format must be dd/mm/yyyy)");
        input = sc.nextLine();
        while (!input.matches(Validation.DATE_PATTERN)) {
            System.out.println("Give valid date:");
            input = sc.nextLine();
        }
        try {
            c1.setStartDate(Validation.parseDate(input));
        } catch (ParseException ex) {
            System.out.println("Invalid date");
        }

        System.out.println("Give end date (the format must be dd/mm/yyyy)");
        input = sc.nextLine();
        while (!input.matches(Validation.DATE_PATTERN)) {
            System.out.println("Give valid date:");
            input = sc.nextLine();
        }
        try {
            c1.setEndDate(Validation.parseDate(input));
        } catch (ParseException ex) {
            System.out.println("Invalid date");
        }
        try {
            AssignmentsPerCourse.addAssignmentToList(c1);
            ProjectPerCourse.addProjectToList(c1);
        } catch (ParseException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }

        int rowsAffected = st.executeUpdate("insert into course values (null,'" + c1.getTitle()
                + "','" + c1.getStream() + "','" + c1.getType()
                + "',STR_TO_DATE('" + Validation.formatDate(c1.getStartDate()) + "', '%d/%m/%Y')"
                + ",STR_TO_DATE('" + Validation.formatDate(c1.getEndDate()) + "', '%d/%m/%Y'));");

//        ResultSet result = st.executeQuery("select * from course order by course_id desc limit 4;");
        ResultSet result = st.executeQuery("select* from course where course_id in "
                + "(select MAX(course_id) from course);");

        while (result.next()) {
            Course c = new Course();

            c.setId(result.getInt(1));
            c.setTitle(result.getString(2));
            c.setStream(result.getString(3));
            c.setType(result.getString(4));
            c.setStartDate(result.getDate(5));
            c.setEndDate(result.getDate(6));

            addAssignmentToCourse(c);
        }
//        addAssignmentToCourse(c1);
        if (rowsAffected == 2) {
            System.out.println("Insert has not been Succeed");

        } else {
            System.out.println("Course has been inserted");

        }



    }

    public static void addStudent(Scanner sc) throws SQLException {

        Statement st = con.createStatement();

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

            insertSyntheticStudent();
            ResultSet result = st.executeQuery("select* from student inner join person on student.PERSON_STUDENT_ID = person.PERSON_ID;");

            while (result.next()) {

                Student s = new Student();

                s.setId(result.getInt("PERSON_ID"));
                s.setFirstName(result.getString("FIRST_NAME"));
                s.setLastName(result.getString("LAST_NAME"));
                s.setDateOfBirth(result.getDate("DATE_OF_BIRTH"));
                s.setTuitionFees((int) result.getDouble("TUITION_FEES"));
//                System.out.println(s);

                System.out.println(s);
            }
            result.close();
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

        int rowsAffected = st.executeUpdate("insert into person values (null,'" + s1.getFirstName()
                + "','" + s1.getLastName() + "');");

        int rowsAffected2 = st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('" + Validation.formatDate(s1.getDateOfBirth()) + "', '%d/%m/%Y'),NULL);");

        if (rowsAffected == 2 || rowsAffected2 == 2) {
            System.out.println("Insert has not been Succeed");

        } else {
            System.out.println("Student has been inserted");

        }



    }

    public static void addTrainer(Scanner sc) throws SQLException {
        Statement st = con.createStatement();

        String input;
        System.out.println("1.Add new Trainer");
        System.out.println("2.Import from existing Trainer");
        input = sc.nextLine();
        while (!input.equalsIgnoreCase("1") && !input.equalsIgnoreCase("2")) {
            System.out.println("Give a valid answer:");
            input = sc.nextLine();
        }
        if (input.equals("2")) {

//            insertSyntheticTrainer();
            ResultSet result = st.executeQuery("select* from trainer inner join person on trainer.PERSON_trainer_ID = person.PERSON_ID;");

            while (result.next()) {

                Trainer t = new Trainer();

                t.setId(result.getInt("PERSON_ID"));
                t.setFirstName(result.getString("FIRST_NAME"));
                t.setLastName(result.getString("LAST_NAME"));
                t.setSubject(result.getString("SUBJECT"));
//                System.out.println(s);

                System.out.println(t);
            }
            result.close();
            return;

        }
        Trainer t1 = new Trainer();

        System.out.println("Give first name:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid name:");
            input = sc.nextLine();
        }
        t1.setFirstName(input);

        System.out.println("Give last name:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid last name:");
            input = sc.nextLine();
        }
        t1.setLastName(input);

        System.out.println("Give teacher's subject name:");
        input = sc.nextLine();
        while (!Validation.stringChecker(input, Validation.INVALID_CHARACTERS)) {
            System.out.println("Give valid subject name:");
            input = sc.nextLine();
        }
        t1.setSubject(input);

        int rowsAffected = st.executeUpdate("insert into person values (null,'" + t1.getFirstName()
                + "','" + t1.getLastName() + "');");

        int rowsAffected2 = st.executeUpdate("insert into trainer values ((select MAX(person_id) from person),'" + (t1.getSubject()) + "');");

        if (rowsAffected == 2 || rowsAffected2 == 2) {
            System.out.println("Insert has not been Succeed");

        } else {
            System.out.println("Trainer has been inserted");

        }

    }

    public static void insertSyntheticStudent() throws SQLException {

        Statement st = con.createStatement();


        st.executeUpdate("insert into person values (null,'Nikos','Stam');");
        st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('01/06/1990', '%d/%m/%Y'),'250.5');");
        st.executeUpdate("insert into person values (null,'Christos','Tze');");
        st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('07/07/1991', '%d/%m/%Y'),'0');");
        st.executeUpdate("insert into person values (null,'Sony','Anderson');");
        st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('11/06/1995', '%d/%m/%Y'),'550');");
        st.executeUpdate("insert into person values (null,'Harry','Kane');");
        st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('30/09/1988', '%d/%m/%Y'),'2500');");
        st.executeUpdate("insert into person values (null,'Tom','Kins');");
        st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('01/02/1987', '%d/%m/%Y'),'120');");
        st.executeUpdate("insert into person values (null,'Sam','Wise');");
        st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('17/06/1990', '%d/%m/%Y'),'125');");
        st.executeUpdate("insert into person values (null,'Kate','Anderson');");
        st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('02/10/1990', '%d/%m/%Y'),'250');");
        st.executeUpdate("insert into person values (null,'Kostas','Chormouzis');");
        st.executeUpdate("insert into student values ((select MAX(person_id) from person),STR_TO_DATE('21/08/1990', '%d/%m/%Y'),'2200');");

    }

    public static void insertSyntheticCourse() throws SQLException {
        Statement st = con.createStatement();

        st.executeUpdate("insert into course values (null,'CB9','Java','full time',STR_TO_DATE('11/11/2019', '%d/%m/%Y'),STR_TO_DATE('11/05/2020', '%d/%m/%Y'));");
        st.executeUpdate("insert into course values (null,'CB9','C#','full time',STR_TO_DATE('14/10/2019', '%d/%m/%Y'),STR_TO_DATE('14/04/2020', '%d/%m/%Y'));");
        st.executeUpdate("insert into course values (null,'CB9','Java','part time',STR_TO_DATE('14/10/2019', '%d/%m/%Y'),STR_TO_DATE('14/01/2020', '%d/%m/%Y'));");
        st.executeUpdate("insert into course values (null,'CB9','C#','part time',STR_TO_DATE('11/11/2019', '%d/%m/%Y'),STR_TO_DATE('10/02/2020', '%d/%m/%Y'));");
        ResultSet result = st.executeQuery("select * from course order by course_id desc limit 4;");

        while (result.next()) {
            Course c = new Course();

            c.setId(result.getInt(1));
            c.setTitle(result.getString(2));
            c.setStream(result.getString(3));
            c.setType(result.getString(4));
            c.setStartDate(result.getDate(5));
            c.setEndDate(result.getDate(6));

            addAssignmentToCourse(c);
        }
        result.close();
    }

    public static void insertSyntheticTrainer() throws SQLException {
        Statement st = con.createStatement();

        st.executeUpdate("insert into person values (null,'Nick','Stam');");
        st.executeUpdate("insert into trainer values ((select MAX(person_id) from person),'Java');");

        st.executeUpdate("insert into person values (null,'Nakos','Samos');");
        st.executeUpdate("insert into trainer values ((select MAX(person_id) from person),'Javascript,Java');");

        st.executeUpdate("insert into person values (null,'Lakis','Stamos');");
        st.executeUpdate("insert into trainer values ((select MAX(person_id) from person),'SQL');");

        st.executeUpdate("insert into person values (null,'Chris','Tsak');");
        st.executeUpdate("insert into trainer values ((select MAX(person_id) from person),'C#');");

    }

    public static void addAssignmentToCourse(Course course) throws SQLException {

        Statement st = con.createStatement();
        int h;

        Calendar c = Calendar.getInstance();

        c.setTime(course.getStartDate());
        if (course.getType().equalsIgnoreCase("full time")) {
            h = 22;
        } else {
            h = 12;
        }
        c.add(Calendar.DATE, h);
        c.add(Calendar.DATE, h - 1);

        st.executeUpdate("insert into exercise values (null,'Exercise 1','" + course.getStream() + " 1 " + course.getType() + "',STR_TO_DATE('" + Validation.formatDate(avoidWeekend(c)) + "', '%d/%m/%Y'));");
        st.executeUpdate("insert into assignment values ((select MAX(exercise_id) from exercise)," + course.getId() + ");");
        c.add(Calendar.DATE, h - 1);
        st.executeUpdate("insert into exercise values (null,'Exercise 2','" + course.getStream() + " 1 " + course.getType() + "',STR_TO_DATE('" + Validation.formatDate(avoidWeekend(c)) + "', '%d/%m/%Y'));");
        st.executeUpdate("insert into assignment values ((select MAX(exercise_id) from exercise)," + course.getId() + ");");
        c.add(Calendar.DATE, h);
        st.executeUpdate("insert into exercise values (null,'Exercise 3','" + course.getStream() + " 1 " + course.getType() + "',STR_TO_DATE('" + Validation.formatDate(avoidWeekend(c)) + "', '%d/%m/%Y'));");
        st.executeUpdate("insert into assignment values ((select MAX(exercise_id) from exercise)," + course.getId() + ");");
        c.add(Calendar.DATE, h - 2);

        st.executeUpdate("insert into exercise values (null,'Exercise 4','" + course.getStream() + " 1 " + course.getType() + "',STR_TO_DATE('" + Validation.formatDate(avoidWeekend(c)) + "', '%d/%m/%Y'));");
        st.executeUpdate("insert into assignment values ((select MAX(exercise_id) from exercise)," + course.getId() + ");");
        c.add(Calendar.DATE, h);

        st.executeUpdate("insert into exercise values (null,'Exercise 5','" + course.getStream() + " 1 " + course.getType() + "',STR_TO_DATE('" + Validation.formatDate(avoidWeekend(c)) + "', '%d/%m/%Y'));");
        st.executeUpdate("insert into assignment values ((select MAX(exercise_id) from exercise)," + course.getId() + ");");

        
    }

    private static Date avoidWeekend(Calendar c) {
        Calendar d = c;

        int day = d.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
            d.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        }
        return d.getTime();
    }


}
