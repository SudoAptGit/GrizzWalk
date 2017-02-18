package com.grizzzwalk.mjschmidt.grizzwalk;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.grizzzwalk.mjschmidt.grizzwalk.R.layout.white_text_list;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.WEEK_OF_MONTH;
import static java.util.Calendar.WEEK_OF_YEAR;

public class Month_View extends AppCompatActivity {

    final String[] alarmCourse = {""};
    final Calendar alarmInit = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month__view);


        final CalendarView calendarList = (CalendarView) (findViewById(R.id.cdrMonth));

        Button examButton = (Button) findViewById((R.id.examButton));
        final Calendar alarmDate = Calendar.getInstance();
        final Calendar alarm = Calendar.getInstance();

        updateCalendar(alarmDate.get(Calendar.YEAR), alarmDate.get(Calendar.MONTH), alarmDate.get(Calendar.DAY_OF_MONTH));

        /*
        On click, allows the user to set an alert for an exam on the day selected on the Calendar
         */
        examButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArrayList<String> classNames = new ArrayList<>();
                final ArrayList<String> examTimes = new ArrayList<>();
                final CourseList courseList = CourseList.getInstance();
                final AlertDialog.Builder examCourse = new AlertDialog.Builder(Month_View.this);
                final AlertDialog.Builder examTime =  new AlertDialog.Builder(Month_View.this);

                examTimes.add("1 hour");
                examTimes.add("6 hours");
                examTimes.add("24 hours");
                examTimes.add("7 days");


                for( int i = 0; i < courseList.sizeOfArray(); i++){
                    Course course = courseList.getCourse(i);
                    classNames.add(course.getCourseName());
                }

                final CharSequence[] courseChars = classNames.toArray(new CharSequence[classNames.size()]);
                final CharSequence[] examAlertTime = examTimes.toArray(new CharSequence[examTimes.size()]);

                examCourse.setTitle("Select a course:");
                examCourse.setCancelable(false);

                examTime.setTitle("How long before the exam would you like to be notified?");

                examCourse.setItems(courseChars, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for( int i = 0; i < courseList.sizeOfArray(); i++){
                            Course course = courseList.getCourse(i);
                            if (courseChars[which] == course.getCourseName()) {
                                int alarmYear = alarmDate.get(Calendar.YEAR);
                                int alarmMonth = alarmDate.get(Calendar.MONTH);
                                int alarmDay = alarmDate.get(DAY_OF_MONTH);
                                int alarmHour = Integer.parseInt(course.getStartTime().split(":")[0]);
                                int alarmMinute = Integer.parseInt(course.getStartTime().split(":")[1]);
                                alarmInit.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
                                alarmCourse[0] = course.getCourseName();

                                examTime.setItems(examAlertTime, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(examAlertTime[which] == "1 hour")
                                            alarm.add(Calendar.HOUR_OF_DAY, -1);
                                        else if(examAlertTime[which] == "6 hours")
                                            alarm.add(Calendar.HOUR_OF_DAY, -6);
                                        else if(examAlertTime[which] == "24 hours")
                                            alarm.add(Calendar.DAY_OF_YEAR, -1);
                                        else if(examAlertTime[which] == "7 days")
                                            alarm.add(WEEK_OF_MONTH, -1);

                                        setAlarm(alarm);
                                        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
                                        Toast.makeText(getApplicationContext(), "Alert set for " + format.format(alarm.getTime()), Toast.LENGTH_LONG).show();
                                    }
                                });
                                examTime.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                examTime.show();

                                alarm.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
                            }
                        }
                    }
                });
                examCourse.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                examCourse.show();
            }
        });
        /*
        Puts the classes the user has for that day in the ListView, when they click a day of the
        month on the CalendarView
         */
        calendarList.setOnDateChangeListener (new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                alarmDate.set(year, month, dayOfMonth);
                updateCalendar(year, month, dayOfMonth);
            }
        });
    }

    /*
    Converts the day of week integer the calendarView gives you into a char, for easier analysis
    of classes
    @param numberDay    The day of week the user has selected in the calendarView
    @return             The day of week int converted to a char
     */
    protected Character getDay(int numberDay){

        char day;
        switch (numberDay){
            case 1:
                day = 'Z';
                break;
            case 2:
                day = 'M';
                break;
            case 3:
                day = 'T';
                break;
            case 4:
                day = 'W';
                break;
            case 5:
                day = 'R';
                break;
            case 6:
                day = 'F';
                break;
            case 7:
                day = 'S';
                break;
            default: day = 'X';
                break;
        }
        return day;
    }

    /*
    Takes in the class the app is examining, and determines whether or not that class is on the day
    the user has selected.
    @param  dayOfWeek   The day the user has selected on the calendarView
    @param  course  The current course object
    @return whether or not that course is on the day the user has selected
     */
    protected boolean classOnDay(char dayOfWeek, Course course){

        char[] daysOfClass = (course.getDays()).toCharArray();

        for( int i = 0; i < daysOfClass.length; i++){
            if (daysOfClass[i] == dayOfWeek)
                return true;
        }
        return false;
    }

    /*
    Sets an exam alarm for the day and time the user has selected
     */
    private void setAlarm(Calendar when) {

        Intent alarmIntent = new Intent(this, AlarmReciever.class);
        alarmIntent.putExtra("course", alarmCourse[0]);
        alarmIntent.putExtra("time", alarmInit.getTimeInMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pendingIntent);
    }

    /*
    Updates the class Listview, once the activity loads and when the user selects a date
     */
    private void updateCalendar(int year, int month, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        int numberOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        ArrayList<String> monthCourses = new ArrayList<>();
        CourseList courseList = CourseList.getInstance();
        ListView monthList = (ListView) (findViewById(R.id.lstMonth));

        Character dayOfWeek = getDay(numberOfWeek);

        for( int i = 0; i < courseList.sizeOfArray(); i++){
            Course course = courseList.getCourse(i);
            if (classOnDay(dayOfWeek, course))
                monthCourses.add(course.getRoomNumber()+ ", " + course.getStartTime() +
                        "-" + course.getEndTime() + ", " + course.getCourseName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), white_text_list, monthCourses);
        monthList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
