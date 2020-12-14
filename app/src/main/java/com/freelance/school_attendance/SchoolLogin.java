package com.freelance.school_attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelance.school_attendance.HelperClass.SharedPrefSession;
import com.google.android.material.textfield.TextInputEditText;

public class SchoolLogin extends AppCompatActivity {
    FetchDetailsSheet info;
    TextInputEditText SIN, pw;
    boolean loginAs;
    ProgressDialog loading;
    SharedPrefSession sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        SIN = findViewById(R.id.ed_sin);
        pw = findViewById(R.id.ed_pw);
        getBundleExtraData();
        sp=new SharedPrefSession(getApplicationContext());
       // CircularProgressIndicator indicator = findViewById(R.id.indicator);

        loading = ProgressDialog.show(this, "Loading", "Fetching Credentials", false, true);
        loading.setCanceledOnTouchOutside(false);
        loading.setCancelable(false);

        info = new FetchDetailsSheet(this, loading);
        info.getItems();
        // initSpinner();

    }

    private void getBundleExtraData() {

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            loginAs = b.getBoolean("LoginAs");

        }


    }

    public void goToDropDown(View view) {
        checkvalidSINpw();

    }

    private void checkvalidSINpw() {

        String sin = SIN.getText().toString().trim();
        String passw = pw.getText().toString().trim();
        if (sin.equals(info.sc_SIN) && passw.equals(info.sc_pw)) {
            Intent i = new Intent(this, ClassSubjectDropDown.class);
            i.putExtra("School name", info.sc_name + "");
            i.putExtra("Teacherlist", info.teacherlist);
            i.putExtra("Classlist", info.classlist);
            i.putExtra("Subjectlist", info.subjectlist);
            i.putExtra("LoginAs", loginAs);
            sp.createLoginSession(loginAs);
            startActivity(i);
        } else {
            Toast.makeText(this, "Wrong Credentials ! Please Try Again", Toast.LENGTH_SHORT).show();

        }
    }

    public void back(View view) {
        onBackPressed();
    }
}