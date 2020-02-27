package course;

import classproject.ClassProject;
import classproject.SyntheticValues;
import classproject.Validation;
import exercise.Assignment;
import exercise.Project;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import person.Student;
import person.Trainer;

public class Course {


    private String title, type, stream;
    private Date startDate;
    private Date endDate;
    private int id;

    private List<Student> studentsPerCourseList = new ArrayList<>();
    private List<Trainer> trainersPerCourseList = new ArrayList<>();
    private List<Assignment> assignmentsPerCourseList = new ArrayList<>();
    private List<Project> projectsPerCourseList = new ArrayList<>();




    public Course(String title, String stream, String type, Date startDate, Date endDate) {

        this.title = title;
        this.type = type;
        this.stream = stream;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Course() {
    }

    public static void addCourseToList(Scanner sc, List<Course> listOfCourses) {
        String input;
        System.out.println("1.Add new Course");
        System.out.println("2.Import from existing Courses");
        input = sc.nextLine();
        while (!input.equalsIgnoreCase("1") && !input.equalsIgnoreCase("2")) {
            System.out.println("Give a valid answer:");
            input = sc.nextLine();
        }
        if (input.equals("2")) {
            try {
                if (!listOfCourses.containsAll(SyntheticValues.syntheticCourses())) {
                    listOfCourses.addAll(SyntheticValues.syntheticCourses());
                }

            } catch (ParseException ex) {
                Logger.getLogger(ClassProject.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(listOfCourses);
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

        if (!listOfCourses.contains(c1)) {
            listOfCourses.add(c1);
        } else {
            System.out.println("Course already exist.");
        }


    }

    public boolean addStudent(Student student) {
        if (this.studentsPerCourseList.contains(student)) {
            System.out.println("Student already exists");
            return false;
        } else if (this.studentsPerCourseList.size() > 20) {
            System.out.println("Course is full. You can't add another student");
            return false;
        } else {
            this.studentsPerCourseList.add(student);
            return true;
        }
    }


    public void addTrainer(Trainer trainer) {
        if (!trainersPerCourseList.contains(trainer)) {
            this.trainersPerCourseList.add(trainer);
        } else {
            System.out.println("Trainer already exist.");
        }
    }


    public Course(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public List<Student> getStudentsPerCourseList() {
        return studentsPerCourseList;
    }

    public void setStudentsPerCourseList(List<Student> studentsPerCourseList) {
        this.studentsPerCourseList = studentsPerCourseList;
    }

    public List<Trainer> getTrainersPerCourseList() {
        return trainersPerCourseList;
    }

    public void setTrainersPerCourseList(List<Trainer> trainersPerCourseList) {
        this.trainersPerCourseList = trainersPerCourseList;
    }

    public List<Assignment> getAssignmentsPerCourseList() {
        return assignmentsPerCourseList;
    }

    public List<Project> getProjectsPerCourseList() {
        return projectsPerCourseList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return this.id + ". " + this.title + ", " + this.stream + ", its "
                + this.type + " course. Starts at " + Validation.formatDate(this.startDate) + " and ends at "
                + Validation.formatDate(this.endDate) + ". "
                + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.stream, other.stream)) {
            return false;
        }
        return true;
    }

}
