package com.grizzzwalk.mjschmidt.grizzwalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import java.util.*;

import static com.grizzzwalk.mjschmidt.grizzwalk.R.layout.white_text_list;

public class Class_Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class__display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView courseListView = (ListView) findViewById(R.id.lstDisplay);
        ArrayList<String> courses = new ArrayList<>();
        final CourseList courseList = CourseList.getInstance();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, white_text_list , courses);
        courseListView.setAdapter(adapter);

        SharedPreferences classes = getSharedPreferences("classes", 0);
        Set<String> set2 = classes.getStringSet("classes", null);

        /*
        Determines if the user has any classes saves, and fills the courseList and
        Arrayview accordingly from the values saved in SharedPreferences
         */
        if ((courseList.sizeOfArray() == 0) && (set2 != null) ) {
            String[] classString = null;
            for (String string : set2) {
                classString = string.split("/");
                courseList.addCourse(classString[0], classString[1], classString[2], classString[3], classString[4]);
            }
        }
        FloatingActionButton addMoreClassesFab = (FloatingActionButton) findViewById(R.id.fab);

        /*
        Adds the courses in CourseList to the arraylist for the user to see
         */
        for (int i = 0; i < courseList.sizeOfArray(); i++) {
            courses.add("Course Name: " + courseList.getCourse(i).getCourseName() +
                    "\nRoom Number: " + courseList.getCourse(i).getRoomNumber() +
                    "\nTime: " + courseList.getCourse(i).getStartTime() +
                    "-" + courseList.getCourse(i).getEndTime() +
                    "\nDays: " + getDays(courseList.getCourse(i).getDays()));
        }

        /*final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        courseListView.setAdapter(adapter);*/
        adapter.notifyDataSetChanged();

        /*
        Takes the user to the directions screen after clicking the directions button
         */
        Button getDirectionsButton = (Button) findViewById(R.id.getDirections);
        getDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Class_Display.this, Get_Directions.class);
                startActivity(intent);
            }
        });

        /*
        Takes the user to the calendar view after clicking the monthly view button
         */
        Button month_view_button = (Button) findViewById(R.id.month_view_button);
        month_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Class_Display.this, Month_View.class);
                startActivity(intent);
            }
        });

        /*
        Clears the entire class list on confirming the selection of the reset button
         */
        Button reset_all_button = (Button) findViewById(R.id.resetall);
        reset_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener yesOrNo = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                                CourseList courseList = CourseList.getInstance();
                                courseList.resetCourseList();
                                courseListView.setAdapter(null);

                                SharedPreferences classes = getSharedPreferences("classes", 0);
                                SharedPreferences.Editor edit = classes.edit();
                                edit.clear();
                                edit.commit();

                                Toast.makeText(getApplicationContext(), "Class list clear", Toast.LENGTH_SHORT).show();

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setPositiveButton("Clear Classes", yesOrNo).setNegativeButton("Don't Clear Classes", yesOrNo).show();



                /*CourseList courseList = CourseList.getInstance();
                courseList.resetCourseList();
                courseListView.setAdapter(null);

                SharedPreferences classes = getSharedPreferences("classes", 0);
                SharedPreferences.Editor edit = classes.edit();
                edit.clear();
                edit.commit();

                Toast.makeText(getApplicationContext(), "Class list clear", Toast.LENGTH_SHORT).show();*/

            }
        });
        /*
        Sends user to the add classes screen once they click the fab with the
        If the user has more than ten classes, it tells them they have too many
         */
        addMoreClassesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseList.sizeOfArray() >= 10)
                    Toast.makeText(getApplicationContext(), "You can't add more than ten classes.", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(Class_Display.this, Add_Classes.class);
                    startActivity(intent);
                }
            }
        });


    }

    /*
    Converts abbreviations into readable days. For easier reading by the user
    @param dayAbbreviations Days as they are stored in a course object in the courseList.
    @return                 Days as they will be read by the user.
     */
    protected String getDays(String dayAbbreviations) {

        String dayConversion = "";

        for (int i = 0; i < dayAbbreviations.length(); i++) {
            char currentDay = dayAbbreviations.charAt(i);

            switch (currentDay) {
                case 'M':
                    dayConversion += "Monday, ";
                    break;
                case 'T':
                    dayConversion += "Tuesday, ";
                    break;
                case 'W':
                    dayConversion += "Wednesday, ";
                    break;
                case 'R':
                    dayConversion += "Thursday, ";
                    break;
                case 'F':
                    dayConversion += "Friday, ";
                    break;
                case 'S':
                    dayConversion += "Saturday, ";
                    break;
                default:
                    break;
            }
        }
        return dayConversion.substring(0, dayConversion.length() - 2);
    }
}