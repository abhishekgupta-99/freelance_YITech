package com.example.sql_project;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CRUD {
    private  Context context;
public boolean updateUI;
Student_CRUD updateUI_obj=new Student_CRUD();
    private  DatabaseHelper db;

    public void show_Add_Student_Dialog(final Context c) {
        final Dialog dialog = new Dialog(c);
        this.context=c;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  LayoutInflater inflater = getLayoutInflater();
        dialog.setContentView(R.layout.addstudent_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        lp.width=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        final EditText et_studentname = (EditText) dialog.findViewById(R.id.studentname);
        final EditText et_studentrollno = (EditText) dialog.findViewById(R.id.roll_no);

        //Here dialog.findviewbyid plays an important role

        Button add_student = (Button) dialog.findViewById(R.id.addstudent);
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String student_name_et = et_studentname.getText().toString();
                String student_rollno_et = et_studentrollno.getText().toString();
              //  Toast.makeText(c, student_name_et, Toast.LENGTH_SHORT).show();
                //Log.d("EDITTEXT", student_name_et);

                add_Student(student_name_et, student_rollno_et, dialog);
            }

        });

    }



    public void add_Student(String sn,String rn,Dialog dg) {

        createNote(sn,rn);
        dg.dismiss();
        updateUI_obj.updateUI();

    }


    private void createNote(String name,String rno) {
        // inserting note in db and getting
        // newly inserted note id
        try {
            db = new DatabaseHelper(context);
            long id = db.insertStudent(name,rno);
            Toast.makeText(context, id+"", Toast.LENGTH_SHORT).show();
            Log.d("IDDD",id+"");

            Student stu = db.getStudent(id);
            if(stu!=null)
            { updateUI=true;
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {//Check error in this ,code entering exception
            //   Toast.makeText(this, "Failed adding student", Toast.LENGTH_SHORT).show();
        }
        // get the newly inserted note from db


    }





}