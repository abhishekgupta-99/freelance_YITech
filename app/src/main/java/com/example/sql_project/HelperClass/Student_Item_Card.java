package com.example.sql_project.HelperClass;
import java.util.ArrayList;

public class Student_Item_Card {
    private String student_name;
    private String roll_no;
    private ArrayList<String> triggered_alarms;



    public Student_Item_Card(String student_name, String roll_no) {
        this.student_name = student_name;
        this.roll_no = roll_no;

    //    this.triggered_alarms=alarms_triggered;
    }

//    public ArrayList<String> getTriggered_alarms() {
//        return triggered_alarms;
//    }
//
//    public void setTriggered_alarms(ArrayList<String> triggered_alarms) {
//        this.triggered_alarms = triggered_alarms;
//    }

    public  Student_Item_Card()
    {

    }

    public String get_roll_no() {
        return roll_no;
    }

    public void set_roll_no(String roll_no) {
        this.roll_no = roll_no;
    }


    public String get_student_name() {
        return student_name;
    }

    public void set_student_name(String student_name) {
        this.student_name = student_name;
    }


}
