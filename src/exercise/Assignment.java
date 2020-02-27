/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise;

import classproject.Validation;
import course.Course;
import java.util.ArrayList;
import java.util.Date;
import person.Student;

public class Assignment extends Exercise {

    private ArrayList<Student> assingmentPerStudentList = new ArrayList<>();
    private ArrayList<Course> assingmentPerCourseList = new ArrayList<>();

    public Assignment(String title, String description, Date subDateTime) {
        super(title, description, subDateTime);
    }

    public Assignment() {
    }


    public ArrayList<Student> getAssingmentPerStudentList() {
        return assingmentPerStudentList;
    }

    public void setAssingmentPerStudentList(ArrayList<Student> assingmentPerStudentList) {
        this.assingmentPerStudentList = assingmentPerStudentList;
    }

    public ArrayList<Course> getAssingmentPerCourseList() {
        return assingmentPerCourseList;
    }

    public void setAssingmentPerCourseList(ArrayList<Course> assingmentPerCourseList) {
        this.assingmentPerCourseList = assingmentPerCourseList;
    }

    @Override
    public String toString() {
        return super.getId() + ". Assignment{" + "Title=" + super.getTitle() + ", Description=" + this.getDescription() + ". Submission date: " + Validation.formatDate(super.getSubDateTime()) + "} \n";
    }

}
