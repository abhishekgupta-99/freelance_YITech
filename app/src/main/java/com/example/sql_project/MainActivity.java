package com.example.sql_project;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sql_project.HelperClass.RecyclerViewAdapter;
import com.example.sql_project.HelperClass.Student_Item_Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    ProgressDialog loading;

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
        Button fab = findViewById(R.id.fab);
        mRecyclerview();
        //updateUI();
        getItems();

//call this function for hardcoded set of values
        //student_list = dataset();


//Uncomment this for fav action click
//
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 show_Add_Student_Dialog();

            }
        });

    }

    private void getItems() {

           loading =  ProgressDialog.show(this,"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxueYt0iOuJN6iPKJKG35CSKDegfuvQ3ls3yENsaefg2qVqGiS_/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                        Log.d("ANSRESPONSE",response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
//
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxueYt0iOuJN6iPKJKG35CSKDegfuvQ3ls3yENsaefg2qVqGiS_/exec?action=getItems", null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                       // System.out.println("response -->> " + response.toString());
//                            Log.d("JRESPONSE",response.toString());
//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                      //  parseItems(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println("change Pass response -->> " + error.toString());
//                    }
//                });
//
//        request.setRetryPolicy(new
//
//                DefaultRetryPolicy(60000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        Volley.newRequestQueue(MainActivity.this).add(request);
    }


    private void parseItems(String jsonResposnce) {
        ArrayList<Student_Item_Card> list = new ArrayList<Student_Item_Card>();
      //  Log.d("jsonResponse",jsonResposnce);

     //   ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            Log.d("jsonobj",jobj.toString());
            JSONArray jarray = jobj.getJSONArray("items");
            Log.d("jsonarray",jarray.toString());


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                Student_Item_Card obj = new Student_Item_Card();
                String json_studentName = jo.getString("StudentsName");
                String json_rollno = jo.getString("Roll No");
                String json_percent_Attend = jo.getString("percentAttend");

//                Log.d("name",json_studentName);
              //  String price = jo.getString("price");

                obj.set_student_name(json_studentName);
                obj.set_roll_no(json_rollno);
                obj.setPercent(json_percent_Attend+"");
                obj.setLast_Attendance("P");
               // obj.setLast_Attendance(cursor.getString(3));
                //obj.setPercent(cursor.getString(4));


//                HashMap<String, String> item = new HashMap<>();
//                item.put("itemName", json_studentName);
//                item.put("brand", json_rollno);
              //  item.put("price",price);
                Log.d("OBJECTT",json_percent_Attend.toString());

                list.add(obj);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new RecyclerViewAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        loading.dismiss();

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

//    private ArrayList<Student_Item_Card> dataset() {
//
//        ArrayList<Student_Item_Card> each_student_data=new ArrayList<>();
//
//        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
//        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
//        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
//        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
//        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
//        each_student_data.add(new Student_Item_Card("Abhishek Gupta","17"));
//
//        return each_student_data;
//    }


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

    public void Update_Sheet(View view) {
    }

    public void student_crud(View view) {
        Intent i=new Intent(this,Student_CRUD.class);
        startActivity(i);
        finish();

    }
}
