/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Project extends Exercise {

    private ArrayList<Project> projectList = new ArrayList<>();
    private boolean teamProject;

    public Project() {
    }

    public Project(boolean teamProject, String title, String description, Date subDateTime) {
        super(title, description, subDateTime);
        this.teamProject = teamProject;
    }


    public ArrayList<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList<Project> projectList) {
        this.projectList = projectList;
    }

    public boolean isTeamProject() {
        return teamProject;
    }

    @Override
    public String toString() {
        return "Project{" + "Title=" + super.getTitle() + ", Description=" + this.getDescription() + ", group Project " + this.teamProject + '}';
    }

}
