/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import classproject.Validation;
import exercise.Assignment;
import exercise.Project;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProjectPerCourse {

    private List<Project> projectPerCourseList = new ArrayList<>();

    public static void addProjectToList(Course course) throws ParseException {
        List<Project> listOfProjects = new ArrayList<>();

        Project a1 = new Project(true, "Project 1", course.getStream() + " 1", Validation.parseDate("15/11/2019"));
        Project a2 = new Project(false, "Project 2", course.getStream() + " 2", Validation.parseDate("10/12/2019"));

        listOfProjects.add(a1);
        listOfProjects.add(a2);
        course.getProjectsPerCourseList().addAll(listOfProjects);
    }

    public List<Project> getProjectPerCourseList() {
        return projectPerCourseList;
    }

    public void setProjectPerCourseList(List<Project> projectPerCourseList) {
        this.projectPerCourseList = projectPerCourseList;
    }

}
