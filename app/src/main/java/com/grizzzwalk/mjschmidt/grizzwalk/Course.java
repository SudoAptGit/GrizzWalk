package com.grizzzwalk.mjschmidt.grizzwalk;

/**
 * Created by austindanaj on 9/28/16.
 */
import java.lang.*;
import java.sql.Time; //Might need for time

public class Course {

    /**
     * Variables for Detail Info
     */

    public String courseName;
    public int index;
    public String startTime;
    public String endTime;
    public String roomNumber;
    public String daysOfWeek;
    //Professor Name
    //public String professor;


    /**
     * Constructor for Course
     */
    public Course(){ }
    public Course(String cName, int ind, String sTime, String eTime, String rNumber, String days ){
        setCourseName(cName);
        setIndex(ind);
        setStartTime(sTime);
        setEndTime(eTime);
        setRoomNumber(rNumber);
        setDays(days);
    }

    /**
     * Set functions
     */
    public void setCourseName(String newCourseName){this.courseName = newCourseName;}
    public void setIndex(int newIndex){
        this.index = newIndex;
    }
    public void setStartTime(String newStartTime){
        this.startTime = newStartTime;
    }
    public void setEndTime(String newEndTime){
        this.endTime = newEndTime;
    }
    public void setRoomNumber(String newRoomNumber){this.roomNumber = newRoomNumber;}
    public void setDays(String newDays){
        this.daysOfWeek = newDays;
    }

    /**
     * Get functions
     */
    public String getCourseName(){
        return this.courseName;
    }
    public int getIndex(){
        return this.index;
    }
    public String getStartTime(){
        return this.startTime;
    }
    public String getEndTime(){
        return this.endTime;
    }
    public String getRoomNumber(){
        return this.roomNumber;
    }
    public String getDays(){
        return this.daysOfWeek;
    }

}
