package com.freelance.school_attendance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseRole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

    }

    public void openTeacherLogin(View v) {

        Intent i = new Intent(this, SchoolLogin.class);
        i.putExtra("LoginAs", false);
        startActivity(i);
    }

    public void openSchoolLogin(View view) {
        Intent i = new Intent(this, SchoolLogin.class);
        i.putExtra("LoginAs", true);
        startActivity(i);

    }
}