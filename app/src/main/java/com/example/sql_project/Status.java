package com.example.sql_project;

public class Status {


    public String setstatus(String lecsattended,String totallecs) {
        String default_status="No Status";
        int requiredclassesfor75  = (int) (75* Integer.parseInt(totallecs))/100;
        int requiredClasses = (int) (requiredclassesfor75-Integer.parseInt(lecsattended));
        if(requiredClasses<requiredclassesfor75) {
            String status = "On track,May leave next" + (requiredClasses - requiredclassesfor75) + "lectures";
            return status;
        }
        else if(requiredclassesfor75<requiredClasses)
        {
            String status = "Not on track,Need to attend next" + (requiredClasses - requiredclassesfor75) + "lectures";
            return status;
        }
        else
            return  default_status;

    }
}
