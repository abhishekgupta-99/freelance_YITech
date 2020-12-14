package com.freelance.school_attendance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.flatdialoglibrary.dialog.FlatDialog;

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
        dialogboxlink();

    }

    public void openSchoolLogin(View view) {
        Intent i = new Intent(this, SchoolLogin.class);
        i.putExtra("LoginAs", true);
        startActivity(i);

    }

   public void dialogboxlink()
    {
        final FlatDialog flatDialog = new FlatDialog(this);
        flatDialog.setTitle("Gsheet link")
                .setSubtitle("Please paste the Google Sheet Link here ! ")
                .setFirstTextFieldHint("Link")
               // .setSecondTextFieldHint("password")
                .setFirstButtonText("CONNECT")
                .setSecondButtonText("CANCEL")
                .setFirstButtonColor(R.color.md_blue_200)
                .setSecondButtonColor(ResourcesCompat.getColor(getResources(), R.color.white,null))
                .setSecondButtonTextColor(R.color.md_blue_200)
                .setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.md_blue_200,null))

                .withFirstButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ChooseRole.this, flatDialog.getFirstTextField(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ChooseRole.this, SchoolLogin.class);
                        i.putExtra("LoginAs", false);
                        startActivity(i);
                    }
                })
                .withSecondButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flatDialog.dismiss();
                    }
                })
                .show();
    }
}