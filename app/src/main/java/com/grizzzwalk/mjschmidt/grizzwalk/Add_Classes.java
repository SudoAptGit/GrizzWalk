package com.grizzzwalk.mjschmidt.grizzwalk;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Add_Classes extends AppCompatActivity {

    static int shour,sminute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button classSave = (Button) findViewById(R.id.btnSave);
        Button classReset = new Button(this);
        classReset = (Button) findViewById(R.id.btnResetAll);
        final Button starttime = (Button) findViewById(R.id.btns);
        final Button endtime = (Button) findViewById(R.id.btne);

        final EditText textCName = (EditText) findViewById(R.id.txtCourseName);
        final EditText textRNumber = (EditText) findViewById(R.id.txtRoomNumber);
        final CheckBox chkMonday = (CheckBox) findViewById(R.id.chkMonday);
        final CheckBox chkTuesday = (CheckBox) findViewById(R.id.chkTuesday);
        final CheckBox chkWednesday = (CheckBox) findViewById(R.id.chkWednesday);
        final CheckBox chkThursday = (CheckBox) findViewById(R.id.chkThursday);
        final CheckBox chkFriday = (CheckBox) findViewById(R.id.chkFriday);
        final CheckBox chkSaturday = (CheckBox) findViewById(R.id.chkSaturday);
        starttime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new TimePickerDialog(Add_Classes.this,new TimePickerDialog.OnTimeSetListener(){
                    public void onTimeSet(TimePicker view,int hourOfDay,int minute){
                        String stime = String.format("%02d:%02d",hourOfDay,minute);
                        starttime.setText(stime);
                        shour = hourOfDay;
                        sminute = minute;
                    }
                },0,0,false).show();
            }
        });
        endtime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new TimePickerDialog(Add_Classes.this,new TimePickerDialog.OnTimeSetListener(){
                    public void onTimeSet(TimePicker view,int hourOfDay,int minute){
                        String etime = String.format("%02d:%02d",hourOfDay,minute);
                        if(hourOfDay>shour){
                            endtime.setText(etime);
                            classSave.setEnabled(true);
                        }
                        else if(hourOfDay==shour&&minute<sminute||hourOfDay<shour){
                            endtime.setText("MUST BE LATER THAN START TIME!!!");
                            classSave.setEnabled(false);
                        }
                    }
                },0,0,false).show();
            }
        });

        //Checks to make sure all fields have something in them. Saves the class to the courseList if they do.
        classSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Classes.this, Class_Display.class);
                CourseList courseList = CourseList.getInstance();
                String temp = "";
                //Checks to see if the courseList is at max. If it is, then the class is not saved.
                if (courseList.sizeOfArray() >= 10){
                    Toast.makeText(getApplicationContext(), "You can't add more than ten classes. Class not saved.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    if(textCName.getText().toString().matches(""))
                        Toast.makeText(getApplicationContext(), "Please enter a course name", Toast.LENGTH_SHORT).show();
                    else if (starttime.getText().toString().matches("choose time") || (starttime.getText().toString().matches("MUST BE LATER THAN START TIME!!!")))
                        Toast.makeText(getApplicationContext(), "Please enter start time", Toast.LENGTH_SHORT).show();
                    else if (endtime.getText().toString().matches("choose time") || (endtime.getText().toString().matches("MUST BE LATER THAN START TIME!!!")))
                        Toast.makeText(getApplicationContext(), "Please enter end time", Toast.LENGTH_SHORT).show();
                    else if(textRNumber.getText().toString().matches(""))
                        Toast.makeText(getApplicationContext(), "Please enter a room number", Toast.LENGTH_SHORT).show();
                    else {
                        if (chkMonday.isChecked()) {
                            temp = temp + "M";
                        }
                        if (chkTuesday.isChecked()) {
                            temp = temp + "T";
                        }
                        if (chkWednesday.isChecked()) {
                            temp = temp + "W";
                        }
                        if (chkThursday.isChecked()) {
                            temp = temp + "R";
                        }
                        if (chkFriday.isChecked()) {
                            temp = temp + "F";
                        }
                        if (chkSaturday.isChecked()) {
                            temp = temp + "S";
                        }
                        if (temp == "")
                            Toast.makeText(getApplicationContext(), "Please check at least one day", Toast.LENGTH_SHORT).show();
                        else {
                            courseList.addCourse(textCName.getText().toString(), starttime.getText().toString(), endtime.getText().toString(), textRNumber.getText().toString(), temp);
                            textCName.setText("");
                            textRNumber.setText("");
                            if (chkMonday.isChecked())
                                chkMonday.toggle();
                            if (chkTuesday.isChecked())
                                chkTuesday.toggle();
                            if (chkWednesday.isChecked())
                                chkWednesday.toggle();
                            if (chkThursday.isChecked())
                                chkThursday.toggle();
                            if (chkFriday.isChecked())
                                chkFriday.toggle();
                            if (chkSaturday.isChecked())
                                chkMonday.toggle();

                            /*
                            Puts the new course into a new string set, then stores that set into SharedPreferences
                            for safe-keeping
                             */
                            SharedPreferences classes = getApplicationContext().getSharedPreferences("classes", 0);
                            SharedPreferences.Editor edit = classes.edit();
                            Set<String> set = new HashSet<String>();

                            if (courseList.sizeOfArray() > 0) {
                                for (int i = 0; i < courseList.sizeOfArray(); i++) {
                                    set.add(courseList.getCourse(i).getCourseName() + "/"
                                            + courseList.getCourse(i).getStartTime() + "/"
                                            + courseList.getCourse(i).getEndTime() + "/"
                                            + courseList.getCourse(i).getRoomNumber() + "/"
                                            + courseList.getCourse(i).getDays());
                                }

                                edit.putStringSet("classes", set);
                                edit.commit();
                            }
                            Toast.makeText(getApplicationContext(), "Class saved", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        classReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textCName.setText("");
                textRNumber.setText("");
                chkMonday.setChecked(false);
                chkTuesday.setChecked(false);
                chkWednesday.setChecked(false);
                chkThursday.setChecked(false);
                chkFriday.setChecked(false);
                chkSaturday.setChecked(false);
                starttime.setText("choose time");
                endtime.setText("choose time");
            }
        });
    }



}
