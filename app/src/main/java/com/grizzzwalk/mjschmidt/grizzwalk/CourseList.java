package com.grizzzwalk.mjschmidt.grizzwalk;

/**
 * Created by austindanaj on 9/28/16.
 */

/** This is How to Reference Course
 * ---------------------------------
 * CourseList newClass = CourseList.getInstance();
 * newClass.[fill function here];
 */
import android.app.*;
import android.content.*;
import android.app.AlarmManager;

import java.lang.*;
import java.sql.*;


public class CourseList {

    //Variable for singleton
    private static CourseList sharedInstance = new CourseList();
    private CourseList(){ }
    public static CourseList getInstance(){return sharedInstance;}

    // Array for storage of courseDetails
    private Course[] courses = new Course[10];

    /**
     * Create a new Course, then call setClass func
     */
    public boolean addCourse (String courseName, String startTime, String endTime, String roomNumber, String daysOfWeek){
        int index = getIndex();
        if (index == -1){
            return false;
        }
       // Time sTime = split(startTime);
       // Time eTime = split(endTime);

        Course addCourse = new Course(courseName, index, startTime, endTime, roomNumber, daysOfWeek);
        setCourse(addCourse);
        return true;
    }

    /**
     * Edits the course at the Course Index with updated information
     */
    public void editCourse(int index, String courseName, String startTime, String endTime, String roomNumber, String daysOfWeek){
       // Time sTime = split(startTime);
       // Time eTime = split(startTime);

        courses[index].setCourseName(courseName);
        courses[index].setStartTime(startTime);
        courses[index].setEndTime(endTime);
        courses[index].setRoomNumber(roomNumber);
        courses[index].setDays(daysOfWeek);
    }

    /**
     * Sets the class to the specified ordinal number in the Course List Variable
     */
    protected void setCourse(Course newCourse){
        courses[newCourse.getIndex()] = newCourse;}

    /**
     * Gets the Course at the specified ordinal
     */
    protected Course getCourse(int index){
        return courses[index];
    }

    /**
     * Removes Course from CourseList List
     */
    public void removeCourse(int index) { courses[index] = null;}

    protected void resetCourseList(){
        for (int index = 0; index < courses.length; index++){
            courses[index] = null;
        }
    }
    /*
    Gets an index for the specific course
     */
    private int getIndex(){

        for (int index = 0; index< courses.length; index++){
            if (courses[index] == null) {
                return index;
            } // end if
        } // end fordynam
        return -1;
    } // function
    /*
    Gets time
     */
    private Time split(String time){
        String [] timeSplit = time.split(":");
        long hours = Long.valueOf(timeSplit[0]).longValue();
        long minutes = Long.valueOf(timeSplit[1]).longValue();
        hours = hours * (long)3.6 * (long)(Math.pow(10.0, 7.0));
        minutes = minutes * (long)(60000.0);
        long combinedTime = hours + minutes;
        Time setTime = new Time(combinedTime);
        return setTime;
    }
    /*
    Gets the number of elements in the CourseList
     */
    protected int sizeOfArray(){
        int count = 0;
        for (int index = 0; index < courses.length; index++){
            if(courses[index] != null){
                count++;
            }
        }
        return count;
    }

    protected Course[] getCourses(){
        return courses;
    }
}
