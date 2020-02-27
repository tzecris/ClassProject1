/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Exercise implements Serializable {

    private String title, description;
    private Date subDateTime;
    private int oralMark, totalMark, id;

    public Exercise(String title, String description, Date subDateTime) {
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;

    }

    public Exercise() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSubDateTime() {
        return subDateTime;
    }

    public void setSubDateTime(Date subDateTime) {
        this.subDateTime = subDateTime;
    }

    public int getOralMark() {
        return oralMark;
    }

    public void setOralMark(int oralMark) {
        this.oralMark = oralMark;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id + ", " + this.title + ", " + this.subDateTime + "\n";
    }

}
