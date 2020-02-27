/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import exercise.Assignment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class AssignmentsPerCourse {

    private List<Assignment> assignmentsPerCourseList = new ArrayList<>();

    public static void addAssignmentToList(Course course) {
        List<Assignment> listOfAssignments = new ArrayList<>();
        int h;

        Calendar c = Calendar.getInstance();
        c.setTime(course.getStartDate());
        if (course.getType().equalsIgnoreCase("full time")) {
            h = 22;
        } else {
            h = 12;
        }
        c.add(Calendar.DATE, h);

        Assignment a1 = new Assignment("Exercise 1", course.getStream() + " 1 " + course.getType(), avoidWeekend(c));
        c.add(Calendar.DATE, h - 1);
        Assignment a2 = new Assignment("Exercise 2", course.getStream() + " 2 " + course.getType(), avoidWeekend(c));
        c.add(Calendar.DATE, h);
        Assignment a3 = new Assignment("Exercise 3", course.getStream() + " 3 " + course.getType(), avoidWeekend(c));
        c.add(Calendar.DATE, h - 2);
        Assignment a4 = new Assignment("Exercise 4", course.getStream() + " 4 " + course.getType(), avoidWeekend(c));
        c.add(Calendar.DATE, h);
        Assignment a5 = new Assignment("Exercise 5", course.getStream() + " 5 " + course.getType(), avoidWeekend(c));

        listOfAssignments.add(a1);
        listOfAssignments.add(a2);
        listOfAssignments.add(a3);
        listOfAssignments.add(a4);
        listOfAssignments.add(a5);
        course.getAssignmentsPerCourseList().addAll(listOfAssignments);
    }

    private static Date avoidWeekend(Calendar c) {
        int day = c.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        }
        return c.getTime();
    }

    public List<Assignment> getAssignmentsPerCourseList() {
        return assignmentsPerCourseList;
    }

    public void setAssignmentsPerCourseList(List<Assignment> assignmentsPerCourseList) {
        this.assignmentsPerCourseList = assignmentsPerCourseList;
    }

}
