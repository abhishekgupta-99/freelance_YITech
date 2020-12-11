package com.freelance.school_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SchoolLogin extends AppCompatActivity {
    FetchDetailsSheet info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_login);
        info= new FetchDetailsSheet();
        info.getItems(this);
       // initSpinner();

    }

    public void goToDropDown(View view) {
        Intent i = new Intent(this, ClassSubjectDropDown.class);
        startActivity(i);
    }
}