package com.example.sql_project;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sql_project.HelperClass.RecyclerViewAdapter;
import com.example.sql_project.HelperClass.Student_Item_Card;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private  DatabaseHelper db;
    EditText studentname;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Student_Item_Card> student_list;
    TextView student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        student=findViewById(R.id.student);
        FloatingActionButton fab = findViewById(R.id.fab);
        mRecyclerview();
        updateUI();


//call this function for hardcoded set of values
        //student_list = dataset();




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                 show_Add_Student_Dialog();

            }
        });

    }

    private void mRecyclerview() {

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }


    private void updateUI() {

        DatabaseHelper dbh=new DatabaseHelper(MainActivity.this);
        student_list=dbh.getAllElements();
        // alarm_listcopy=new ArrayList<>(alarm_list);
        mAdapter = new RecyclerViewAdapter(student_list);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void show_Add_Student_Dialog() {
        final Dialog dialog= new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater =getLayoutInflater();
        dialog.setContentView(R.layout.addstudent_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        final EditText et_studentname= (EditText)dialog.findViewById(R.id.studentname);
        final EditText et_studentrollno= (EditText)dialog.findViewById(R.id.roll_no);
        //final String student_name_et;

        //Here dialog.findviewbyid plays an important role

        Button add_student=(Button) dialog.findViewById(R.id.addstudent) ;
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String student_name_et= et_studentname.getText().toString();
                String student_rollno_et=et_studentrollno.getText().toString();
                Toast.makeText(getApplicationContext(), student_name_et, Toast.LENGTH_SHORT).show();
                Log.d("EDITTEXT",student_name_et);

                add_Student(student_name_et,student_rollno_et,dialog);
            }

        });


    }

    private ArrayList<Student_Item_Card> dataset() {

        ArrayList<Student_Item_Card> each_student_data=new ArrayList<>();

        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));

        return each_student_data;
    }


    private void createNote(String name,String rno) {
        // inserting note in db and getting
        // newly inserted note id
        try {
            long id = db.insertStudent(name,rno);
            Toast.makeText(this, id+"", Toast.LENGTH_SHORT).show();
            Log.d("IDDD",id+"");

            Student stu = db.getStudent(id);
            if(stu!=null)
            {
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {//Check error in this ,code entering exception
         //   Toast.makeText(this, "Failed adding student", Toast.LENGTH_SHORT).show();
        }
        // get the newly inserted note from db


    }

    public void add_Student(String sn,String rn,Dialog dg) {

        createNote(sn,rn);
       dg.dismiss();
       updateUI();

    }

}
