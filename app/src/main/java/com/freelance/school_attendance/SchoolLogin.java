package com.freelance.school_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

public class SchoolLogin extends AppCompatActivity {
    FetchDetailsSheet info;
    TextInputEditText SIN, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_login);
        SIN=(TextInputEditText) findViewById(R.id.ed_sin);
        pw= (TextInputEditText)findViewById(R.id.ed_pw);
        CircularProgressIndicator indicator = findViewById(R.id.indicator);
        info= new FetchDetailsSheet(this, indicator);
        info.getItems();
       // initSpinner();

    }

    public void goToDropDown(View view) {
        checkvalidSINpw();

    }

    private void checkvalidSINpw() {

        String sin= SIN.getText().toString().trim();
        String passw= pw.getText().toString().trim();
        if(sin.equals(info.sc_SIN) && passw.equals(info.sc_pw))
        {
            Intent i = new Intent(this, ClassSubjectDropDown.class);
            i.putExtra("School name", info.sc_name+"");
            i.putExtra("Teacherlist", info.teacherlist);
            i.putExtra("Classlist",info.classlist);
            i.putExtra("Subjectlist",info.subjectlist);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "Wrong Credentials ! Please Try Again", Toast.LENGTH_SHORT).show();

        }
    }
}